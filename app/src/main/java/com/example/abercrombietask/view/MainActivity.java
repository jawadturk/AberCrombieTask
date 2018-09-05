package com.example.abercrombietask.view;


import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;


import com.example.abercrombietask.R;
import com.example.abercrombietask.databinding.ActivityMainBinding;
import com.example.abercrombietask.model.Promotion;
import com.example.abercrombietask.stickyrecyclerheaders.SectionedRecyclerViewAdapter;
import com.example.abercrombietask.viewmodel.PromotionsViewModel;
import com.example.abercrombietask.viewmodel.RecyclerPromotionView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer, RecyclerPromotionView.onShopNowClickListener {

    private ActivityMainBinding activityMainBinding;
    private PromotionsViewModel promotionsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataBinding();
        setupListPromotionsView(activityMainBinding.listPromotions);
        setupObserver(promotionsViewModel);
        promotionsViewModel.loadPromotions();

    }

    private void initDataBinding() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        promotionsViewModel = new PromotionsViewModel(this);
        activityMainBinding.setMainViewModel(promotionsViewModel);
    }

    private void setupListPromotionsView(RecyclerView listPromotions) {
        SectionedRecyclerViewAdapter adapter = new SectionedRecyclerViewAdapter();
        listPromotions.setAdapter(adapter);
        listPromotions.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        promotionsViewModel.reset();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof PromotionsViewModel) {

            List<Promotion> promotionList = promotionsViewModel.getPromotionsList();
            SectionedRecyclerViewAdapter listPromotionsAdapter = (SectionedRecyclerViewAdapter) activityMainBinding.listPromotions.getAdapter();
            listPromotionsAdapter.removeAllSections();

            for (int i = 0; i < promotionList.size(); i++) {

                RecyclerPromotionView promotionView = new RecyclerPromotionView(promotionList.get(i));
                promotionView.setMyClickListener(this);
                listPromotionsAdapter.addSection(String.valueOf(i), promotionView);

            }
            listPromotionsAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onItemClick(View v, String url) {
        promotionsViewModel.openWebView(v, url);
    }

    @Override
    protected void onPause() {
        super.onPause();
        promotionsViewModel.cancelApiCall();
    }
}

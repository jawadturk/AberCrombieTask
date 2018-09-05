/**
 * Copyright 2016 Erik Jhordan Rey. <p/> Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at <p/> http://www.apache.org/licenses/LICENSE-2.0 <p/> Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package com.example.abercrombietask.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.abercrombietask.AberCrombieApplication;
import com.example.abercrombietask.R;
import com.example.abercrombietask.data.DataService;
import com.example.abercrombietask.model.Promotion;
import com.example.abercrombietask.view.WebViewActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PromotionsViewModel extends Observable {
    private final String TAG = PromotionsViewModel.class.getSimpleName();

    private ObservableInt promotionsProgress;
    private ObservableInt promotionsRecycler;


    private List<Promotion> promotionList;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Gson gson;

    public PromotionsViewModel(@NonNull Context context) {

        this.context = context;
        this.promotionList = new ArrayList<>();
        promotionsProgress = new ObservableInt(View.GONE);
        promotionsRecycler = new ObservableInt(View.VISIBLE);
        gson = new Gson();
    }
    public ObservableInt getPromotionsProgress() {
        return promotionsProgress;
    }

    public ObservableInt getPromotionsRecycler() {
        return promotionsRecycler;
    }

    public void loadPromotions() {
        initializeViews();

        //using Retrofit
        //fetchPromotionsWithRetrofit();
        //using volley
        fetchPromotionsWithVolley();

    }

    //It is "public" to show an example of test
    public void initializeViews() {
        promotionsRecycler.set(View.GONE);
        promotionsProgress.set(View.VISIBLE);
    }


    private void fetchPromotionsWithVolley()

    {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.abercrombie.com/anf/nativeapp/qa/codetest/codeTest_exploreData.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type collectionType = new TypeToken<List<Promotion>>() {
                        }.getType();
                        List<Promotion> promotions = (List<Promotion>) gson.fromJson(response.toString(), collectionType);
                        changePromotionsDataSet(promotions);
                        Log.d(TAG, "onResponse: " + promotions.size());
                        promotionsProgress.set(View.GONE);
                        promotionsRecycler.set(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + error.toString());
                Toast.makeText(context, R.string.error_loading, Toast.LENGTH_SHORT).show();
                promotionsProgress.set(View.GONE);
                promotionsRecycler.set(View.GONE);
            }
        });


        // Adding request to request queue
        AberCrombieApplication.create(context).addToRequestQueue(stringRequest, TAG);
    }

    public void fetchPromotionsWithRetrofit() {
        AberCrombieApplication aberCrombieApplication = AberCrombieApplication.create(context);
        DataService dataService = aberCrombieApplication.getDataService();

        Disposable disposable = dataService.fetchPromotions()
                .subscribeOn(aberCrombieApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Promotion>>() {
                    @Override
                    public void accept(List<Promotion> promotions) throws Exception {
                        changePromotionsDataSet(promotions);
                        promotionsProgress.set(View.GONE);
                        promotionsRecycler.set(View.VISIBLE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, throwable.getMessage());
                        Toast.makeText(context, R.string.error_loading, Toast.LENGTH_SHORT).show();

                        promotionsProgress.set(View.GONE);
                        promotionsRecycler.set(View.GONE);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void changePromotionsDataSet(List<Promotion> promotions) {
        promotionList.addAll(promotions);
        setChanged();
        notifyObservers();
    }

    public List<Promotion> getPromotionsList() {
        return promotionList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }

    public void openWebView(View v, String url) {
        context.startActivity(WebViewActivity.launchWebView(v.getContext(), url));
    }

    public void cancelApiCall()
    {
        AberCrombieApplication.create(context).cancelPendingRequests(TAG);
    }
}

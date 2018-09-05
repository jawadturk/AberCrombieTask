package com.example.abercrombietask.viewmodel;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abercrombietask.R;
import com.example.abercrombietask.model.Promotion;
import com.example.abercrombietask.stickyrecyclerheaders.StatelessSection;


public class RecyclerPromotionView extends StatelessSection {

    private Promotion promotion;
    private onShopNowClickListener myClickListener;


    public RecyclerPromotionView(Promotion promotion) {
        super(R.layout.item_promotion_layout, R.layout.item_button_layout);
        this.promotion = promotion;

    }

    public void setMyClickListener(onShopNowClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public int getContentItemsTotal() {
        return promotion.getContent().size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        itemHolder.button_shopNow.setText(promotion.getContent().get(position).getTitle());
        itemHolder.button_shopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onItemClick(v, promotion.getContent().get(position).getTarget());
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int headerPosition) {
        HeaderViewHolder mainHolder = (HeaderViewHolder) holder;

        if (TextUtils.isEmpty(promotion.getTitle())) {
            mainHolder.mTextViewTitle.setVisibility(View.GONE);
        } else {
            mainHolder.mTextViewTitle.setText(promotion.getTitle());
            mainHolder.mTextViewTitle.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(promotion.getTopDescription())) {
            mainHolder.mTextViewTopDescription.setVisibility(View.GONE);
        } else {
            mainHolder.mTextViewTopDescription.setText(promotion.getTopDescription());
            mainHolder.mTextViewTopDescription.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(promotion.getPromoMessage())) {
            mainHolder.mTextViewPromoMessage.setVisibility(View.GONE);
        } else {
            mainHolder.mTextViewPromoMessage.setText(promotion.getPromoMessage());
            mainHolder.mTextViewPromoMessage.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(promotion.getBottomDescription())) {
            mainHolder.mTextViewBottomDescription.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mainHolder.mTextViewBottomDescription.setText(Html.fromHtml(promotion.getBottomDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                mainHolder.mTextViewBottomDescription.setText(Html.fromHtml(promotion.getBottomDescription()));
            }
        } else {
            mainHolder.mTextViewBottomDescription.setVisibility(View.GONE);
        }

        Glide.with(mainHolder.mImageViewBackGroud.getContext()).load(promotion.getBackgroundImage()).into(mainHolder.mImageViewBackGroud);

    }


    public interface onShopNowClickListener {
        public void onItemClick(View v, String url);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private TextView mTextViewTopDescription;
        private TextView mTextViewPromoMessage;
        private TextView mTextViewBottomDescription;
        private ImageView mImageViewBackGroud;


        public HeaderViewHolder(View view) {
            super(view);

            mTextViewTitle = (TextView) itemView.findViewById(R.id.label_title);
            mTextViewTopDescription = (TextView) itemView.findViewById(R.id.label_topDescription);
            mTextViewPromoMessage = (TextView) itemView.findViewById(R.id.label_promoMessage);
            mTextViewBottomDescription = (TextView) itemView.findViewById(R.id.label_bottomDescription);
            mImageViewBackGroud = (ImageView) itemView.findViewById(R.id.image_promotion);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final Button button_shopNow;

        public ItemViewHolder(View view) {
            super(view);
            button_shopNow = (Button) view.findViewById(R.id.button_shopNow);

        }
    }

}
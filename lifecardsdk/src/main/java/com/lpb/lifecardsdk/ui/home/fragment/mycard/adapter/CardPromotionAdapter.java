package com.lpb.lifecardsdk.ui.home.fragment.mycard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpb.lifecardsdk.R;
import com.lpb.lifecardsdk.constant.Constants;
import com.lpb.lifecardsdk.data.model.response.default_.MyCardDetailsWaitResponseDefault;
import com.lpb.lifecardsdk.util.GlideUtils;
import com.lpb.lifecardsdk.util.StringUtils;

import java.util.List;

public class CardPromotionAdapter extends RecyclerView.Adapter<CardPromotionAdapter.ViewHolder> {
    private List<MyCardDetailsWaitResponseDefault.PromotionDto> promotionDtos;
    private Context context;

    public CardPromotionAdapter(Context context, List<MyCardDetailsWaitResponseDefault.PromotionDto> promotionDtos) {
        this.promotionDtos = promotionDtos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lifecardsdk_item_promotion, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideUtils.loadImageUrl(holder.imgPromotion,promotionDtos.get(position).getUrlIcon(),context, Constants.PlaceHolderType.LOGO_PROMOTION);
        holder.tvPromotion.setText(StringUtils.convertHTMLToString(promotionDtos.get(position).getDescription(), context));
    }


    @Override
    public int getItemCount() {
        if (promotionDtos == null) return 0;
        return promotionDtos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imgPromotion;
        final TextView tvPromotion;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPromotion = itemView.findViewById(R.id.imgPromotion);
            tvPromotion = itemView.findViewById(R.id.tvPromotion);
        }
    }

}
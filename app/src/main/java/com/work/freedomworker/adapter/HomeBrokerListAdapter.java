package com.work.freedomworker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.work.freedomworker.R;
import com.work.freedomworker.model.HomeBrokerModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class HomeBrokerListAdapter extends RecyclerView.Adapter<HomeBrokerListAdapter.ViewHolder> {

    private Context context;

    private List<HomeBrokerModel.HomeBrokerBean.HomeBrokerEntry> list;

    OnMainItemClick onMainItemClick;

    public HomeBrokerListAdapter(Context context, List<HomeBrokerModel.HomeBrokerBean.HomeBrokerEntry> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnMainItemClick(OnMainItemClick onMainItemClick) {
        this.onMainItemClick = onMainItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_home_broker, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Glide.with(context)
                .load(list.get(i).getFull_avatar())
                .into(viewHolder.ivBrokerHeadportrait);

        viewHolder.tvBrokerName.setText(list.get(i).getName());
        if (list.get(i).getLevel_num() == 1) {
            viewHolder.ivBrokerLevel.setImageResource(R.mipmap.ic_level_one);
        } else {
            viewHolder.ivBrokerLevel.setImageResource(R.mipmap.ic_level_two);
        }

        if (!(list.get(i).getDistance()==null)&&!list.get(i).getDistance().equals("-")) {//distance为空或者-都是无位置信息
            viewHolder.llBrokerDistance.setVisibility(View.VISIBLE);
            int num = Integer.parseInt(list.get(i).getDistance());
            if (num >= 1000) {
                double distance = Math.round(num / 100d) / 10d;//单位，千米，保留以为小数点，如果是保留两位，就是(num/100d)/100d
                viewHolder.tvBrokerDistance.setText("距离 " + distance + "km");
            } else {
                viewHolder.tvBrokerDistance.setText("距离 " + num + "m");
            }
        }else{
            viewHolder.llBrokerDistance.setVisibility(View.GONE);
        }


        viewHolder.tvBrokerShareCount.setText(list.get(i).getShare_count() + "");
        viewHolder.tvBrokerApplyCount.setText(list.get(i).getApply_count() + "");
        viewHolder.tvBrokerOfficalCount.setText(list.get(i).getOffical_count() + "");


//        viewHolder.llMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onMainItemClick.onItemClick(i);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        ImageView ivBrokerHeadportrait;
        TextView tvBrokerName;
        ImageView ivBrokerLevel;
        TextView tvBrokerEvalute;
        TextView tvBrokerDistance;

        TextView tvBrokerShareCount;
        TextView tvBrokerApplyCount;
        TextView tvBrokerOfficalCount;

        LinearLayout llBrokerDistance;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivBrokerHeadportrait = (ImageView) itemView.findViewById(R.id.iv_broker_headportrait);
            tvBrokerName = (TextView) itemView.findViewById(R.id.tv_broker_name);
            ivBrokerLevel = (ImageView) itemView.findViewById(R.id.iv_broker_level);
            tvBrokerEvalute = (TextView) itemView.findViewById(R.id.tv_broker_evalute);
            tvBrokerDistance = (TextView) itemView.findViewById(R.id.tv_broker_distance);

            tvBrokerShareCount = (TextView) itemView.findViewById(R.id.tv_broker_share_count);
            tvBrokerApplyCount = (TextView) itemView.findViewById(R.id.tv_broker_apply_count);
            tvBrokerOfficalCount = (TextView) itemView.findViewById(R.id.tv_broker_offical_count);
            llBrokerDistance=itemView.findViewById(R.id.ll_broker_distance);
        }
    }

    public interface OnMainItemClick {
        void onItemClick(int position);
    }

}

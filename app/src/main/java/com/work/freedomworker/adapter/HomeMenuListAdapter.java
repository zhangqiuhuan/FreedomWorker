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
import com.work.freedomworker.model.MenuModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class HomeMenuListAdapter extends RecyclerView.Adapter<HomeMenuListAdapter.ViewHolder>{

    private Context context;

    private List<MenuModel.MenuBean> list;

    OnMainItemClick onMainItemClick;

    public HomeMenuListAdapter(Context context, List<MenuModel.MenuBean> list){
        this.context=context;
        this.list=list;
    }

    public void setOnMainItemClick(OnMainItemClick onMainItemClick) {
        this.onMainItemClick = onMainItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_home_menu,  viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {

        viewHolder.tvMenu.setText(list.get(i).getTitle());

        viewHolder.llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMainItemClick.onItemClick(i);
            }
        });

        Glide.with(context)
                .load(list.get(i).getFull_icon())
                .into(viewHolder.ivMenu);
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
        LinearLayout llMenu;
        ImageView ivMenu;
        TextView tvMenu;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llMenu = (LinearLayout) itemView.findViewById(R.id.ll_menu);
            ivMenu = (ImageView) itemView.findViewById(R.id.iv_menu);
            tvMenu = (TextView) itemView.findViewById(R.id.tv_menu);
        }
    }

    public interface OnMainItemClick{
        void onItemClick(int position);
    }

}

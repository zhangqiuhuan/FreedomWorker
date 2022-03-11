package com.work.freedomworker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.work.freedomworker.R;
import com.work.freedomworker.model.CityModel;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private Context context;

    private List<CityModel.CityBean> list;

    OnCityItemClick onItemClick;

    private int selectindex=-1;

    public CityListAdapter(Context context, List<CityModel.CityBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClick(OnCityItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setSelectindex(int selectindex) {
        this.selectindex = selectindex;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_area_select_city_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tvCityname.setText(list.get(i).getName());


        //选中时
        if (selectindex == i) {
            viewHolder.tvCityname.setTextColor(context.getResources().getColor(R.color.maincolor));
        } else {
            viewHolder.tvCityname.setTextColor(context.getResources().getColor(R.color.defaultcolor));
        }
        viewHolder.tvCityname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(i);
            }
        });

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
        TextView tvCityname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvCityname = (TextView) itemView.findViewById(R.id.tv_cityname);

        }
    }

    public interface OnCityItemClick {
        void onItemClick(int position);
    }

}

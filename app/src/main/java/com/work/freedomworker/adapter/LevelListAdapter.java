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
import com.work.freedomworker.model.LevelModel;

import java.util.List;

public class LevelListAdapter extends RecyclerView.Adapter<LevelListAdapter.ViewHolder> {

    private Context context;

    private List<LevelModel.LevelBean> list;

    OnLevelItemClick onItemClick;

    private int selectindex=-1;

    public LevelListAdapter(Context context, List<LevelModel.LevelBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClick(OnLevelItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setSelectindex(int selectindex) {
        this.selectindex = selectindex;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_level_select_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tvLevel.setText(list.get(i).getLevel_name());


        //选中时
        if (selectindex == i) {
            viewHolder.tvLevel.setTextColor(context.getResources().getColor(R.color.maincolor));
            viewHolder.tvLevel.setBackgroundResource(R.drawable.btn_level_selected);
        } else {
            viewHolder.tvLevel.setTextColor(context.getResources().getColor(R.color.defaultcolor));
            viewHolder.tvLevel.setBackgroundResource(R.drawable.btn_level_unselect);
        }
        viewHolder.tvLevel.setOnClickListener(new View.OnClickListener() {
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
        TextView tvLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvLevel = (TextView) itemView.findViewById(R.id.tv_level);

        }
    }

    public interface OnLevelItemClick {
        void onItemClick(int position);
    }

}

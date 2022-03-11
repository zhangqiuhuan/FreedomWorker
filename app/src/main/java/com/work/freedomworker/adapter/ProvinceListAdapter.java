package com.work.freedomworker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.work.freedomworker.R;
import com.work.freedomworker.model.ProvinceModel;

import java.util.List;

public class ProvinceListAdapter extends RecyclerView.Adapter<ProvinceListAdapter.ViewHolder>{

    private Context context;

    private List<ProvinceModel.ProvinceBean> list;

    OnProvinceItemClick onItemClick;

    private  int selectindex=-1;
    public ProvinceListAdapter(Context context, List<ProvinceModel.ProvinceBean> list){
        this.context=context;
        this.list=list;
    }

    public void setOnItemClick(OnProvinceItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setSelectindex(int selectindex) {
        this.selectindex = selectindex;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_area_select_province_item,  viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {

        viewHolder.tvProvincename.setText(list.get(i).getName());

        viewHolder.tvProvincename.post(new Runnable() {
            @Override
            public void run() {
//                获取要 textview的宽度
                viewHolder.tvProvincename.measure(0, 0);
                int height =   viewHolder.tvProvincename.getHeight();
//                获取 线 的 layout参数
                ViewGroup.LayoutParams lineParams = viewHolder.viewHignLight.getLayoutParams();
//                将 textview 的宽度  设置给  线的
                lineParams.height = height;
                viewHolder.viewHignLight.setLayoutParams(lineParams);
            }
        });

        //选中时
        if (selectindex==i) {
            viewHolder.viewHignLight.setVisibility(View.VISIBLE);
            viewHolder.tvProvincename.setTextColor(context.getResources().getColor(R.color.maincolor));
        }else{
            viewHolder.viewHignLight.setVisibility(View.INVISIBLE);
            viewHolder.tvProvincename.setTextColor(context.getResources().getColor(R.color.defaultcolor));
        }
            viewHolder.llProvince.setOnClickListener(new View.OnClickListener() {
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
        View viewHignLight;
        TextView tvProvincename;
        LinearLayout llProvince;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewHignLight =  itemView.findViewById(R.id.view_highlight);
            tvProvincename = (TextView) itemView.findViewById(R.id.tv_provicename);
            llProvince = (LinearLayout) itemView.findViewById(R.id.ll_province);

        }
    }

    public interface OnProvinceItemClick{
        void onItemClick(int position);
    }

}

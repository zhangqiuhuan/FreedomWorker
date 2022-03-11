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
import com.work.freedomworker.model.HomeTaskSquareModel;
import com.work.freedomworker.utils.DateUtils;

import java.util.List;

public class HomeTaskSquareListAdapter extends RecyclerView.Adapter<HomeTaskSquareListAdapter.ViewHolder>{

    private Context context;

    private List<HomeTaskSquareModel.HomeTaskSquareBean.HomeTaskSquareEntry> list;

    OnMainItemClick onMainItemClick;

    public HomeTaskSquareListAdapter(Context context, List<HomeTaskSquareModel.HomeTaskSquareBean.HomeTaskSquareEntry> list){
        this.context=context;
        this.list=list;
    }

    public void setOnMainItemClick(OnMainItemClick onMainItemClick) {
        this.onMainItemClick = onMainItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_home_task_square,  viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {

        viewHolder.tvTaskSquareTitle.setText(list.get(i).getTitle());
        viewHolder.tvTaskSquareSalary.setText(list.get(i).getSalary()+getSalaryUnit(list.get(i).getSalary_unit_ext()));
        viewHolder.tvTaskSquarePosition.setText(list.get(i).getBusiness_name()+"丨"+list.get(i).getPosition());
        viewHolder.tvTaskSquareDate.setText("工作日期 "+ DateUtils.formatDate(list.get(i).getWork_start_date(),"yyyy.MM.dd")+"-"+ DateUtils.formatDate(list.get(i).getWork_end_date(),"yyyy.MM.dd"));

        int num=(int)list.get(i).getDistance();
        if (num>=1000){
            double distance=Math.round(list.get(i).getDistance()/100d)/10d;//单位，千米，保留以为小数点，如果是保留两位，就是(num/100d)/100d
            viewHolder.tvTaskSquareDistance.setText("距离 "+distance+"km");
        }else{
            viewHolder.tvTaskSquareDistance.setText("距离 "+(int)list.get(i).getDistance()+"m");
        }



        if (list.get(i).getIs_student_papers()==0){
            viewHolder.tvTaskSquareStudentcard.setVisibility(View.GONE);
        }else{
            viewHolder.tvTaskSquareStudentcard.setVisibility(View.VISIBLE);
        }

        if (list.get(i).getIs_student_papers()==0){
            viewHolder.tvTaskSquareHealthcard.setVisibility(View.GONE);
        }else{
            viewHolder.tvTaskSquareHealthcard.setVisibility(View.VISIBLE);
        }

        if (list.get(i).getOpen_share_earn()==0){
            viewHolder.llTaskSquareShareearn.setVisibility(View.GONE);
        }else{
            viewHolder.llTaskSquareShareearn.setVisibility(View.VISIBLE);
        }

//        viewHolder.llMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onMainItemClick.onItemClick(i);
//            }
//        });


    }

    private String getSalaryUnit(int type){
        String unit="";
        switch (type){
            case 1:
                unit="元/小时";
                break;
            case 2:
                unit="元/天";
                break;
            case 3:
                unit="元/周";
                break;
            case 4:
                unit="元/月";
                break;
            case 5:
                unit="元/件";
                break;
            case 6:
                unit="元/次";
                break;
            default:
                break;
        }
        return unit;
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

        TextView tvTaskSquareTitle;
        TextView tvTaskSquareSalary;
        TextView tvTaskSquarePosition;
        TextView tvTaskSquareDate;
        TextView tvTaskSquareDistance;

        LinearLayout llTaskSquareShareearn;
        TextView tvTaskSquareStudentcard;
        TextView tvTaskSquareHealthcard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTaskSquareTitle = (TextView) itemView.findViewById(R.id.tv_task_square_title);
            tvTaskSquareSalary = (TextView) itemView.findViewById(R.id.tv_task_square_salary);
            tvTaskSquarePosition = (TextView) itemView.findViewById(R.id.tv_task_square_position);
            tvTaskSquareDate = (TextView) itemView.findViewById(R.id.tv_task_square_date);
            tvTaskSquareDistance = (TextView) itemView.findViewById(R.id.tv_task_square_distance);

            llTaskSquareShareearn = (LinearLayout) itemView.findViewById(R.id.ll_task_square_shareearn);
            tvTaskSquareStudentcard = (TextView) itemView.findViewById(R.id.tv_task_square_studentcard);
            tvTaskSquareHealthcard = (TextView) itemView.findViewById(R.id.tv_task_square_healthcard);
        }
    }

    public interface OnMainItemClick{
        void onItemClick(int position);
    }

}

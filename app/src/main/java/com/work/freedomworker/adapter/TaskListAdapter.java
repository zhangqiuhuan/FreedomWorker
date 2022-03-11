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
import com.work.freedomworker.model.TaskModel;
import com.work.freedomworker.utils.DateUtils;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>{

    private Context context;

    private List<TaskModel.TaskBean.TaskEntry> list;

    OnTaskItemClick onMainItemClick;

    public TaskListAdapter(Context context, List<TaskModel.TaskBean.TaskEntry> list){
        this.context=context;
        this.list=list;
    }

    public void setOnTaskItemClick(OnTaskItemClick onMainItemClick) {
        this.onMainItemClick = onMainItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_task_list_item,  viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {

        viewHolder.tvTaskTitle.setText(list.get(i).getTitle());
        viewHolder.tvTaskSalary.setText(list.get(i).getSalary()+getSalaryUnit(list.get(i).getSalary_unit_ext()));
        viewHolder.tvTaskPosition.setText(list.get(i).getBusiness_name()+"丨"+list.get(i).getPosition());
        viewHolder.tvTaskDate.setText("工作日期 "+ DateUtils.formatDate(list.get(i).getWork_start_date(),"yyyy.MM.dd")+"-"+ DateUtils.formatDate(list.get(i).getWork_end_date(),"yyyy.MM.dd"));

        setTaskStatusTxt(viewHolder.tvTaskStatus,list.get(i).getApply_status());

        if (list.get(i).getJob_sex()==0){
            viewHolder.tvTaskSex.setText("男女不限");
        }else if (list.get(i).getJob_sex()==1){
            viewHolder.tvTaskSex.setText("限男");
        }else{
            viewHolder.tvTaskSex.setText("限女");
        }

        if (list.get(i).getIs_student_papers()==0){
            viewHolder.tvTaskStudentcard.setVisibility(View.GONE);
        }else{
            viewHolder.tvTaskStudentcard.setVisibility(View.VISIBLE);
        }

        if (list.get(i).getIs_student_papers()==0){
            viewHolder.tvTaskHealthcard.setVisibility(View.GONE);
        }else{
            viewHolder.tvTaskHealthcard.setVisibility(View.VISIBLE);
        }





//        viewHolder.llMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onMainItemClick.onItemClick(i);
//            }
//        });


    }


    private void setTaskStatusTxt(TextView textView,int status){
        //1:报名 2:确认上岗 3:取消报名 4:取消上岗 5:撤回录用 6已完成
        String statusTxt="";
        switch (status) {
            case 1:
                statusTxt = "待录用";
                textView.setText(statusTxt);
                textView.setTextColor(context.getResources().getColor(R.color.maincolor));
                textView.setBackgroundResource(R.drawable.bg_task_status_employed_stay);
                break;
            case 2:
                statusTxt = "已录用";
                textView.setText(statusTxt);
                textView.setTextColor(context.getResources().getColor(R.color.green61D));
                textView.setBackgroundResource(R.drawable.bg_task_status_employed_already);
                break;
            case 3:
                statusTxt = "取消报名";
                textView.setText(statusTxt);
                textView.setTextColor(context.getResources().getColor(R.color.grayAF));
                textView.setBackgroundResource(R.drawable.bg_task_status_employed_cancel);
                break;
            case 4:
                statusTxt = "取消上岗";
                textView.setText(statusTxt);
                textView.setTextColor(context.getResources().getColor(R.color.grayAF));
                textView.setBackgroundResource(R.drawable.bg_task_status_employed_cancel);
                break;
            case 5:
                statusTxt = "撤回上岗";
                textView.setText(statusTxt);
                textView.setTextColor(context.getResources().getColor(R.color.grayAF));
                textView.setBackgroundResource(R.drawable.bg_task_status_employed_cancel);
                break;
            case 6:
                statusTxt = "已完成";
                textView.setText(statusTxt);
                textView.setTextColor(context.getResources().getColor(R.color.redFF3));
                textView.setBackgroundResource(R.drawable.bg_task_status_employed_finish);
                break;
        }

      //  return statusTxt;
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

        TextView tvTaskTitle;
        TextView tvTaskStatus;
        TextView tvTaskSalary;
        TextView tvTaskPosition;
        TextView tvTaskDate;

        TextView tvTaskSex;
        TextView tvTaskStudentcard;
        TextView tvTaskHealthcard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTaskTitle = (TextView) itemView.findViewById(R.id.tv_task_title);
            tvTaskStatus = (TextView) itemView.findViewById(R.id.tv_task_status);

            tvTaskSalary = (TextView) itemView.findViewById(R.id.tv_task_salary);
            tvTaskPosition = (TextView) itemView.findViewById(R.id.tv_task_position);

            tvTaskDate = (TextView) itemView.findViewById(R.id.tv_task_date);

            tvTaskSex = (TextView) itemView.findViewById(R.id.tv_task_sex);
            tvTaskStudentcard = (TextView) itemView.findViewById(R.id.tv_task_studentcard);
            tvTaskHealthcard = (TextView) itemView.findViewById(R.id.tv_task_healthcard);
        }
    }

    public interface OnTaskItemClick{
        void onItemClick(int position);
    }

}

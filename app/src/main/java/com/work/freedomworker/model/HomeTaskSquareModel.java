package com.work.freedomworker.model;

import java.util.List;

public class HomeTaskSquareModel {
    public int code;
    public String status;
    private HomeTaskSquareBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HomeTaskSquareBean getData() {
        return data;
    }

    public void setData(HomeTaskSquareBean data) {
        this.data = data;
    }

    public class HomeTaskSquareBean {
        private List<HomeTaskSquareEntry> data;

        public List<HomeTaskSquareEntry> getData() {
            return data;
        }

        public void setData(List<HomeTaskSquareEntry> data) {
            this.data = data;
        }

        public class HomeTaskSquareEntry {
            private int id;
            private String title;//任务标题
            private int task_status;//任务状态
            private int salary;//薪资
            private int salary_unit_ext;//薪资单位，1：元/小时，2：元/天，3：元/周，4：元/月，5：元/件，6：元/次
            private String position;//岗位
            private String business_name;//商家名
            private int feedback_number;//报名人数
            private int total_number;//招聘人数
            private int offical_number;//上岗人数
            private String work_start_date;//工作开始日期
            private String work_end_date;//工作结束日期
            private int job_sex;//性别限制 0不限男女，1限男，2限女
            private int is_student_papers;//是否需要学生证 0不需要1需要
            private int is_health_papers;//是否需要健康证 0不需要1需要
            private double distance;// 距离（米）
            private int open_share_earn;

            public int getOpen_share_earn() {
                return open_share_earn;
            }

            public void setOpen_share_earn(int open_share_earn) {
                this.open_share_earn = open_share_earn;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getTask_status() {
                return task_status;
            }

            public void setTask_status(int task_status) {
                this.task_status = task_status;
            }

            public int getSalary() {
                return salary;
            }

            public void setSalary(int salary) {
                this.salary = salary;
            }

            public int getSalary_unit_ext() {
                return salary_unit_ext;
            }

            public void setSalary_unit_ext(int salary_unit_ext) {
                this.salary_unit_ext = salary_unit_ext;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getBusiness_name() {
                return business_name;
            }

            public void setBusiness_name(String business_name) {
                this.business_name = business_name;
            }

            public int getFeedback_number() {
                return feedback_number;
            }

            public void setFeedback_number(int feedback_number) {
                this.feedback_number = feedback_number;
            }

            public int getTotal_number() {
                return total_number;
            }

            public void setTotal_number(int total_number) {
                this.total_number = total_number;
            }

            public int getOffical_number() {
                return offical_number;
            }

            public void setOffical_number(int offical_number) {
                this.offical_number = offical_number;
            }

            public String getWork_start_date() {
                return work_start_date;
            }

            public void setWork_start_date(String work_start_date) {
                this.work_start_date = work_start_date;
            }

            public String getWork_end_date() {
                return work_end_date;
            }

            public void setWork_end_date(String work_end_date) {
                this.work_end_date = work_end_date;
            }

            public int getJob_sex() {
                return job_sex;
            }

            public void setJob_sex(int job_sex) {
                this.job_sex = job_sex;
            }

            public int getIs_student_papers() {
                return is_student_papers;
            }

            public void setIs_student_papers(int is_student_papers) {
                this.is_student_papers = is_student_papers;
            }

            public int getIs_health_papers() {
                return is_health_papers;
            }

            public void setIs_health_papers(int is_health_papers) {
                this.is_health_papers = is_health_papers;
            }

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }
        }
    }
}

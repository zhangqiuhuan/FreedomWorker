package com.work.freedomworker.model;

import java.util.List;

public class TaskModel {
    public int code;
    public String status;
    private TaskBean data;

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

    public TaskBean getData() {
        return data;
    }

    public void setData(TaskBean data) {
        this.data = data;
    }

    public class TaskBean {

        private PageBean page;

        private List<TaskEntry> data;

        public List<TaskEntry> getData() {
            return data;
        }

        public void setData(List<TaskEntry> data) {
            this.data = data;
        }

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public class PageBean{
            private int current_page;
            private int total;

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }

        public class TaskEntry {
            private int id;//报名任务id
            private String title;//任务标题
            private String address;//地址
            private String province;
            private String city;
            private String district;
            private String broker_name;//经纪人名字
            private int apply_status;//报名状态 1:报名 2:确认上岗 3:取消报名 4:取消上岗 5:撤回录用 6已完成
            private int salary;//薪资
            private int salary_unit_ext;//经纪人奖金单位，1：小时， 2：天 ，3：周 ，4：月，5：件，  6：次
            private int job_task_id;//任务id
            private int broker_id;//经纪人id
            private String broker;//经纪人名
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getBroker_name() {
                return broker_name;
            }

            public void setBroker_name(String broker_name) {
                this.broker_name = broker_name;
            }

            public int getApply_status() {
                return apply_status;
            }

            public void setApply_status(int apply_status) {
                this.apply_status = apply_status;
            }

            public int getJob_task_id() {
                return job_task_id;
            }

            public void setJob_task_id(int job_task_id) {
                this.job_task_id = job_task_id;
            }

            public int getBroker_id() {
                return broker_id;
            }

            public void setBroker_id(int broker_id) {
                this.broker_id = broker_id;
            }

            public String getBroker() {
                return broker;
            }

            public void setBroker(String broker) {
                this.broker = broker;
            }
        }
    }
}

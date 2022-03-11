package com.work.freedomworker.model;

import java.util.List;

public class HomeBrokerModel {
    public int code;
    public String status;
    private HomeBrokerModel.HomeBrokerBean data;

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

    public HomeBrokerBean getData() {
        return data;
    }

    public void setData(HomeBrokerBean data) {
        this.data = data;
    }

    public class HomeBrokerBean {
        private List<HomeBrokerModel.HomeBrokerBean.HomeBrokerEntry> data;

        public List<HomeBrokerEntry> getData() {
            return data;
        }

        public void setData(List<HomeBrokerEntry> data) {
            this.data = data;
        }

        public class HomeBrokerEntry {
            private int id;
            private String name;//经纪人姓名
            private String avatar;//头像相对路径
            private String full_avatar;//头像完整路径
            private String phone;//手机
            private String level;//等级
            private int level_num;//经验等级值
            private int level_full_exp;//经验值
            private int share_count;//分享数
            private int apply_count;//报名人数
            private int offical_count;//上岗人数
            private String distance;// 距离（米）

            private String lng;
            private String lat;


            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getFull_avatar() {
                return full_avatar;
            }

            public void setFull_avatar(String full_avatar) {
                this.full_avatar = full_avatar;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public int getLevel_num() {
                return level_num;
            }

            public void setLevel_num(int level_num) {
                this.level_num = level_num;
            }

            public int getLevel_full_exp() {
                return level_full_exp;
            }

            public void setLevel_full_exp(int level_full_exp) {
                this.level_full_exp = level_full_exp;
            }

            public int getShare_count() {
                return share_count;
            }

            public void setShare_count(int share_count) {
                this.share_count = share_count;
            }

            public int getApply_count() {
                return apply_count;
            }

            public void setApply_count(int apply_count) {
                this.apply_count = apply_count;
            }

            public int getOffical_count() {
                return offical_count;
            }

            public void setOffical_count(int offical_count) {
                this.offical_count = offical_count;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }
    }
}

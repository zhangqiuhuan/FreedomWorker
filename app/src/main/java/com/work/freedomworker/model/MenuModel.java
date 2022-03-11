package com.work.freedomworker.model;

import java.util.List;

public class MenuModel {
    public int code;
    public String status;
    private List<MenuModel.MenuBean> data;

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

    public List<MenuBean> getData() {
        return data;
    }

    public void setData(List<MenuBean> data) {
        this.data = data;
    }

    public class MenuBean {
        private int id;
        private String icon;
        private String full_icon;
        private String title;
        private int order;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getFull_icon() {
            return full_icon;
        }

        public void setFull_icon(String full_icon) {
            this.full_icon = full_icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }
}

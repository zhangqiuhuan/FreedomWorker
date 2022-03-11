package com.work.freedomworker.model;

import java.util.List;

public class ProvinceModel {
    public int code;
    public String status;

    private List<ProvinceBean> data;

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

    public List<ProvinceBean> getData() {
        return data;
    }

    public void setData(List<ProvinceBean> data) {
        this.data = data;
    }

    public class ProvinceBean{
        private int code;
        private String name;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

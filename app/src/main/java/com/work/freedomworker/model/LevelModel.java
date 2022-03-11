package com.work.freedomworker.model;

import java.util.List;

public class LevelModel {
    public int code;
    public String status;

    private List<LevelBean> data;

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

    public List<LevelBean> getData() {
        return data;
    }

    public void setData(List<LevelBean> data) {
        this.data = data;
    }

    public static class LevelBean{
        private int id;
        private int level;
        private String level_name;
        private int level_full_exp;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public int getLevel_full_exp() {
            return level_full_exp;
        }

        public void setLevel_full_exp(int level_full_exp) {
            this.level_full_exp = level_full_exp;
        }
    }
}

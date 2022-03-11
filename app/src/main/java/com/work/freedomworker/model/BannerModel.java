package com.work.freedomworker.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.work.freedomworker.R;
import com.work.freedomworker.view.RoundImageView;
import com.bumptech.glide.Glide;
import com.kelin.banner.BannerEntry;

import java.io.Serializable;
import java.util.List;

public class BannerModel {

    public int code;
    public String status;


    private List<BannerBean> data;

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

    public List<BannerBean> getData() {
        return data;
    }

    public void setData(List<BannerBean> data) {
        this.data = data;
    }

    public class BannerBean implements Serializable , BannerEntry<String>{

        private int id;
        private String title;
        private int type;
        private String banner_img;
        private String content_img;
        private String url;
        private int order;

        private String full_banner_img;
        private String full_content_img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getBanner_img() {
            return banner_img;
        }

        public void setBanner_img(String banner_img) {
            this.banner_img = banner_img;
        }

        public String getContent_img() {
            return content_img;
        }

        public void setContent_img(String content_img) {
            this.content_img = content_img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getFull_banner_img() {
            return full_banner_img;
        }

        public void setFull_banner_img(String full_banner_img) {
            this.full_banner_img = full_banner_img;
        }

        public String getFull_content_img() {
            return full_content_img;
        }

        public void setFull_content_img(String full_content_img) {
            this.full_content_img = full_content_img;
        }

        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item_adverting, parent, false);
            RoundImageView imageView = (RoundImageView) view.findViewById(R.id.iv_banner_adverting);
            imageView.setRectAdius(6);
            Glide.with(parent.getContext())
                    .load(full_banner_img)
                    .into(imageView);
            return view;
        }

        @Override
        public CharSequence getTitle() {
            return null;
        }

        @Override
        public CharSequence getSubTitle() {
            return null;
        }

        @Override
        public String getValue() {
            return url;
        }

        @Override
        public boolean same(BannerEntry newEntry) {
            return false;
        }
    }
}

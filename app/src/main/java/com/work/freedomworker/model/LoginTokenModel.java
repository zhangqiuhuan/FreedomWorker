package com.work.freedomworker.model;

public class LoginTokenModel {
    public int code;
    public String status;


    private LoginTokenBean data;

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

    public LoginTokenBean getData() {
        return data;
    }

    public void setData(LoginTokenBean data) {
        this.data = data;
    }

    public class LoginTokenBean{
        String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

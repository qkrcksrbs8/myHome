package com.example.myhome.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class UserVO implements Serializable {

    private static final long serialVersionUID = -6181709364228437486L;

    private int idx;
    private String email;
    private String password;
    private String name;
    private String auth_code;
    private String email_auth;
    private List<HashMap<String, Object>> firstDepthMenu;
    private List<HashMap<String, Object>> otherDepthMenu;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getEmail_auth() {
        return email_auth;
    }

    public void setEmail_auth(String email_auth) {
        this.email_auth = email_auth;
    }

    public List<HashMap<String, Object>> getFirstDepthMenu() {
        return firstDepthMenu;
    }

    public void setFirstDepthMenu(List<HashMap<String, Object>> firstDepthMenu) {
        this.firstDepthMenu = firstDepthMenu;
    }

    public List<HashMap<String, Object>> getOtherDepthMenu() {
        return otherDepthMenu;
    }

    public void setOtherDepthMenu(List<HashMap<String, Object>> otherDepthMenu) {
        this.otherDepthMenu = otherDepthMenu;
    }
}
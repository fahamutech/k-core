package com.fahamutech.adminapp.forum.model;

public class DoctorOnlineStatus {
    public static String DOCTOR = "doctor";
    private String online;
    private String flag;

    public DoctorOnlineStatus() {

    }

    public DoctorOnlineStatus(String online, String flag) {
        this.online = online;
        this.flag = flag;
    }


    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}

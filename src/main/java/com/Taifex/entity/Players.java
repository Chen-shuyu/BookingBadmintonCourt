package com.Taifex.entity;

public class Players {

    private String name;
    private String id5;
    private String phone;
    private Integer weekNO;
    private String timeString;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId5() {
        return id5;
    }

    public void setId5(String id5) {
        this.id5 = id5;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getWeekNO() {
        return weekNO;
    }

    public void setWeekNO(Integer weekNO) {
        this.weekNO = weekNO;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    @Override
    public String toString() {
        return "Players{" +
                "name='" + name + '\'' +
                ", id5='" + id5 + '\'' +
                ", phone='" + phone + '\'' +
                ", weekNO=" + weekNO +
                ", timeString='" + timeString + '\'' +
                '}';
    }
}

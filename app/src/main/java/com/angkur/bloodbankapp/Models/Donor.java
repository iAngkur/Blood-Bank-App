package com.angkur.bloodbankapp.Models;

public class Donor {
    private String name, phone, city, bloodGroup;

    public Donor(String name, String phone, String city, String bloodGroup) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.bloodGroup = bloodGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}

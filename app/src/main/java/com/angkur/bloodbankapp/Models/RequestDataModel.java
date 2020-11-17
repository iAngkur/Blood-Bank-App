package com.angkur.bloodbankapp.Models;

import com.google.firebase.database.ServerValue;

public class RequestDataModel {
    private String posterPhoneAsId, patientName, patientCity, patientPhone, patientBloodGroup, message;
    private Object postTime;

    public RequestDataModel() {
    }

    public RequestDataModel(String posterPhoneAsId, String patientName, String patientCity, String patientPhone, String patientBloodGroup, String message) {
        this.posterPhoneAsId = posterPhoneAsId;
        this.patientName = patientName;
        this.patientCity = patientCity;
        this.patientPhone = patientPhone;
        this.patientBloodGroup = patientBloodGroup;
        this.message = message;
        this.postTime = ServerValue.TIMESTAMP;
    }

    public String getPosterPhoneAsId() {
        return posterPhoneAsId;
    }

    public void setPosterPhoneAsId(String posterPhoneAsId) {
        this.posterPhoneAsId = posterPhoneAsId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientCity() {
        return patientCity;
    }

    public void setPatientCity(String patientCity) {
        this.patientCity = patientCity;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientBloodGroup() {
        return patientBloodGroup;
    }

    public void setPatientBloodGroup(String patientBloodGroup) {
        this.patientBloodGroup = patientBloodGroup;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPostTime() {
        return postTime;
    }

    public void setPostTime(Object postTime) {
        this.postTime = postTime;
    }
}

package com.eysoft.eyhost.AdapterObjects;

import org.json.JSONObject;

/**
 * Created by EySoft IT Solution on 2/25/2016.
 */
public class TicketObject {
    String subject;
    String date;
    String status;
    JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

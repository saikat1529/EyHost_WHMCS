package com.eysoft.eyhost.AdapterObjects;

import org.json.JSONObject;

/**
 * Created by EySoft IT Solution on 2/25/2016.
 */
public class ServiceObject {
    String groupName;
    String name;
    String domainName;
    String price;
    JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getPrice() {
        return price;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getName() {
        return name;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

package com.eysoft.eyhost.AdapterObjects;

import org.json.JSONObject;

/**
 * Created by EySoft IT Solution on 2/25/2016.
 */
public class InvoiceObject {
    String dueDate;
    String invoiceNo;
    String price;
    String status;
    JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
}

package com.eysoft.eyhost.Details;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eysoft.eyhost.AsyncResponse;
import com.eysoft.eyhost.JSONExecuter;
import com.eysoft.eyhost.Utility;
import com.eysoft.eyhost.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by EySoft IT Solution on 2/29/2016.
 */
public class InvoiceDetails extends Fragment implements AsyncResponse {

    Context context;
    JSONObject jsonObject;
    JSONObject[] jsonObjectArray;
    JSONExecuter executer;
    Utility utility;
    String URL;
    TextView service_details_number,
            service_date,
            service_dom_reg,
            service_dom_desc,
            service_det_amnt,
            service_due_date,
            service_payme,
            invoice_title,
            invoice_details,
            invoice_details_invoice_date_title,
            invoice_details_domain_registration_title,
            invoice_det_dom_desc_title,
            invoice_det_amount_title,
            invoice_det_due_date_title,
            invoice_det_pay_met_title,
            invoice_det_status_title,

            service_inv_status;

    public InvoiceDetails(Context context, JSONObject jsonObject){
        this.context = context;
        this.jsonObject = jsonObject;
        executer = new JSONExecuter(context);
        executer.delegate = this;
        utility = new Utility(context);
        URL = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getinvoice" +
                "&invoiceid=" + jsonObject.optString("id") +
                "&clientid=" + utility.getUserID()+
                "&responsetype=json";
        utility.setNav();
        utility.setNavMyService(3);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invoice_details, null);
        service_details_number = (TextView)view.findViewById(R.id.invoice_details_number);
        service_date = (TextView)view.findViewById(R.id.invoice_details_invoice_date);
        service_dom_reg = (TextView)view.findViewById(R.id.invoice_details_domain_registration);
        service_dom_desc = (TextView)view.findViewById(R.id.invoice_det_dom_desc);
        service_det_amnt = (TextView)view.findViewById(R.id.invoice_det_amount);
        service_due_date = (TextView)view.findViewById(R.id.invoice_det_due_date);
        service_payme = (TextView)view.findViewById(R.id.invoice_det_pay_met);
        service_inv_status = (TextView)view.findViewById(R.id.invoice_det_status);
        invoice_title = (TextView)view.findViewById(R.id.invoice_title);
        invoice_details = (TextView)view.findViewById(R.id.invoice_details);
        invoice_details_invoice_date_title = (TextView)view.findViewById(R.id.invoice_details_invoice_date_title);
        invoice_details_domain_registration_title = (TextView)view.findViewById(R.id.invoice_details_domain_registration_title);
        invoice_det_dom_desc_title = (TextView)view.findViewById(R.id.invoice_det_dom_desc_title);
        invoice_det_amount_title = (TextView)view.findViewById(R.id.invoice_det_amount_title);
        invoice_det_due_date_title = (TextView)view.findViewById(R.id.invoice_det_due_date_title);
        invoice_det_pay_met_title = (TextView)view.findViewById(R.id.invoice_det_pay_met_title);
        invoice_det_status_title = (TextView)view.findViewById(R.id.invoice_det_status_title);
        utility.setFont(service_details_number);
        utility.setFont(service_date);
        utility.setFont(service_dom_reg);
        utility.setFont(service_dom_desc);
        utility.setFont(service_det_amnt);
        utility.setFont(service_due_date);
        utility.setFont(service_payme);
        utility.setFont(service_inv_status);
        utility.setFont(invoice_title);
        utility.setFont(invoice_details);
        utility.setFont(invoice_details_invoice_date_title);
        utility.setFont(invoice_details_domain_registration_title);
        utility.setFont(invoice_det_dom_desc_title);
        utility.setFont(invoice_det_amount_title);
        utility.setFont(invoice_det_due_date_title);
        utility.setFont(invoice_det_pay_met_title);
        utility.setFont(invoice_det_status_title);
        executer.execute(URL);
        return view;
    }

    @Override
    public void processFinish(JSONObject[] jsonObject) {
        jsonObjectArray = jsonObject;
        service_details_number.setText("#"+jsonObjectArray[0].optString("invoiceid"));
        service_date.setText(jsonObjectArray[0].optString("date"));
        String description = "";
        String type = "";
        String amount = "";
        JSONObject object = jsonObjectArray[0].optJSONObject("items");
        JSONArray jsonArray = object.optJSONArray("item");
        for(int i=0; i<jsonArray.length(); i++){
            try {
                if(i!=0){
                    type = "\n" + type;
                    description = "\n" + description;
                    amount = "\n" + amount;
                }
                JSONObject jsonObjectLocal = jsonArray.getJSONObject(i);
                type = jsonObjectLocal.optString("type");
                description = jsonObjectLocal.optString("description");
                amount = jsonObjectLocal.optString("amount");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        service_dom_reg.setText(type);
        service_dom_desc.setText(description);
        service_det_amnt.setText("$"+amount);
        service_due_date.setText(jsonObjectArray[0].optString("duedate"));
        service_payme.setText(jsonObjectArray[0].optString("paymentmethod"));
        service_inv_status.setText(jsonObjectArray[0].optString("status"));
    }
}

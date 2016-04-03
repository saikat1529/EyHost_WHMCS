package com.eysoft.eyhost.Details;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eysoft.eyhost.Utility;
import com.eysoft.eyhost.R;

import org.json.JSONObject;

/**
 * Created by EySoft IT Solution on 2/28/2016.
 */
public class ServiceDetails extends Fragment {

    Context context;
    JSONObject jsonObject;
    Utility utility;


    public ServiceDetails(Context context, JSONObject jsonObject){
        this.context = context;
        this.jsonObject = jsonObject;
        utility = new Utility(context);
        utility.setNav();
        utility.setNavMyService(1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_details, null);
        TextView service_details = (TextView)view.findViewById(R.id.ser_det_name);
        TextView service_plan = (TextView)view.findViewById(R.id.ser_det_plan);
        TextView service_package = (TextView)view.findViewById(R.id.ser_det_package);
        TextView service_reg_date = (TextView)view.findViewById(R.id.ser_det_reg_date);
        TextView service_dom = (TextView)view.findViewById(R.id.ser_det_dom);
        TextView service_amount = (TextView)view.findViewById(R.id.ser_det_rec_amn);
        TextView service_billing = (TextView)view.findViewById(R.id.ser_det_bil_cyc);
        TextView service_nextdate = (TextView)view.findViewById(R.id.ser_det_nxt_dt);
        TextView service_payment = (TextView)view.findViewById(R.id.ser_det_pay_met);
        TextView sevice_details_title = (TextView)view.findViewById(R.id.sevice_details_title);
        TextView ser_details = (TextView)view.findViewById(R.id.ser_details);
        TextView ser_det_reg_date_title = (TextView)view.findViewById(R.id.ser_det_reg_date_title);
        TextView ser_det_dom_title = (TextView)view.findViewById(R.id.ser_det_dom_title);
        TextView ser_det_rec_amn_title = (TextView)view.findViewById(R.id.ser_det_rec_amn_title);
        TextView ser_det_bil_cyc_title = (TextView)view.findViewById(R.id.ser_det_bil_cyc_title);
        TextView ser_det_nxt_dt_title = (TextView)view.findViewById(R.id.ser_det_nxt_dt_title);
        TextView ser_det_pay_met_title = (TextView)view.findViewById(R.id.ser_det_pay_met_title);
        utility.setFont(service_details);
        utility.setFont(service_plan);
        utility.setFont(service_package);
        utility.setFont(service_reg_date);
        utility.setFont(service_dom);
        utility.setFont(service_amount);
        utility.setFont(service_billing);
        utility.setFont(service_nextdate);
        utility.setFont(service_payment);
        utility.setFont(sevice_details_title);
        utility.setFont(ser_details);
        utility.setFont(ser_det_reg_date_title);
        utility.setFont(ser_det_dom_title);
        utility.setFont(ser_det_rec_amn_title);
        utility.setFont(ser_det_bil_cyc_title);
        utility.setFont(ser_det_nxt_dt_title);
        utility.setFont(ser_det_pay_met_title);
        service_details.setText(jsonObject.optString("groupname"));
        service_plan.setText("Plan 1");
        service_package.setText(jsonObject.optString("name"));
        service_reg_date.setText(jsonObject.optString("regdate"));
        service_dom.setText(jsonObject.optString("domain"));
        service_amount.setText("$"+jsonObject.optString("recurringamount"));
        service_billing.setText(jsonObject.optString("billingcycle"));
        service_nextdate.setText(jsonObject.optString("nextduedate"));
        service_payment.setText(jsonObject.optString("paymentmethodname"));
        return view;
    }
}

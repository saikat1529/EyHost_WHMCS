package com.eysoft.eyhost;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eysoft.eyhost.R;

import org.json.JSONObject;

/**
 * Created by EySoft IT Solution on 2/25/2016.
 */
public class Dashboard extends Fragment implements AsyncResponse {

    Context context;
    String fname, cname, eml, cell, cred, ser, dom, inv, tck;
    TextView fullName,
            companyName,
            email,
            cellNo,
            avlCrd,
            tot_ser,
            tot_dom,
            tot_inv,
            dash_avl_cr_title,
            dash_tot_srv_count_title,
            dash_tot_dom_count_title,
            dash_tot_inv_count_title,
            dash_tot_tck_count_title,
            tot_tck;
    JSONExecuter executer = null;
    Utility utility;
    JSONObject[] jsonObjects = null;
    private ProgressDialog pDialog;
    JSONParser parser;

    public Dashboard(Context ctx){
        context = ctx;
        executer = new JSONExecuter(context);
        executer.delegate = this;
        utility = new Utility(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_layout, null);
        parser = new JSONParser();
        fullName =(TextView)view.findViewById(R.id.dash_full_name);
        companyName = (TextView)view.findViewById(R.id.dash_company_name);
        email = (TextView)view.findViewById(R.id.dash_email);
        cellNo = (TextView)view.findViewById(R.id.dash_cell_no);
        avlCrd = (TextView)view.findViewById(R.id.dash_avl_cr);
        tot_ser = (TextView)view.findViewById(R.id.dash_tot_srv_count);
        tot_dom = (TextView)view.findViewById(R.id.dash_tot_dom_count);
        tot_inv = (TextView)view.findViewById(R.id.dash_tot_inv_count);
        tot_tck = (TextView)view.findViewById(R.id.dash_tot_tck_count);
        dash_avl_cr_title = (TextView)view.findViewById(R.id.dash_avl_cr_title);
        dash_tot_srv_count_title = (TextView)view.findViewById(R.id.dash_tot_srv_count_title);
        dash_tot_dom_count_title = (TextView)view.findViewById(R.id.dash_tot_dom_count_title);
        dash_tot_inv_count_title = (TextView)view.findViewById(R.id.dash_tot_inv_count_title);
        dash_tot_tck_count_title = (TextView)view.findViewById(R.id.dash_tot_tck_count_title);
        utility.setFont(fullName);
        utility.setFont(companyName);
        utility.setFont(email);
        utility.setFont(cellNo);
        utility.setFont(avlCrd);
        utility.setFont(tot_ser);
        utility.setFont(tot_dom);
        utility.setFont(tot_inv);
        utility.setFont(tot_tck);
        utility.setFont(dash_avl_cr_title);
        utility.setFont(dash_tot_srv_count_title);
        utility.setFont(dash_tot_dom_count_title);
        utility.setFont(dash_tot_inv_count_title);
        utility.setFont(dash_tot_tck_count_title);

        String url_info = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getclientsdetails" +
                "&clientid=" + utility.getUserID()+
                "&responsetype=json";
        String url_service = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getclientsproducts" +
                "&clientid=" + utility.getUserID() +
                "&responsetype=json";
        String url_domain = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getclientsdomains" +
                "&clientid=" + utility.getUserID() +
                "&responsetype=json";
        String url_invoice = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getinvoices" +
                "&userid=" + utility.getUserID() +
                "&responsetype=json";
        String url_tickets = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=gettickets" +
                "&clientid=" + utility.getUserID() +
                "&responsetype=json";
        executer.execute(url_info,url_service,url_domain,url_invoice,url_tickets);
        return view;
    }

    @Override
    public void processFinish(JSONObject[] jsonObject) {
        jsonObjects = jsonObject;
        fullName.setText(jsonObjects[0].optString("fullname"));
        companyName.setText(jsonObjects[0].optString("companyname"));
        email.setText("Email: "+jsonObjects[0].optString("email"));
        cellNo.setText("Cell: "+jsonObjects[0].optString("phonenumber"));
        avlCrd.setText("$"+jsonObjects[0].optString("credit"));
        tot_ser.setText(jsonObjects[1].optString("totalresults"));
        tot_dom.setText(jsonObjects[2].optString("totalresults"));
        tot_inv.setText(jsonObjects[3].optString("totalresults"));
        tot_tck.setText(jsonObjects[4].optString("totalresults"));
    }

}

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
public class DomianDetails extends Fragment {

    Context context;
    JSONObject jsonObject;
    Utility utility;

    public DomianDetails(Context context, JSONObject jsonObject){
        this.context = context;
        this.jsonObject = jsonObject;
        utility = new Utility(context);
        utility.setNav();
        utility.setNavMyService(2);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.domain_details, null);
        TextView domain_details = (TextView)view.findViewById(R.id.dom_det_dom_name);
        TextView domain_title = (TextView)view.findViewById(R.id.domain_title);
        TextView dom_details = (TextView)view.findViewById(R.id.dom_details);
        TextView domain_reg_date = (TextView)view.findViewById(R.id.dom_det_reg_date);
        TextView dom_det_reg_date_title = (TextView)view.findViewById(R.id.dom_det_reg_date_title);
        TextView domain_recuramount = (TextView)view.findViewById(R.id.dom_det_rec_amn);
        TextView dom_det_fir_pay_title = (TextView)view.findViewById(R.id.dom_det_fir_pay_title);
        TextView domain_firstamount = (TextView)view.findViewById(R.id.dom_det_fir_pay);
        TextView dom_det_rec_amn_title = (TextView)view.findViewById(R.id.dom_det_rec_amn_title);
        TextView domain_billing = (TextView)view.findViewById(R.id.dom_det_bil_cyc);
        TextView dom_det_bil_cyc_title = (TextView)view.findViewById(R.id.dom_det_bil_cyc_title);
        TextView domain_nextdate = (TextView)view.findViewById(R.id.dom_det_nxt_dt);
        TextView dom_det_nxt_dt_title = (TextView)view.findViewById(R.id.dom_det_nxt_dt_title);
        TextView domain_payment = (TextView)view.findViewById(R.id.dom_det_pay_met);
        TextView dom_det_pay_met_title = (TextView)view.findViewById(R.id.dom_det_pay_met_title);
        utility.setFont(domain_title);
        utility.setFont(domain_details);
        utility.setFont(dom_details);
        utility.setFont(domain_reg_date);
        utility.setFont(dom_det_reg_date_title);
        utility.setFont(domain_recuramount);
        utility.setFont(dom_det_fir_pay_title);
        utility.setFont(domain_firstamount);
        utility.setFont(dom_det_rec_amn_title);
        utility.setFont(domain_billing);
        utility.setFont(dom_det_bil_cyc_title);
        utility.setFont(domain_nextdate);
        utility.setFont(dom_det_nxt_dt_title);
        utility.setFont(domain_payment);
        utility.setFont(dom_det_pay_met_title);
        domain_details.setText(jsonObject.optString("domainname"));
        domain_reg_date.setText(jsonObject.optString("regdate"));
        domain_recuramount.setText("$"+jsonObject.optString("recurringamount"));
        domain_firstamount.setText("$"+jsonObject.optString("firstpaymentamount"));
        domain_billing.setText("Annually");
        domain_nextdate.setText(jsonObject.optString("nextduedate"));
        domain_payment.setText(jsonObject.optString("paymentmethodname"));
        return view;
    }
}

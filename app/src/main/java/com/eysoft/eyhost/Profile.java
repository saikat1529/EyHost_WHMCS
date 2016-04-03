package com.eysoft.eyhost;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eysoft.eyhost.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by EySoft IT Solution on 3/2/2016.
 */
public class Profile extends Fragment implements AsyncResponse {

    Context context;
    TextView date, name, company, adrs1, phn, email, credit, status, login, info, contact, others;
    JSONExecuter executer;
    Utility utility;
    JSONObject[] jsonObjects;

    public Profile(Context ctx){
        context = ctx;
        executer =  new JSONExecuter(context);
        utility = new Utility(context);
        utility.setNav();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout,null);
        date = (TextView)view.findViewById(R.id.profile_date);
        name = (TextView)view.findViewById(R.id.profile_name);
        company = (TextView)view.findViewById(R.id.profile_company_name);
        adrs1 = (TextView)view.findViewById(R.id.profile_address_one);
        phn = (TextView)view.findViewById(R.id.profile_phone);
        email = (TextView)view.findViewById(R.id.profile_email);
        credit = (TextView)view.findViewById(R.id.profile_credit);
        status = (TextView)view.findViewById(R.id.profile_status);
        login = (TextView)view.findViewById(R.id.profile_last_login);
        info = (TextView)view.findViewById(R.id.profile_information);
        contact = (TextView)view.findViewById(R.id.profile_contact);
        others = (TextView)view.findViewById(R.id.profile_others);
        utility.setFont(date);
        utility.setFont(name);
        utility.setFont(company);
        utility.setFont(adrs1);
        utility.setFont(phn);
        utility.setFont(email);
        utility.setFont(credit);
        utility.setFont(status);
        utility.setFont(login);
        utility.setFont(info);
        utility.setFont(contact);
        utility.setFont(others);
        executer.delegate = this;
        String url = getResources().getString(R.string.base_url) +
                "?username=" + getResources().getString(R.string.base_name) +
                "&password=" + getResources().getString(R.string.base_pass) +
                "&action=getclientsdetails" +
                "&clientid="+ utility.getUserID()+
                "&responsetype=json";
        executer.execute(url);
        return view;
    }


    @Override
    public void processFinish(JSONObject[] jsonObject) {
        jsonObjects = jsonObject;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(calendar.getTime());
        date.setText("Date: "+formattedDate);
        name.setText(jsonObjects[0].optString("fullname"));
        company.setText("Company: "+jsonObjects[0].optString("companyname"));
        adrs1.setText("Address: \n"+jsonObjects[0].optString("address1")+"\n"+jsonObjects[0].optString("city")+" - "+jsonObjects[0].optString("postcode")+", "+jsonObjects[0].optString("countryname"));
        phn.setText("Phone: "+jsonObjects[0].optString("phonenumber"));
        email.setText("Email: "+jsonObjects[0].optString("email"));
        credit.setText("Credit: $"+jsonObjects[0].optString("credit"));
        status.setText("Status: "+jsonObjects[0].optString("status"));
        login.setText("Last Login: \n"+jsonObjects[0].optString("lastlogin").replace("<br>","\n"));
    }
}

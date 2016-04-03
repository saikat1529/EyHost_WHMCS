package com.eysoft.eyhost;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.eysoft.eyhost.Adapter.ServiceAdapater;
import com.eysoft.eyhost.AdapterObjects.ServiceObject;
import com.eysoft.eyhost.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 2/25/2016.
 */
public class ServiceList extends Fragment implements AsyncResponse {

    Context context;
    ArrayList<ServiceObject> objects;
    Utility utility;
    JSONExecuter executer = null;
    ServiceAdapater adapater;
    ListView listView;
    View view;
    TextView noDataView;
    private FragmentActivity mycontext;

    @Override
    public void onAttach(Activity activity) {
        mycontext = (FragmentActivity)activity;
        super.onAttach(activity);
    }

    public  ServiceList(Context ctx){
        context =ctx;
        utility = new Utility(context);
        executer = new JSONExecuter(context);
        executer.delegate = this;
        objects = new ArrayList<ServiceObject>();
        generateObject();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_layout,null);
        listView = (ListView)view.findViewById(R.id.list_item);
        noDataView = (TextView)view.findViewById(R.id.no_data_list);
        return view;
    }

    public void generateObject(){
        String URL = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getclientsproducts" +
                "&clientid=" + utility.getUserID() +
                "&responsetype=json";
        executer.execute(URL);
    }

    @Override
    public void processFinish(JSONObject[] jsonObject) {
        if(jsonObject[0].optInt("totalresults")>0) {
            listView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
            JSONObject productObject = null;
            JSONArray productArray = null;
            try {
                productObject = jsonObject[0].getJSONObject("products");
                productArray = productObject.getJSONArray("product");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int count = productArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject object = productArray.optJSONObject(i);
                ServiceObject serviceObject = new ServiceObject();
                serviceObject.setGroupName(object.optString("groupname"));
                serviceObject.setDomainName(object.optString("domain"));
                serviceObject.setName(object.optString("name"));
                serviceObject.setPrice("Price: $" + object.optString("recurringamount"));
                serviceObject.setJsonObject(object);
                objects.add(serviceObject);
            }
            adapater = new ServiceAdapater(context, objects);
            listView.setAdapter(adapater);
        }
        else{
            listView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
        }
    }
}

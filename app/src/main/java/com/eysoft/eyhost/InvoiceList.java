package com.eysoft.eyhost;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.eysoft.eyhost.Adapter.InvoiceAdapter;
import com.eysoft.eyhost.AdapterObjects.InvoiceObject;
import com.eysoft.eyhost.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 2/25/2016.
 */
public class InvoiceList extends Fragment implements AsyncResponse {

    Context context;
    ArrayList<InvoiceObject> objects;
    Utility utility;
    JSONExecuter executer = null;
    InvoiceAdapter adapater;
    ListView listView;
    View view;
    TextView noDataView;

    public InvoiceList(Context ctx){
        context =ctx;
        utility = new Utility(context);
        utility.setNav();
        executer = new JSONExecuter(context);
        executer.delegate = this;
        objects = new ArrayList<InvoiceObject>();
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

    @Override
    public void processFinish(JSONObject[] jsonObject) {
        if(jsonObject[0].optInt("totalresults")>0) {
            listView.setVisibility(View.VISIBLE);
            noDataView.setVisibility(View.GONE);
            JSONObject productObject = null;
            JSONArray productArray = null;
            try {
                productObject = jsonObject[0].getJSONObject("invoices");
                productArray = productObject.getJSONArray("invoice");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int count = productArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject object = productArray.optJSONObject(i);
                InvoiceObject serviceObject = new InvoiceObject();
                serviceObject.setDueDate("Due Date: " + object.optString("duedate"));
                serviceObject.setInvoiceNo("Invoice No: #" + object.optString("id"));
                serviceObject.setPrice("Price: $" + object.optString("subtotal"));
                serviceObject.setStatus("Status: " + object.optString("status"));
                serviceObject.setJsonObject(object);
                objects.add(serviceObject);
            }
            adapater = new InvoiceAdapter(context, objects);
            listView.setAdapter(adapater);
        }
        else{
            listView.setVisibility(View.GONE);
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    public void generateObject(){
        String URL = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getinvoices" +
                "&userid=" + utility.getUserID() +
                "&responsetype=json";
        executer.execute(URL);
    }
}

package com.eysoft.eyhost.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.eysoft.eyhost.AdapterObjects.ServiceObject;
import com.eysoft.eyhost.MainActivity;
import com.eysoft.eyhost.Utility;
import com.eysoft.eyhost.R;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 2/25/2016.
 */
public class ServiceAdapater extends BaseAdapter {

    Context context;
    ArrayList<ServiceObject> objects;
    Utility utility;


    public  ServiceAdapater(Context ctx, ArrayList<ServiceObject> objects){
        context = ctx;
        this.objects = objects;
        utility = new Utility(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater =  (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.service_listview_child,null);
        }
        TextView serviceName = (TextView)view.findViewById(R.id.service_listchild_name);
        TextView domainName = (TextView)view.findViewById(R.id.service_listchild_domain);
        TextView price = (TextView)view.findViewById(R.id.service_listchild_price);
        Button viewBtn = (Button)view.findViewById(R.id.service_view_details);
        utility.setFont(serviceName);
        utility.setFont(domainName);
        utility.setFont(price);
        utility.setFont(viewBtn);
        serviceName.setText(objects.get(i).getGroupName()+" - "+objects.get(i).getName());
        domainName.setText(objects.get(i).getDomainName());
        price.setText(objects.get(i).getPrice());
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("value","service");
                intent.putExtra("json", String.valueOf(objects.get(i).getJsonObject()));
                context.startActivity(intent);
            }
        });
        return view;
    }
}

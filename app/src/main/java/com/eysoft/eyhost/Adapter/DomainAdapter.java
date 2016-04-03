package com.eysoft.eyhost.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.eysoft.eyhost.AdapterObjects.DomainObject;
import com.eysoft.eyhost.MainActivity;
import com.eysoft.eyhost.Utility;
import com.eysoft.eyhost.R;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 2/25/2016.
 */
public class DomainAdapter extends BaseAdapter {

    Context context;
    ArrayList<DomainObject> domainObjects = null;
    Utility utility;

    public DomainAdapter(Context ctx, ArrayList<DomainObject> objects){
        context = ctx;
        domainObjects = objects;
        utility = new Utility(context);
    }

    @Override
    public int getCount() {
        return domainObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return domainObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater lf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lf.inflate(R.layout.domain_listview_child,null);
        }
        TextView domainName = (TextView)view.findViewById(R.id.domain_list_child_domain);
        TextView dueDate = (TextView)view.findViewById(R.id.domain_list_child_date);
        TextView status = (TextView)view.findViewById(R.id.domain_list_child_status);
        Button viewDetails = (Button)view.findViewById(R.id.domain_list_view_details);
        utility.setFont(domainName);
        utility.setFont(dueDate);
        utility.setFont(status);
        utility.setFont(viewDetails);
        domainName.setText(domainObjects.get(i).getDomain());
        dueDate.setText(domainObjects.get(i).getDueDate());
        status.setText(domainObjects.get(i).getStatus());
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("value","domain");
                intent.putExtra("json", String.valueOf(domainObjects.get(i).getJsonObject()));
                context.startActivity(intent);
            }
        });
        return view;
    }
}

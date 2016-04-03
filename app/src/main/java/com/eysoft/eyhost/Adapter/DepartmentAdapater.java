package com.eysoft.eyhost.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eysoft.eyhost.AdapterObjects.Department;
import com.eysoft.eyhost.Utility;
import com.eysoft.eyhost.R;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 3/1/2016.
 */
public class DepartmentAdapater extends BaseAdapter {

    Context context;
    ArrayList<Department> departments;
    Utility utility;

    public DepartmentAdapater(Context ctx, ArrayList<Department> departments){
        context = ctx;
        this.departments = departments;
        utility = new Utility(context);
    }


    @Override
    public int getCount() {
        return departments.size();
    }

    @Override
    public Object getItem(int i) {
        return departments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater lf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lf.inflate(R.layout.spinner,null);
        }
        TextView textView = (TextView)view.findViewById(R.id.spinner_child);
        textView.setText(departments.get(i).getName());
        utility.setFont(textView);
        return view;
    }
}

package com.eysoft.eyhost.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eysoft.eyhost.AdapterObjects.Priority;
import com.eysoft.eyhost.Utility;
import com.eysoft.eyhost.R;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 3/1/2016.
 */
public class PriorityAdapter extends BaseAdapter {

    Context context;
    ArrayList<Priority> priorities;
    Utility utility;

    public PriorityAdapter(Context ctx, ArrayList<Priority> priorities){
        context = ctx;
        this.priorities = priorities;
        utility = new Utility(context);
    }

    @Override
    public int getCount() {
        return priorities.size();
    }

    @Override
    public Object getItem(int i) {
        return priorities.get(i);
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
        textView.setText(priorities.get(i).getName());
        utility.setFont(textView);
        return view;
    }
}

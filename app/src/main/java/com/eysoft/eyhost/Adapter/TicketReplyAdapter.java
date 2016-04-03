package com.eysoft.eyhost.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eysoft.eyhost.AdapterObjects.TicketReply;
import com.eysoft.eyhost.Utility;
import com.eysoft.eyhost.R;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 2/29/2016.
 */
public class TicketReplyAdapter extends BaseAdapter {

    Context context;
    ArrayList<TicketReply> objects;
    Utility utility;


    public  TicketReplyAdapter(Context ctx, ArrayList<TicketReply> objects){
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater =  (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.get_ticket_child,null);
        }
        TextView title = (TextView)view.findViewById(R.id.get_ticket_child_title);
        TextView message = (TextView)view.findViewById(R.id.get_ticket_child_message);
        utility.setFont(title);
        utility.setFont(message);
        title.setText(objects.get(i).getName_date());
        message.setText(objects.get(i).getMessage());
        return view;
    }
}

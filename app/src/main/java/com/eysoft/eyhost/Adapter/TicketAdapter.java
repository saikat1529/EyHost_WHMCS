package com.eysoft.eyhost.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.eysoft.eyhost.AdapterObjects.TicketObject;
import com.eysoft.eyhost.MainActivity;
import com.eysoft.eyhost.Utility;
import com.eysoft.eyhost.R;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 2/25/2016.
 */
public class TicketAdapter extends BaseAdapter {

    Context context;
    ArrayList<TicketObject> ticketObjects = null;
    Utility utility;

    public TicketAdapter(Context ctx, ArrayList<TicketObject> ticketObjects){
        context = ctx;
        this.ticketObjects = ticketObjects;
        utility = new Utility(context);
    }

    @Override
    public int getCount() {
        return ticketObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return ticketObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater lf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lf.inflate(R.layout.ticket_listview_child,null);
        }
        TextView ticketSub = (TextView)view.findViewById(R.id.ticket_listchild_subject);
        TextView ticketDate = (TextView)view.findViewById(R.id.ticket_listchild_date);
        TextView ticketStatus = (TextView)view.findViewById(R.id.ticket_listchild_status);
        Button ticketViewDetails = (Button)view.findViewById(R.id.ticket_view_details);
        utility.setFont(ticketSub);
        utility.setFont(ticketDate);
        utility.setFont(ticketStatus);
        utility.setFont(ticketViewDetails);
        ticketSub.setText(ticketObjects.get(i).getSubject());
        ticketDate.setText(ticketObjects.get(i).getDate());
        if(ticketObjects.get(i).getStatus().equals("Status: Customer-Reply")) {
            ticketStatus.setText("Status: Answered");
        }
        else if(ticketObjects.get(i).getStatus().equals("Status: Answered")) {
            ticketStatus.setText("Status: Staff-Reply");
        }
        else{
            ticketStatus.setText(ticketObjects.get(i).getStatus());
        }
        ticketViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("value","ticket");
                intent.putExtra("json", String.valueOf(ticketObjects.get(i).getJsonObject()));
                context.startActivity(intent);
            }
        });
        return view;
    }
}

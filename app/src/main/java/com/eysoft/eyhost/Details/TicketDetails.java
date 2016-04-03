package com.eysoft.eyhost.Details;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.eysoft.eyhost.Adapter.TicketReplyAdapter;
import com.eysoft.eyhost.AdapterObjects.TicketReply;
import com.eysoft.eyhost.AsyncResponse;
import com.eysoft.eyhost.JSONExecuter;
import com.eysoft.eyhost.TicketList;
import com.eysoft.eyhost.Utility;
import com.eysoft.eyhost.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 2/29/2016.
 */
public class TicketDetails extends Fragment implements AsyncResponse {

    Context context;
    JSONObject jsonObject;
    TextView date,dept,priority,status,subject;
    ListView ticketReply;
    String DeptURL, ReplyURL, ClientDetials;
    final JSONExecuter executer;
    JSONObject[] jsonObjectArray;
    String tid, deptid;
    Utility utility;
    String user_fullname;
    ArrayList<TicketReply> list;
    Button reply, close, back;
    boolean flag = true;

    public TicketDetails(Context context, JSONObject jsonObject){
        this.context = context;
        this.jsonObject = jsonObject;
        executer = new JSONExecuter(context);
        executer.delegate = this;
        deptid = jsonObject.optString("deptid");
        tid = jsonObject.optString("id");
        utility = new Utility(context);
        list = new ArrayList<TicketReply>();
        utility.setNav();
        utility.setNavMyService(4);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.get_ticket, null);
        date = (TextView)view.findViewById(R.id.get_ticket_date);
        subject = (TextView)view.findViewById(R.id.get_ticket_subject);
        dept = (TextView)view.findViewById(R.id.get_ticket_department);
        priority = (TextView)view.findViewById(R.id.get_ticket_priority);
        status = (TextView)view.findViewById(R.id.get_ticket_status);
        ticketReply = (ListView)view.findViewById(R.id.get_reply_list);
        reply = (Button)view.findViewById(R.id.get_ticket_reply);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationView view1 =  (NavigationView)((Activity)context).findViewById(R.id.shitstuff);
                utility.showReplyDialog(tid, jsonObject, view1);
            }
        });
        close = (Button)view.findViewById(R.id.get_ticket_close);
        back = (Button)view.findViewById(R.id.get_ticket_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicketList ticketList = new TicketList(context);
                ((Activity) context)
                        .getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerView, ticketList)
                        .addToBackStack(null)
                        .commit();
            }
        });
        utility.setFont(date);
        utility.setFont(subject);
        utility.setFont(dept);
        utility.setFont(priority);
        utility.setFont(status);
        utility.setFont(ticketReply);
        utility.setFont(reply);
        utility.setFont(close);
        utility.setFont(back);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utility.closeTicket(tid);
            }
        });

        date.setText(jsonObject.optString("date"));
        DeptURL = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getsupportdepartments" +
                "&ignore_dept_assignments=true" +
                "&responsetype=json";
        ReplyURL = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getticket" +
                "&ticketid=" + jsonObject.optString("id") +
                "&responsetype=json";
        ClientDetials = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getclientsdetails" +
                "&clientid=" + utility.getUserID() +
                "&responsetype=json";
        priority.setText(jsonObject.optString("priority"));
        subject.setText(jsonObject.optString("subject"));
        //status.setText(jsonObject.optString("status"));
        flag = true;
        executer.execute(DeptURL,ReplyURL,ClientDetials);
        return view;
    }

    @Override
    public void processFinish(JSONObject[] jsonObject) {
        jsonObjectArray = jsonObject;
        if(flag) {
            setDepartment(jsonObjectArray[0]);
            user_fullname = jsonObjectArray[2].optString("fullname");
            setListView(jsonObjectArray[1]);
            if(jsonObjectArray[1].optString("status").equals("Customer-Reply")){
                status.setText("Answered");
            }
            else if(jsonObjectArray[1].optString("status").equals("Answered")){
                status.setText("Staff-Reply");
            }
            else{
                status.setText(jsonObjectArray[1].optString("status"));
            }

        } else {
            utility.showToast("Ticket closed successfully");
        }
    }

    private void setDepartment(JSONObject jsonObject){
        JSONObject object = jsonObject.optJSONObject("departments");
        JSONArray jsonArray = object.optJSONArray("department");
        for(int i=0; i<jsonArray.length(); i++){
            try {
                JSONObject localObject = jsonArray.getJSONObject(i);
                if(localObject.optString("id").equals(deptid)){
                    dept.setText(localObject.optString("name"));
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setListView(JSONObject jsonObject) {
        JSONObject object = jsonObject.optJSONObject("replies");
        JSONArray jsonArray = object.optJSONArray("reply");
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject localObject = null;
            try {
                localObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TicketReply ticketReply = new TicketReply();
            if(localObject.optString("userid").equals(String.valueOf(utility.getUserID()))) {
                ticketReply.setName_date(user_fullname + " | " +localObject.optString("date"));
            }
            else{
                ticketReply.setName_date("Staff | " +localObject.optString("date"));
            }
            ticketReply.setMessage(localObject.optString("message"));
            list.add(ticketReply);
        }
        TicketReplyAdapter adapter = new TicketReplyAdapter(context,list);
        ticketReply.setAdapter(adapter);
    }

}

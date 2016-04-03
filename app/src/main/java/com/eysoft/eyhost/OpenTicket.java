package com.eysoft.eyhost;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.eysoft.eyhost.Adapter.DepartmentAdapater;
import com.eysoft.eyhost.Adapter.PriorityAdapter;
import com.eysoft.eyhost.AdapterObjects.Department;
import com.eysoft.eyhost.AdapterObjects.Priority;
import com.eysoft.eyhost.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 3/1/2016.
 */
public class OpenTicket extends Fragment implements AsyncResponse {

    Context context;
    JSONObject[] jsonObjects;
    JSONExecuter executer;
    Utility utility;
    String keyword;
    ArrayList<Department> departments;
    DepartmentAdapater adapater;
    Spinner prioSpinner, deptSpinner;
    EditText subject, message;
    TextView openTicket, priority, department;
    ArrayList<Priority> priorities;
    PriorityAdapter adapterPrio;
    String[] priorityString;
    Button btn;
    String[][] deptArray;

    public OpenTicket(Context ctx){
        context = ctx;
        executer = new JSONExecuter(context);
        executer.delegate = this;
        utility = new Utility(context);
        utility.setNav();
        departments = new ArrayList<Department>();
        priorities = new ArrayList<Priority>();
        priorityString = context.getResources().getStringArray(R.array.priority);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.open_ticket_layout,null);
        prioSpinner = (Spinner)view.findViewById(R.id.open_ticket_spinner_priority);
        deptSpinner = (Spinner)view.findViewById(R.id.open_ticket_spinner_dept);
        subject = (EditText)view.findViewById(R.id.open_ticket_subject);
        message = (EditText)view.findViewById(R.id.open_ticket_message);
        btn =  (Button)view.findViewById(R.id.open_ticket_submit);
        priority = (TextView)view.findViewById(R.id.open_priority);
        department = (TextView)view.findViewById(R.id.open_department);
        openTicket = (TextView)view.findViewById(R.id.open_ticket_title);
        utility.setFont(subject);
        utility.setFont(message);
        utility.setFont(btn);
        utility.setFont(openTicket);
        utility.setFont(priority);
        utility.setFont(department);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textViewDept = (TextView)deptSpinner.getSelectedView();
                String deptName = textViewDept.getText().toString();
                String deptId = null;
                String subjectvalue = subject.getText().toString();
                subjectvalue = subjectvalue.replace(" ", "%20");
                subjectvalue = subjectvalue.replace("\n","%20");
                String messagevalue = message.getText().toString();
                TextView textViewPrio = (TextView)prioSpinner.getSelectedView();
                String prioName = textViewPrio.getText().toString().toLowerCase();
                messagevalue = messagevalue.replace(" ", "%20");
                messagevalue = messagevalue.replace("\n","%20");
                for(int i=0; i<deptArray.length;i++){
                    if(deptName.equals(deptArray[i][1])){
                        deptId = deptArray[i][0];
                    }
                }
                utility.openTicket(deptId, subjectvalue, messagevalue,prioName);
            }
        });
        setSpinnerValue();
        return view;
    }

    private void setSpinnerValue(){
        for(int i=0; i<priorityString.length; i++){
            Priority priority = new Priority();
            priority.setName(priorityString[i]);
            priorities.add(priority);
        }
        adapterPrio = new PriorityAdapter(context, priorities);
        prioSpinner.setAdapter(adapterPrio);
        keyword = "spinner";
        String url_department = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=getsupportdepartments" +
                "&ignore_dept_assignments=true" +
                "&responsetype=json";
        executer.execute(url_department);
    }

    @Override
    public void processFinish(JSONObject[] jsonObject) {
        jsonObjects = jsonObject;
        if(keyword.equals("spinner")){
            JSONObject object = null;
            JSONArray jsonArray = null;
            try {
                object = jsonObjects[0].getJSONObject("departments");
                jsonArray = object.getJSONArray("department");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            deptArray = new String[jsonArray.length()][2];
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject objectLocal = null;
                try {
                    objectLocal = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Department department = new Department();
                department.setId(Integer.parseInt(objectLocal.optString("id")));
                department.setName(objectLocal.optString("name"));
                deptArray[i][0]=objectLocal.optString("id");
                deptArray[i][1]=objectLocal.optString("name");
                departments.add(department);
            }
            adapater = new DepartmentAdapater(context,departments);
            deptSpinner.setAdapter(adapater);
        }
        else if (keyword.equals("openticket")){
            utility.showToast("Tikcet Opened");
        }
    }
}

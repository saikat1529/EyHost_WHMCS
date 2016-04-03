package com.eysoft.eyhost;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eysoft.eyhost.Details.TicketDetails;
import com.eysoft.eyhost.R;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by EySoft IT Solution on 2/24/2016.
 */
public class Utility implements AsyncResponse {

    Context context;
    private ProgressDialog pDialog;
    JSONParser jsonParser;
    String main_url = "";
    String main_method = "";
    List<Pair<String,String>> main_param = null;
    JSONObject jsonObject;
    String fileName = "login_status";
    SharedPreferences preferences;
    JSONExecuter executer;
    String keyword = null;
    JSONObject jsonObjectReply = null;
    NavigationView navigationView;

    public Utility(Context ctx, NavigationView navigationView){
        context = ctx;
        this.navigationView = navigationView;
    }

    public Utility(Context ctx){
        context = ctx;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public void hideKeyBoard(){
        View view = ((Activity)context).getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow((view.getWindowToken()), 0);
        }
    }

    public boolean checkPrefFile(){
        File file  = new File("data/data/com.androidbelieve.drawerwithswipetabs/shared_prefs/"+fileName+".xml");
        if(file.exists())
            return true;
        else
            return false;
    }

    public void updatePreFile(String status, int clientid, String name, String company){
        SharedPreferences sharedPreferences = ((Activity)context).getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("status",status);
        editor.putInt("clientid", clientid);
        editor.putString("name", name);
        editor.putString("company", company);
        editor.commit();
    }

    public boolean checkLogInStatus(){
        SharedPreferences sharedPreferences = ((Activity)context).getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String status = sharedPreferences.getString("status", null);
        if(status!=null){
            if(status.equals("yes"))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    public int getUserID(){
        SharedPreferences sharedPreferences = ((Activity)context).getSharedPreferences(fileName, Context.MODE_PRIVATE);
        int clientid = sharedPreferences.getInt("clientid", 0);
        return clientid;
    }

    public String getUserFullname(){
        SharedPreferences sharedPreferences = ((Activity)context).getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String clientid = sharedPreferences.getString("name", "");
        return clientid;
    }

    public String getUserCompany(){
        SharedPreferences sharedPreferences = ((Activity)context).getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String clientid = sharedPreferences.getString("company", "");
        return clientid;
    }

    public void setNav(){
        NavigationView view = (NavigationView)((Activity)context).findViewById(R.id.shitstuff);
        View view1 = view.getHeaderView(0);
        TextView name = (TextView)view1.findViewById(R.id.header_name);
        TextView company = (TextView)view1.findViewById(R.id.header_company);
        name.setText(getUserFullname());
        company.setText(getUserCompany());
        setFont(name);
        setFont(company);
    }

    public void setNavMyService(int i){
        NavigationView view = (NavigationView)((Activity)context).findViewById(R.id.shitstuff);
        view.getMenu().getItem(i).setChecked(true);
    }

    public void closeTicket(String tid){
        executer = new JSONExecuter(context);
        executer.delegate = this;
        String CloseLink = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=updateticket" +
                "&ticketid=" + tid +
                "&status=Closed" +
                "&responsetype=json";
        keyword = "close";
        executer.execute(CloseLink);
    }

    public void openTicket(String tid){
        executer = new JSONExecuter(context);
        executer.delegate = this;
        String CloseLink = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=updateticket" +
                "&ticketid=" + tid +
                "&status=Open" +
                "&responsetype=json";
        executer.execute(CloseLink);
    }


    @Override
    public void processFinish(JSONObject[] jsonObject) {
        JSONObject[] jsonObjects = jsonObject;
        if(jsonObjects[0].optString("result").equals("success")){
            if(keyword.equals("reply")) {
                TicketDetails ticketDetails = new TicketDetails(context, jsonObjectReply);
                ((Activity) context)
                        .getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerView, ticketDetails)
                        .addToBackStack(null)
                        .commit();
            }
            if(keyword.equals("close")) {
                TicketList ticketDetails = new TicketList(context);
                ((Activity) context)
                        .getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerView, ticketDetails)
                        .addToBackStack(null)
                        .commit();
            }
            if(keyword.equals("open")) {
                TicketList ticketDetails = new TicketList(context);
                ((Activity) context)
                        .getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerView, ticketDetails)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }


    public int[] getScreen(){
        int[] value = new int[2];
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        value[0] = size.x;
        value[1] = size.y;
        return value;
    }

    public void submitReply(String tid, String message){
        executer = new JSONExecuter(context);
        executer.delegate = this;
        message = message.replaceAll(" ","%20");
        message = message.replaceAll("\n","%20");
        message = message.replace("n't","%20not");
        message = message.replace("[({^a-zA-Z0-9})]+'","");
        String CloseLink = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=addticketreply" +
                "&ticketid=" + tid +
                "&clientid=" + getUserID() +
                "&message=" + message +
                "&responsetype=json";
        executer.execute(CloseLink);
    }

    public boolean showReplyDialog(final String tid, final JSONObject jsonObject, NavigationView view){
        boolean flag = false;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.reply_ticket);
        final EditText editText = (EditText)dialog.findViewById(R.id.reply_ticket_message);
        Button button = (Button)dialog.findViewById(R.id.reply_ticket_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String message = editText.getText().toString();
                if ( message.length() > 0) {
                    submitReply(tid, message);
                    jsonObjectReply = jsonObject;
                    keyword = "reply";
                } else {
                    showToast("Please Write Your Message");
                }
            }
        });
        dialog.setCancelable(true);
        int[] value = getScreen();
        Window window = dialog.getWindow();
        window.setLayout((value[0] / 10) * 8, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        return flag;
    }

    public void openTicket(String deptId, String subjectvalue, String messagevalue, String prioName){
        executer = new JSONExecuter(context);
        executer.delegate = this;
        String OpenTicket = context.getResources().getString(R.string.base_url) +
                "?username=" + context.getResources().getString(R.string.base_name) +
                "&password=" + context.getResources().getString(R.string.base_pass) +
                "&action=OpenTicket" +
                "&clientid=" + getUserID() +
                "&deptid=" + deptId +
                "&subject=" + subjectvalue +
                "&message=" + messagevalue +
                "&priority=" + prioName +
                "&responsetype=json";
        keyword="open";
        executer.execute(OpenTicket);
    }

    public void setFont(View view){
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),context.getResources().getString(R.string.font));
        if(view instanceof TextView){
            TextView textView = (TextView)view;
            textView.setTypeface(customFont);
        }
        else if(view instanceof Button){
            Button button = (Button)view;
            button.setTypeface(customFont);
        }
        else if(view instanceof EditText){
            EditText editText = (EditText)view;
            editText.setTypeface(customFont);
        }


    }


}

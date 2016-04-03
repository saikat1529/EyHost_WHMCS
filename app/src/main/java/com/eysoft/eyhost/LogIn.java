package com.eysoft.eyhost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eysoft.eyhost.R;

import org.json.JSONObject;


/**
 * Created by EySoft IT Solution on 2/24/2016.
 */
public class LogIn extends Activity implements AsyncResponse {

    private ProgressDialog pDialog;
    JSONParser jsonParser;
    String main_url = "";
    String main_url2 = "";
    JSONObject[] jsonObjectOur = null;
    EditText username, password;
    Button signin;
    Utility utility;
    JSONExecuter jsonExecuter = new JSONExecuter(LogIn.this);
    String fileName = "login_status";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        jsonExecuter.delegate = this;
        utility = new Utility(LogIn.this);
        username = (EditText)findViewById(R.id.login_username);
        password = (EditText)findViewById(R.id.login_password);
        signin = (Button)findViewById(R.id.login_signin);
        utility.setFont(username);
        utility.setFont(password);
        utility.setFont(signin);
        /*
        * This listener is submitting data for login validation
        * */
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utility.hideKeyBoard();
                int usernameLength = username.getText().toString().length();
                int passwordLength = password.getText().toString().length();
                if(usernameLength>0&&passwordLength>0) {
                    jsonParser = new JSONParser();
                    main_url = getApplicationContext().getResources().getString(R.string.base_url) +
                            "?username=" + getApplicationContext().getResources().getString(R.string.base_name) +
                            "&password=" + getApplicationContext().getResources().getString(R.string.base_pass) +
                            "&action=validatelogin" +
                            "&email=" + username.getText().toString() +
                            "&password2=" + password.getText().toString() +
                            "&responsetype=json";
/*                String url_info = getApplicationContext().getResources().getString(R.string.base_url) +
                        "?username=" + getApplicationContext().getResources().getString(R.string.base_name) +
                        "&password=" + getApplicationContext().getResources().getString(R.string.base_pass) +
                        "&action=getclientsdetails" +
                        "&clientid=" + utility.getUserID()+
                        "&responsetype=json";*/
                    username.setText("");
                    password.setText("");
                    jsonExecuter.execute(main_url);
                }
                else{
                    utility.showToast("Please enter credential");
                }
            }
        });
    }

    /*
    * This is from Interface. When AsyncTask is finished his job
    * Then this function is triggered out
    * */
    @Override
    public void processFinish(JSONObject[] jsonObject) {
        this.jsonObjectOur = jsonObject;
        if(jsonObjectOur[0].optString("result").equals("success")){
            Intent intent = new Intent(LogIn.this, MainActivity.class);
            startActivity(intent);
            utility.updatePreFile("yes", Integer.parseInt(jsonObjectOur[0].optString("userid")), "", "");
            finish();
        }
        else{
            utility.showToast("Wrong Credential entered");
            Intent i = new Intent(LogIn.this, LogIn.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
}

package com.eysoft.eyhost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eysoft.eyhost.R;


/**
 * Created by EySoft IT Solution on 2/24/2016.
 */
public class NoInternet extends Activity {

    Utility utility;
    Button btn;
    TextView noNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonet_layout);
        utility = new Utility(getApplicationContext());
        btn = (Button)findViewById(R.id.nonet_try_again);
        noNet = (TextView)findViewById(R.id.no_net_message);
        utility.setFont(btn);
        utility.setFont(noNet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * When button clicked application check internet state.
                * If internet found then it takes user to LogIn Page.
                * Else do nothing.
                * */
                if(utility.isNetworkAvailable()){
                    Intent intent = new Intent(NoInternet.this,LogIn.class);
                    startActivity(intent);
                }
            }
        });
    }
}

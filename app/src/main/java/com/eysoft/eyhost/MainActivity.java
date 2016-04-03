package com.eysoft.eyhost;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eysoft.eyhost.Details.DomianDetails;
import com.eysoft.eyhost.Details.InvoiceDetails;
import com.eysoft.eyhost.Details.ServiceDetails;
import com.eysoft.eyhost.Details.TicketDetails;
import com.eysoft.eyhost.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    android.app.FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Utility utility;
    JSONExecuter jsonExecuter = new JSONExecuter(MainActivity.this);
    JSONObject[] jsonObjects = null;
    TextView headerName, headerCompany;
    String jsonData;
    String link;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utility = new Utility(MainActivity.this);
        jsonExecuter.delegate = this;

        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

         /*
        * This function is called to set dynamic data on navigation menu
        * */
        setNavData();

        applyFontToMenuItem(mNavigationView.getMenu().getItem(0));
        applyFontToMenuItem(mNavigationView.getMenu().getItem(1));
        applyFontToMenuItem(mNavigationView.getMenu().getItem(2));
        applyFontToMenuItem(mNavigationView.getMenu().getItem(3));
        applyFontToMenuItem(mNavigationView.getMenu().getItem(4));
        applyFontToMenuItem(mNavigationView.getMenu().getItem(5));
        applyFontToMenuItem(mNavigationView.getMenu().getItem(6));


        if(getIntent().hasExtra("json")){
            keyword = "json";
            jsonData = getIntent().getExtras().getString("json");
            if(jsonData.length()>0){
                try {
                    JSONObject jsonObjectLocal = new JSONObject(jsonData);
                    if(getIntent().getExtras().getString("value").equals("service")) {
                        mFragmentTransaction.replace(R.id.containerView, new ServiceDetails(MainActivity.this, jsonObjectLocal)).commit();
                    }
                    else if (getIntent().getExtras().getString("value").equals("domain")){
                        mFragmentTransaction.replace(R.id.containerView, new DomianDetails(MainActivity.this, jsonObjectLocal)).commit();
                    }
                    else if (getIntent().getExtras().getString("value").equals("invoice")){
                        mFragmentTransaction.replace(R.id.containerView, new InvoiceDetails(MainActivity.this, jsonObjectLocal)).commit();
                    }
                    else if (getIntent().getExtras().getString("value").equals("ticket")){
                        mFragmentTransaction.replace(R.id.containerView, new TicketDetails(MainActivity.this, jsonObjectLocal)).commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(getIntent().hasExtra("ticket")){
            mFragmentTransaction.replace(R.id.containerView, new TicketList(MainActivity.this)).commit();
            mNavigationView.getMenu().getItem(4).setChecked(true);
        }
        else if(getIntent().hasExtra("open")){
            mFragmentTransaction.replace(R.id.containerView, new TicketList(MainActivity.this)).commit();
            mNavigationView.getMenu().getItem(4).setChecked(true);
        }
        else if(getIntent().hasExtra("reply")){
            jsonData = getIntent().getExtras().getString("jsonReply");
            JSONObject jsonObjectLocal = null;
            try {
                jsonObjectLocal = new JSONObject(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mFragmentTransaction.replace(R.id.containerView, new TicketDetails(MainActivity.this, jsonObjectLocal)).commit();
        }

            /**
             *Setup the DrawerLayout and NavigationView
             */







            /**
             * Setup click events on the Navigation View Items.
             */

            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    mDrawerLayout.closeDrawers();


                    if (menuItem.getItemId() == R.id.nav_item_dashboard) {
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, new Dashboard(MainActivity.this)).commit();
                        mNavigationView.getMenu().getItem(0).setChecked(true);
                    }

                    if (menuItem.getItemId() == R.id.nav_item_service) {
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, new ServiceList(MainActivity.this)).commit();
                        mNavigationView.getMenu().getItem(1).setChecked(true);
                    }

                    if (menuItem.getItemId() == R.id.nav_item_domain) {
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, new DomainList(MainActivity.this)).commit();
                        mNavigationView.getMenu().getItem(2).setChecked(true);
                    }

                    if (menuItem.getItemId() == R.id.nav_item_myinvoices) {
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, new InvoiceList(MainActivity.this)).commit();
                        mNavigationView.getMenu().getItem(3).setChecked(true);
                    }

                    if (menuItem.getItemId() == R.id.nav_item_ticket) {
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, new TicketList(MainActivity.this)).commit();
                        mNavigationView.getMenu().getItem(4).setChecked(true);
                    }

                    if (menuItem.getItemId() == R.id.nav_open_ticket) {
                        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, new OpenTicket(MainActivity.this)).commit();
                        mNavigationView.getMenu().getItem(5).setChecked(true);
                    }

                    if (menuItem.getItemId() == R.id.nav_item_logout) {
                        Intent intent = new Intent(MainActivity.this, LogIn.class);
                        startActivity(intent);
                        utility.updatePreFile("no", 0, "", "");
                    }

                    return false;
                }

            });

            /**
             * Setup Drawer Toggle of the Toolbar
             */

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            TextView toolbarTitle = (TextView)toolbar.findViewById(R.id.toolbar_title);
            utility.setFont(toolbarTitle);
            ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                    R.string.app_name);

            mDrawerLayout.setDrawerListener(mDrawerToggle);

            mDrawerToggle.syncState();

    }

    private void setNavData() {
        View view = mNavigationView.getHeaderView(0);
        RelativeLayout relativelayout = (RelativeLayout)view.findViewById(R.id.header);
        TextView title = (TextView)relativelayout.findViewById(R.id.header_name);
        TextView compnay = (TextView)relativelayout.findViewById(R.id.header_company);
        utility.setFont(title);
        utility.setFont(compnay);
        relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, new Profile(MainActivity.this)).commit();
                mNavigationView.getMenu().getItem(0).setChecked(false);
                mNavigationView.getMenu().getItem(1).setChecked(false);
                mNavigationView.getMenu().getItem(2).setChecked(false);
                mNavigationView.getMenu().getItem(3).setChecked(false);
                mNavigationView.getMenu().getItem(4).setChecked(false);
                mNavigationView.getMenu().getItem(5).setChecked(false);
            }
        });

        headerName = (TextView)view.findViewById(R.id.header_name);
        headerCompany = (TextView)view.findViewById(R.id.header_company);
        keyword = "nav";
        String url = getResources().getString(R.string.base_url) +
                "?username=" + getResources().getString(R.string.base_name) +
                "&password=" + getResources().getString(R.string.base_pass) +
                "&action=getclientsdetails" +
                "&clientid="+ utility.getUserID()+
                "&responsetype=json";
        jsonExecuter.execute(url);
    }

    @Override
    public void processFinish(JSONObject[] jsonObject) {
        this.jsonObjects = jsonObject;
        if(keyword!=null) {
            if (keyword.equals("nav")) {
                if (jsonObjects[0].optString("result").equals("success")) {
                    headerName.setText(jsonObjects[0].optString("fullname"));
                    headerCompany.setText(jsonObject[0].optString("companyname"));
                    utility.updatePreFile("yes",utility.getUserID(),jsonObjects[0].optString("fullname"),jsonObject[0].optString("companyname"));
                    mFragmentTransaction.replace(R.id.containerView, new Dashboard(MainActivity.this)).commit();
                    mNavigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(isTaskRoot()){
            Toast.makeText(this, "Last Task", Toast.LENGTH_LONG).show();
        }
        else{
            //finish();
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.font));
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

}
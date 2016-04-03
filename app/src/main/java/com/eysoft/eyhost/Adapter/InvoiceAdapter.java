package com.eysoft.eyhost.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.eysoft.eyhost.AdapterObjects.InvoiceObject;
import com.eysoft.eyhost.MainActivity;
import com.eysoft.eyhost.Utility;
import com.eysoft.eyhost.R;

import java.util.ArrayList;

/**
 * Created by EySoft IT Solution on 2/25/2016.
 */
public class InvoiceAdapter extends BaseAdapter {

    Context context;
    ArrayList<InvoiceObject> invoiceObjects = null;
    Utility utility;

    public InvoiceAdapter(Context ctx, ArrayList<InvoiceObject> invoiceObjects){
        context = ctx;
        this.invoiceObjects = invoiceObjects;
        utility = new Utility(context);
    }
    @Override
    public int getCount() {
        return invoiceObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return invoiceObjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater lf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lf.inflate(R.layout.invoice_listview_child,null);
        }
        TextView invoiceDate = (TextView)view.findViewById(R.id.invoice_list_child_date);
        TextView invoiceNo = (TextView)view.findViewById(R.id.invoice_list_child_invoice_number);
        TextView invoicePrice = (TextView)view.findViewById(R.id.invoice_list_child_price);
        TextView invoiceStatus = (TextView)view.findViewById(R.id.invoice_list_child_status);
        Button invoiceViewDetails = (Button)view.findViewById(R.id.invoice_view_details);
        utility.setFont(invoiceDate);
        utility.setFont(invoiceNo);
        utility.setFont(invoicePrice);
        utility.setFont(invoiceStatus);
        utility.setFont(invoiceViewDetails);
        invoiceDate.setText(invoiceObjects.get(i).getDueDate());
        invoiceNo.setText(invoiceObjects.get(i).getInvoiceNo());
        invoicePrice.setText(invoiceObjects.get(i).getPrice());
        invoiceStatus.setText(invoiceObjects.get(i).getStatus());
        invoiceViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("value","invoice");
                intent.putExtra("json", String.valueOf(invoiceObjects.get(i).getJsonObject()));
                context.startActivity(intent);
            }
        });
        return view;
    }
}

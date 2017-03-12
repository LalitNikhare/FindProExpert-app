package com.example.com.findproexperttabbed.Notification.Customer.ProfessionalResponded;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.com.findproexperttabbed.Config;
import com.example.com.findproexperttabbed.JSONNotification;
import com.example.com.findproexperttabbed.ProfileActivity;
import com.example.com.findproexperttabbed.R;

import java.util.HashMap;
import java.util.Map;

public class ViewProfRespondedAdapter extends ArrayAdapter<String> {
    private static final String ACCEPT_PROFESSIONAL_URL = "https://findproexpertcom.000webhostapp.com/accept_professional.php";
    private static final String REQUEST_ID = "request_id";
    private static final String CUST_USERNAME = "cust_username";
    private static final String PROF_USERNAME = "prof_username";
    private static final String REQUEST_STR = "request_str";
    private static final String DOMAIN = "profession_name";
    private String[] fname;
    private String[] lname;
    //private String[] effeciency;
    private int request_id,index;
    private Context context;
    String cust_username,prof_username;

    public ViewProfRespondedAdapter( Context context,  String[] fname,String[] lname,int request_id,int index) {
        super(context, R.layout.activity_view_prof_responded_adapter, fname);
        this.context=context;
        this.fname=fname;
        this.lname=lname;
        this.request_id=request_id;
        this.index=index;
        //this.effeciency=effeciency;
    }
    private class ViewHolder {
        TextView name1, effeciency1;
        Button accept,view;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater
                    .inflate(R.layout.activity_view_prof_responded_adapter, parent, false);
            holder.name1 = (TextView) convertView.findViewById(R.id.view_prof_res_name);
            holder.effeciency1= (TextView) convertView.findViewById(R.id.view_prof_res_eff);
            holder.accept= (Button) convertView.findViewById(R.id.view_prof_res_accept);
            holder.view= (Button) convertView.findViewById(R.id.view_prof_res_view_prof);

            convertView.setTag(holder);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try{
            holder.name1.setText("" + fname[position]+" "+lname[position]);
            //holder.effeciency1.setText("" + effeciency[position]);

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptProfessional(position);
                    Toast.makeText(getContext(),"You Accepted this Professional",Toast.LENGTH_SHORT).show();
                }
            });

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str_username=JSONNotification.prof_username[position];
                    Intent intent=new Intent(getContext(), ProfileActivity.class);
                    intent.putExtra("username",str_username);
                    intent.putExtra("view","view");
                    getContext().startActivity(intent);
                    Toast.makeText(getContext(),"Fetching Profile Contents",Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }

        return convertView;
    }
    public void acceptProfessional(final int position){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        cust_username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ACCEPT_PROFESSIONAL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),""+response,Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String ,String> params=new HashMap<String, String>();
                params.put(REQUEST_ID,String.valueOf(JSONNotification.request_id[index]));
                params.put(CUST_USERNAME,cust_username);
                params.put(PROF_USERNAME, JSONNotification.prof_username[position]);
                params.put(REQUEST_STR,JSONNotification.request[index]);
                params.put(DOMAIN,JSONNotification.domain[index]);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}

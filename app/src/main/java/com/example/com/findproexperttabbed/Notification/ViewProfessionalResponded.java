package com.example.com.findproexperttabbed.Notification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.com.findproexperttabbed.JSONNotification;
import com.example.com.findproexperttabbed.R;

import java.util.HashMap;
import java.util.Map;

public class ViewProfessionalResponded extends AppCompatActivity {

    private static final String FETCH_PROF_RESPONDED = "https://findproexpertcom.000webhostapp.com/fetch_prof_responded.php";
    ListView mylist;
    ProgressDialog progressDialog;
    int req_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_professional_responded);
        mylist= (ListView) findViewById(R.id.view_prof_responded_list);

        Intent intent=getIntent();
        req_id=intent.getIntExtra("request_id",-1);
        fetch_prof_responded(req_id);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ViewProfessionalResponded.this,""+JSONNotification.username[position],Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetch_prof_responded(final int req_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_PROF_RESPONDED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(getActivity().getApplicationContext(), "Reached onResponse()" + response, Toast.LENGTH_SHORT).show();
                        try{
                            processJSON(response);
                            Toast.makeText(ViewProfessionalResponded.this,""+response,Toast.LENGTH_SHORT).show();
                        }catch(Exception e){

                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity().getApplicationContext(), "Error in network " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("requestid", String.valueOf(req_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ViewProfessionalResponded.this);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(ViewProfessionalResponded.this);
        progressDialog.setMessage("Reading Response from server...");
        progressDialog.show();
    }

    private void processJSON(String response) {
        JSONNotification jsonNotification=new JSONNotification(response);
        jsonNotification.parseForProfessionalResponded();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ViewProfessionalResponded.this,android.R.layout.simple_list_item_1);
        for(int i=0;i<JSONNotification.fname.length;i++){
            adapter.add(""+JSONNotification.fname[i]+" "+JSONNotification.lname[i]);
        }
        mylist.setAdapter(adapter);
    }
}
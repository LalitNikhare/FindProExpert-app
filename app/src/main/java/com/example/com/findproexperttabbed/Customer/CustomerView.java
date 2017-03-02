package com.example.com.findproexperttabbed.Customer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.com.findproexperttabbed.JSONProfessional;
import com.example.com.findproexperttabbed.R;

/**
 * Created by faltu on 09-Feb-17.
 */

public class CustomerView extends Fragment {
    public static final String JSON_URL = "https://findproexpertcom.000webhostapp.com/fetch_professions.php";
    ListView pro_list;
    View view;
    ProgressDialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.customer_fragment, container, false);
        pro_list = (ListView) view.findViewById(R.id.pro_list1);
        sendRequest();
        pro_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_for_customer);
                Button confirm, cancel;
                confirm= (Button) dialog.findViewById(R.id.request_confirm);
                cancel= (Button) dialog.findViewById(R.id.request_cancel);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity().getApplicationContext(),"Request confirmed",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity().getApplicationContext(),"Request cancelled",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.setTitle("Place Request");
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        return view;
    }

    private void sendRequest() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(getActivity().getApplicationContext(), "Reached onResponse()" + response, Toast.LENGTH_SHORT).show();
                        showJSON(response);
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity().getApplicationContext(), "Error in network " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Reading Response from server...");
        progressDialog.show();
    }

    private void showJSON(String json) {
        JSONProfessional pj = new JSONProfessional(json);
        pj.parseJSONforProfessional();
//        Toast.makeText(getActivity().getApplicationContext(), "No of objects :" + pj.worker.length, Toast.LENGTH_LONG).show();
//        for (int i = 0; i < pj.worker.length; i++) {//Chances of error here
//            Toast.makeText(getActivity().getApplicationContext(), i + " Name: " + JSONProfessional.names[i] + "Desc: " + pj.desc[i] + "No. of workers: " + pj.worker[i], Toast.LENGTH_SHORT).show();
//        }

        try {
            SeeProfessions cl = new SeeProfessions(getActivity(), JSONProfessional.names, JSONProfessional.desc, JSONProfessional.worker);
            pro_list.setAdapter(cl);
        } catch (Exception e) {
            Log.d("Error", "Error in HomeActivity customer fragment custom adapter");
            e.printStackTrace();
        }

    }
}

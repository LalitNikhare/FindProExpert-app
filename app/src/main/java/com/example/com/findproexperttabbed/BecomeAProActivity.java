package com.example.com.findproexperttabbed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BecomeAProActivity extends AppCompatActivity {
    private static final String BECOME_A_PRO_URL = "https://findproexpertcom.000webhostapp.com/becomepro.php";
    private static final String KEY_PROF = "prof_name";
    private ListView mylist;
    Button add, reject;
    public int[] index;
    public int len;
    ArrayList<String> profession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_apro);
        add = (Button) findViewById(R.id.become_a_pro_accept);
        reject = (Button) findViewById(R.id.become_a_pro_reject);
        mylist = (ListView) findViewById(R.id.become_a_pro_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BecomeAProActivity.this, android.R.layout.simple_list_item_checked);
        adapter.addAll(JSONProfessional.prof);
        mylist.setAdapter(adapter);
//        public void onListItemClick(ListView parent, View v,int position,long id){
//            CheckedTextView item = (CheckedTextView) v;
//            Toast.makeText(BecomeAProActivity.this, JSONProfessional.prof[position] + " checked : " +
//                    item.isChecked(), Toast.LENGTH_SHORT).show();
//        }
        mylist.setChoiceMode(mylist.CHOICE_MODE_MULTIPLE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profession=new ArrayList<String>();
                SparseBooleanArray a = mylist.getCheckedItemPositions();
                for (int i = 0; i < mylist.getCount(); i++) {
                    if (a.get(i) == true) {
                        profession.add(mylist.getItemAtPosition(i).toString());
                        Toast.makeText(BecomeAProActivity.this, "" + mylist.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                    }

//                    Toast.makeText(BecomeAProActivity.this,""+a.get(i),Toast.LENGTH_SHORT).show();
                }
                place_profession();
                Toast.makeText(BecomeAProActivity.this, "Added Skills Successfully", Toast.LENGTH_SHORT).show();
//                for(int i=0;i<len;i++){
//                    Toast.makeText(BecomeAProActivity.this,"Index Selected:"+index[i],Toast.LENGTH_SHORT).show();
//                }
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BecomeAProActivity.this, "Going back to Settings..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BecomeAProActivity.this, SettingsPref.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void place_profession() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BECOME_A_PRO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BecomeAProActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BecomeAProActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String ,String> params=new HashMap<String, String>(profession.size());
                params.put("username",username);
                params.put("count",""+profession.size());
                for(int i=0;i<profession.size();i++)
                {
                    params.put("params_"+i, profession.get(i));
                }
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

package com.example.com.findproexperttabbed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ViewRequestActivity extends AppCompatActivity {

    ListView mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        mylist=(ListView)findViewById(R.id.view_request);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
        String[] requests={"Website","Android App","Website","Android App","Website","Android App","Website","Android App"};
        ViewRequestAdapter cl=new ViewRequestAdapter(ViewRequestActivity.this,requests);
    mylist.setAdapter(cl);
    }

}

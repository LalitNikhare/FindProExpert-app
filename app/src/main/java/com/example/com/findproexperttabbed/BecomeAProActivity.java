package com.example.com.findproexperttabbed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.com.findproexperttabbed.Professional.SeeRequests;

import static com.example.com.findproexperttabbed.R.id.become_a_pro_list;

public class BecomeAProActivity extends AppCompatActivity {
    private ListView mylist;
    Button add,reject;
    public int[] index;
    public int len;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_apro);
        add= (Button) findViewById(R.id.become_a_pro_accept);
        reject= (Button) findViewById(R.id.become_a_pro_reject);
        mylist=(ListView)findViewById(R.id.become_a_pro_list);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(BecomeAProActivity.this,android.R.layout.simple_list_item_checked);
        adapter.addAll(JSONProfessional.prof);
        mylist.setAdapter(adapter);
//        public void onListItemClick(ListView parent, View v,int position,long id){
//            CheckedTextView item = (CheckedTextView) v;
//            Toast.makeText(BecomeAProActivity.this, JSONProfessional.prof[position] + " checked : " +
//                    item.isChecked(), Toast.LENGTH_SHORT).show();
//        }
        mylist.setChoiceMode(mylist.CHOICE_MODE_MULTIPLE);

//
//        try {
//            BecomeAProCustomAdapter cl = new BecomeAProCustomAdapter(BecomeAProActivity.this, JSONProfessional.prof);
//            index=new int[JSONProfessional.prof.length];
//            len=JSONProfessional.prof.length;
//            mylist.setAdapter(cl);
//        } catch (Exception e) {
//            Log.d("Error", "Error in HomeActivity customer fragment custom adapter");
//            e.printStackTrace();
//        }

//        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(BecomeAProActivity.this,"hello"+position,Toast.LENGTH_SHORT).show();
//            }
//        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray a=mylist.getCheckedItemPositions();
                for(int i=0;i<mylist.getCount();i++)
                {
                    if(a.get(i)==true){
                        Toast.makeText(BecomeAProActivity.this,""+mylist.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(BecomeAProActivity.this,""+a.get(i),Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(BecomeAProActivity.this,"Added Skills Successfully",Toast.LENGTH_SHORT).show();
//                for(int i=0;i<len;i++){
//                    Toast.makeText(BecomeAProActivity.this,"Index Selected:"+index[i],Toast.LENGTH_SHORT).show();
//                }
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BecomeAProActivity.this,"Going back to Settings..",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(BecomeAProActivity.this,SettingsPref.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

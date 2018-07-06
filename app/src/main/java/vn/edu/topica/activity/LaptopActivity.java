package vn.edu.topica.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.edu.topica.adapter.DienthoaiAdapter;
import vn.edu.topica.adapter.LaptopAdapter;
import vn.edu.topica.appbanhang.R;
import vn.edu.topica.model.Sanpham;
import vn.edu.topica.until.Server;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarlaptop;
    ListView lvLaptop;
    ArrayList<Sanpham> dsLaptop;
    LaptopAdapter laptopAdapter;

    int Idlaptop=0;
    int page=1;

    View footerview;
    boolean isLoading= false;
    boolean limitdata = false;
    mHandler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        addControls();
        addEvents();
    }

    private void addEvents() {
        getDulieusanpham();
        actionToolbar();
        getData(page);
        LoadmoreData();
    }
    private void LoadmoreData() {
        lvLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Chitietsanpham.class);
                intent.putExtra("thongtinsanpham",dsLaptop.get(i));
                startActivity(intent);
            }
        });
        lvLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if(FirstItem+VisibleItem==TotalItem && TotalItem !=0 && isLoading==false && limitdata ==false){
                    isLoading=true;
                    ThreadData threadData =new LaptopActivity.ThreadData();
                    threadData.start();

                }

            }
        });
    }

    private void getData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Duongdandienthoai+String.valueOf(Page);
        StringRequest stringRequest= new StringRequest(Request.Method.POST,duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tenlaptop="";
                int Gialaptop=0;
                String hinhanhlaptop="";
                String motalaptop="";
                int Idsplaptop=0;

                if(response!=null && response.length() !=2){
                    lvLaptop.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject =jsonArray.getJSONObject(i);
                            id =jsonObject.getInt("id");
                            Tenlaptop = jsonObject.getString("tensanpham");
                            Gialaptop =jsonObject.getInt("giasanpham");
                            hinhanhlaptop=jsonObject.getString("hinhanhsanpham");
                            motalaptop=jsonObject.getString("motasanpham");
                            Idsplaptop=jsonObject.getInt("idsanpham");
                            dsLaptop.add(new Sanpham(id,Tenlaptop,Gialaptop,motalaptop,hinhanhlaptop,Idsplaptop));
                            laptopAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    limitdata=true;
                    lvLaptop.removeFooterView(footerview);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(Idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }
    private void actionToolbar() {
        setSupportActionBar(toolbarlaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDulieusanpham() {
        Idlaptop= getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("Giatriloaisanpham",Idlaptop+"");

    }

    private void addControls() {
        toolbarlaptop= (Toolbar) findViewById(R.id.toolbarlaptop);
        lvLaptop= (ListView) findViewById(R.id.lvlaptop);
        dsLaptop=new ArrayList<>();
        laptopAdapter=new LaptopAdapter(this,R.layout.item_laptop,dsLaptop);
        lvLaptop.setAdapter(laptopAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview =inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(this,GiohangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public class mHandler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvLaptop.addFooterView(footerview);
                    break;
                case 1:
                    getData(++page);
                    isLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public  class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message =mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}

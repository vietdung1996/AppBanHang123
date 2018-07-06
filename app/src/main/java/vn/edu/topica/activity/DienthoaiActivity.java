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
import java.util.logging.Handler;

import vn.edu.topica.adapter.DienthoaiAdapter;
import vn.edu.topica.appbanhang.R;
import vn.edu.topica.model.Sanpham;
import vn.edu.topica.until.Server;

public class DienthoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvDienthoai;
    ArrayList<Sanpham>dsDienthoai;
    DienthoaiAdapter dienthoaiAdapter;

    int Iddt=0;
    int page=1;

    View footerview;
    boolean isLoading= false;
    boolean limitdata = false;
    mHandler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);
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
        lvDienthoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Chitietsanpham.class);
                intent.putExtra("thongtinsanpham",dsDienthoai.get(i));
                startActivity(intent);
            }
        });
        lvDienthoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if(FirstItem+VisibleItem==TotalItem && TotalItem !=0 && isLoading==false && limitdata ==false){
                    isLoading=true;
                    ThreadData threadData =new ThreadData();
                    threadData.start();

                }

            }
        });

    }
    @Override
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

    private void getData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Duongdandienthoai+String.valueOf(Page);
        StringRequest stringRequest= new StringRequest(Request.Method.POST,duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tendt="";
                int Giadt=0;
                String hinhanhdt="";
                String motadt="";
                int Idspdt=0;

                if(response!=null && response.length() !=2){
                    lvDienthoai.addFooterView(footerview);
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject =jsonArray.getJSONObject(i);
                            id =jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensanpham");
                            Giadt =jsonObject.getInt("giasanpham");
                            hinhanhdt=jsonObject.getString("hinhanhsanpham");
                            motadt=jsonObject.getString("motasanpham");
                            Idspdt=jsonObject.getInt("idsanpham");
                            dsDienthoai.add(new Sanpham(id,Tendt,Giadt,motadt,hinhanhdt,Idspdt));
                            dienthoaiAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    limitdata=true;
                    lvDienthoai.removeFooterView(footerview);
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
                param.put("idsanpham",String.valueOf(Iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDulieusanpham() {
        Iddt= getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("Giatriloaisanpham",Iddt+"");

    }

    private void addControls() {
        toolbar= (Toolbar) findViewById(R.id.toolbarđienthoai);
        lvDienthoai= (ListView) findViewById(R.id.lvdienthoai);
        dsDienthoai=new ArrayList<>();
        dienthoaiAdapter=new DienthoaiAdapter(this,R.layout.item_dienthoai,dsDienthoai);
        lvDienthoai.setAdapter(dienthoaiAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview =inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();

    }
    public class mHandler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvDienthoai.addFooterView(footerview);
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

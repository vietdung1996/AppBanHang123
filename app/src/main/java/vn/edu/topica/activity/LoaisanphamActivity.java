package vn.edu.topica.activity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.edu.topica.adapter.DienthoaiAdapter;
import vn.edu.topica.adapter.LoaispAdapter;
import vn.edu.topica.appbanhang.R;
import vn.edu.topica.model.Loaisp;
import vn.edu.topica.until.CheckConnection;
import vn.edu.topica.until.Server;

public class LoaisanphamActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvLoaisp;
    ArrayList<Loaisp> dsloaisp;
    LoaispAdapter loaispAdapter;
    int id=0;
    String tenloaisp="";
    String hinhanhloaisp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaisanpham);
        addControls();
        addEvents();
    }

    private void addEvents() {
        getDulieusanpham();
        getChuyenmanhinh();

    }

    private void getChuyenmanhinh() {
        lvLoaisp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                    if (CheckConnection.HaveNetworkConnection(getApplicationContext())) {
                        Intent intent = new Intent(LoaisanphamActivity.this, DienthoaiActivity.class);
                        intent.putExtra("idloaisanpham", dsloaisp.get(i).getId());
                        startActivity(intent);
                    } else {
                        CheckConnection.showToast_Short(getApplicationContext(), "Kiem tra lai ket noi Internet");
                    }
                    break;
                    case 1:
                        if (CheckConnection.HaveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(LoaisanphamActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisanpham", dsloaisp.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Kiem tra lai ket noi Internet");
                        }
                        break;
                }
            }
        });

    }

    private void getDulieusanpham() {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if(response != null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("tenloaisanpham");
                            hinhanhloaisp=jsonObject.getString("hinhanhloaisanpham");
                            dsloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(),error.toString());
            }
        }
        );
        requestQueue.add(jsonArrayRequest);




    }

    private void addControls() {
        toolbar= (Toolbar) findViewById(R.id.toolbarLoaisanpham);
        lvLoaisp= (ListView) findViewById(R.id.lvLoaisp);
        dsloaisp=new ArrayList<>();
        loaispAdapter=new LoaispAdapter(this,R.layout.item_loaisp,dsloaisp);
        lvLoaisp.setAdapter(loaispAdapter);

    }


}

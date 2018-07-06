package vn.edu.topica.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import vn.edu.topica.appbanhang.R;
import vn.edu.topica.until.CheckConnection;
import vn.edu.topica.until.Server;

public class ThongTinThanhToanActivity extends AppCompatActivity {
    EditText editTennguoimua,editEmail,editPhonenumber;
    Button btnxacnhan,btnQuaylai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_thanh_toan);

        addControls();
        addEvents();
    }

    private void addEvents() {
        EventsButton();
    }

    private void EventsButton() {
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ten = editTennguoimua.getText().toString().trim();
                final String sdt = editPhonenumber.getText().toString().trim();
                final String email=editEmail.getText().toString().trim();
                if(ten.length()>0 && sdt.length()>0 && email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if(Integer.parseInt(madonhang)>0){
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.Chitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            MainActivity.arrGiohang.clear();
                                            CheckConnection.showToast_Short(getApplicationContext(),"Ban da them giu lieu gio hang thanh cong");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                        }else{
                                            CheckConnection.showToast_Short(getApplicationContext(),"Ban da them giu lieu gio hang that bai");
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray =new JSONArray();
                                        for(int i=0;i<MainActivity.arrGiohang.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.arrGiohang.get(i).getId());
                                                jsonObject.put("tensanpham",MainActivity.arrGiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.arrGiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.arrGiohang.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap =new HashMap<String, String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);

                }else{
                    CheckConnection.showToast_Short(getApplicationContext(),"Hay kiem tra lai du lieu");
                }
            }
        });
    }

    private void addControls() {
        editTennguoimua= (EditText) findViewById(R.id.editTennguoimua);
        editEmail= (EditText) findViewById(R.id.editEmail);
        editPhonenumber= (EditText) findViewById(R.id.editPhonenumber);
        btnxacnhan= (Button) findViewById(R.id.btnXacnhan);
        btnQuaylai= (Button) findViewById(R.id.btnQuaylai);
    }
}

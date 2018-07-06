package vn.edu.topica.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import vn.edu.topica.adapter.LoaispAdapter;
import vn.edu.topica.adapter.SanphamAdapter;
import vn.edu.topica.appbanhang.R;
import vn.edu.topica.model.Giohang;
import vn.edu.topica.model.Loaisp;
import vn.edu.topica.model.Sanpham;
import vn.edu.topica.until.CheckConnection;
import vn.edu.topica.until.Server;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView lvmanhinh;
    DrawerLayout drawerLayout;

    ArrayList<Loaisp>dsLoaisp;
    LoaispAdapter loaispAdapter;

    ArrayList<Sanpham>dsSanpham;
    SanphamAdapter sanphamAdapter;

    public static ArrayList<Giohang> arrGiohang;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();

        if(CheckConnection.HaveNetworkConnection(getApplicationContext())){
            events();
            xuLyViewFlipper();
            getDuLieuMoiNhat();
            catchonitemlistview();
        }else {
            CheckConnection.showToast_Short(getApplicationContext(),"Ban hay kiem tra lai ket noi");
            finish();
        }



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

    private void catchonitemlistview() {
        lvmanhinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                    drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 1: if(CheckConnection.HaveNetworkConnection(getApplicationContext())){
                        Intent intent = new Intent(MainActivity.this,LoaisanphamActivity.class);
                        //intent.putExtra("idloaisanpham",dsLoaisp.get(position).getId());
                        startActivity(intent);
                    }else{
                        CheckConnection.showToast_Short(getApplicationContext(),"Kiem tra lai ket noi Internet");
                    }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case 2: if(CheckConnection.HaveNetworkConnection(getApplicationContext())){
                        Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                        startActivity(intent);
                    }else{
                        CheckConnection.showToast_Short(getApplicationContext(),"Kiem tra lai ket noi Internet");
                    }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 3: if(CheckConnection.HaveNetworkConnection(getApplicationContext())){
                        Intent intent = new Intent(MainActivity.this,ThongtinActivity.class);
                        startActivity(intent);
                    }else{
                        CheckConnection.showToast_Short(getApplicationContext(),"Kiem tra lai ket noi Internet");
                    }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.HaveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DangkiActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Kiem tra lai ket noi Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }

            }
        });

    }

    private void getDuLieuMoiNhat() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanspmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    int Id=0;
                    String Tensanpham="";
                    Integer Giasanpham=0;
                    String Hinhanhsanpham="";
                    String Motasanpham="";
                    int Idsanpham=0;
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject= response.getJSONObject(i);
                            Id =jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensanpham");
                            Giasanpham =jsonObject.getInt("giasanpham");
                            Hinhanhsanpham=jsonObject.getString("hinhanhsanpham");
                            Motasanpham=jsonObject.getString("motasanpham");
                            Idsanpham=jsonObject.getInt("idsanpham");
                            dsSanpham.add(new Sanpham(Id,Tensanpham,Giasanpham,Motasanpham,Hinhanhsanpham,Idsanpham));
                            sanphamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void xuLyViewFlipper() {
        ArrayList<String>mangquangcao=new ArrayList<>();
        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJUV65F3MCAK5p-Fhvi8_ONK5RO3yi6Jbhsa8JVZQwIKS5YU-K");
        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3Zbo5QbNkaEkv4N_recspDko8hQShk4yNrw_DZK8FgaxheSp9");

        for(int i=0;i<mangquangcao.size();i++){
            ImageView imageview=new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageview);
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageview);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.silde_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }

    private void events() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    private void addControls() {
        toolbar= (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper= (ViewFlipper) findViewById(R.id.viewflipper);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        navigationView= (NavigationView) findViewById(R.id.navigationview);
        lvmanhinh= (ListView) findViewById(R.id.lvmanhinh);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerlayout);

        dsLoaisp=new ArrayList<>();
        dsLoaisp.add(0,new Loaisp(0,"trang chinh","https://askwoody.com/wp-content/themes/newaskwoody/images/ico/home-icon.png"));
        dsLoaisp.add(1,new Loaisp(0,"Danh sách sản phẩm","https://autono1.com.vn/images/icon_cart_top_empty.png"));
        dsLoaisp.add(2,new Loaisp(0,"Liên hệ","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPg4lR02FkdrSyDVsrOTv4YrFQBxV-3pHkou4OqVU1wC2j1Tq-"));
        dsLoaisp.add(3,new Loaisp(0,"Thông tin","http://www.roswelldistillery.com/media/mint/icons/16/Information.png"));
        dsLoaisp.add(4,new Loaisp(0,"Đăng kí/Đăng nhập","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLnpoylkqPysc2g-zlreTppvxsrXYNJXV-XAP4B_6yVx_Khi6V"));
        loaispAdapter=new LoaispAdapter(MainActivity.this,R.layout.item_loaisp,dsLoaisp);
        lvmanhinh.setAdapter(loaispAdapter);

        dsSanpham=new ArrayList<>();
        sanphamAdapter=new SanphamAdapter(getApplicationContext(),dsSanpham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanphamAdapter);

        if(arrGiohang !=null){

        }else{
            arrGiohang=new ArrayList<>();
        }
    }


}

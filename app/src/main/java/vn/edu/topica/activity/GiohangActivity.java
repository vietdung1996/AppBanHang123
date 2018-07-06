package vn.edu.topica.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

import vn.edu.topica.adapter.GiohangAdapter;
import vn.edu.topica.appbanhang.R;
import vn.edu.topica.model.Giohang;
import vn.edu.topica.until.CheckConnection;

public class GiohangActivity extends AppCompatActivity {
    ListView lvGiohang;
    TextView txtThongbao;
    static TextView txtTongtien;
    Button btnThanhtoan,btnTieptucmua;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        addControls();
        addEvents();
    }

    private void addEvents() {
        ActionToolbar();
        CheckData();
        EventUtil();
        CatchOntiemListview();
        EventsButton();
    }

    private void EventsButton() {
        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.arrGiohang.size() !=0){
                    Intent intent=new Intent(getApplicationContext(),ThongTinThanhToanActivity.class);
                    startActivity(intent);
                }else{
                    CheckConnection.showToast_Short(getApplicationContext(),"Gio hang chua co san pham de thanh toan");
                }

            }
        });
        btnTieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchOntiemListview() {
        lvGiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder= new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.arrGiohang.size()<=0){
                            txtThongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.arrGiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventUtil();
                            if(MainActivity.arrGiohang.size()<=0){
                                txtThongbao.setVisibility(View.VISIBLE);
                            }else {
                                txtThongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventUtil();
                            }

                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();

                return true;
            }
        });
    }

    public static void EventUtil() {
        long tongtien=0;
        for(int i=0;i<MainActivity.arrGiohang.size();i++){
            tongtien += MainActivity.arrGiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtien)+"D");
    }

    private void CheckData() {
        if (MainActivity.arrGiohang.size()<=0){
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            lvGiohang.setVisibility(View.INVISIBLE);
        }else{
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            lvGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        lvGiohang= (ListView) findViewById(R.id.lvGiohang);
        txtThongbao= (TextView) findViewById(R.id.txtThongbao);
        txtTongtien= (TextView) findViewById(R.id.txtTonggiatien);
        btnThanhtoan= (Button) findViewById(R.id.btnThanhToan);
        btnTieptucmua= (Button) findViewById(R.id.btnTieptucmuahang);
        toolbargiohang= (Toolbar) findViewById(R.id.toolbarGiohang);
        giohangAdapter = new GiohangAdapter(GiohangActivity.this,MainActivity.arrGiohang);
        lvGiohang.setAdapter(giohangAdapter);
    }
}

package vn.edu.topica.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import vn.edu.topica.appbanhang.R;
import vn.edu.topica.model.Giohang;
import vn.edu.topica.model.Sanpham;

public class Chitietsanpham extends AppCompatActivity {
    Toolbar toolbarchitiet;
    ImageView imgchitietsp;
    TextView txtTenchitietsp,txtGiachitietsp,txtMota;
    Spinner spinner;
    Button btndatmua;

    int id =0;
    String Tenchitiet="";
    int Giachitiet=0;
    String Hinhanhchitiet="";
    String Motachitiet="";
    int idsanpham=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        addControls();
        addEvents();
    }

    private void addEvents() {
        ActionToolBar();
        GetInformation();
        CatchEventSpinner();
        EventsButton();

    }

    private void EventsButton() {

        btndatmua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(MainActivity.arrGiohang.size() != 0){
                    int sl= Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.arrGiohang.size(); i++) {
                        if (MainActivity.arrGiohang.get(i).getId() == id) {
                            MainActivity.arrGiohang.get(i).setSoluongsp(MainActivity.arrGiohang.get(i).getSoluongsp() + sl);
                            if (MainActivity.arrGiohang.get(i).getSoluongsp() >= 10) {
                                MainActivity.arrGiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.arrGiohang.get(i).setGiasp(Giachitiet * MainActivity.arrGiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if(exists==false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi=soluong * Giachitiet;
                        MainActivity.arrGiohang.add(new Giohang(id,Tenchitiet,giamoi,Hinhanhchitiet,soluong));

                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi=soluong * Giachitiet;
                    MainActivity.arrGiohang.add(new Giohang(id,Tenchitiet,giamoi,Hinhanhchitiet,soluong));
                }

                Intent intent =new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);
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

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        Sanpham sanpham= (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id=sanpham.getId();
        Tenchitiet=sanpham.getTensanpham();
        Giachitiet=sanpham.getGiasanpham();
        Hinhanhchitiet=sanpham.getHinhanhsanpham();
        Motachitiet=sanpham.getMotasanpham();
        txtTenchitietsp.setText(Tenchitiet);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtGiachitietsp.setText("Giá: "+decimalFormat.format(Giachitiet)+"Đ");
        txtMota.setText(Motachitiet);
        Picasso.with(getApplicationContext()).load(Hinhanhchitiet)
                .placeholder(R.drawable.load1)
                .error(R.drawable.cancel)
                .into(imgchitietsp);


    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        toolbarchitiet= (Toolbar) findViewById(R.id.toolbarchitietsp);
        imgchitietsp= (ImageView) findViewById(R.id.imgchitietsanpham);
        txtTenchitietsp= (TextView) findViewById(R.id.txtTenchitietsp);
        txtGiachitietsp= (TextView) findViewById(R.id.txtGiachitietsp);
        txtMota= (TextView) findViewById(R.id.txtMotachitietsp);
        spinner= (Spinner) findViewById(R.id.spinner);
        btndatmua= (Button) findViewById(R.id.btnthem);


    }
}

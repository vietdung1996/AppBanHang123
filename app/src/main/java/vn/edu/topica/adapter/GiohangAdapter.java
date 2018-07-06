package vn.edu.topica.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.edu.topica.activity.GiohangActivity;
import vn.edu.topica.activity.MainActivity;
import vn.edu.topica.appbanhang.R;
import vn.edu.topica.model.Giohang;

/**
 * Created by admin on 11/18/2017.
 */

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayGioHang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arrayGioHang) {
        this.context = context;
        this.arrayGioHang = arrayGioHang;
    }

    @Override
    public int getCount() {
        return arrayGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayGioHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txtTengiohang,txtGiagiohang;
        Button btnCong,btnTru,btnvalues;
        ImageView imgGiohang;


    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txtTengiohang=view.findViewById(R.id.txtTengiohang);
            viewHolder.txtGiagiohang=view.findViewById(R.id.txtGiagiohang);
            viewHolder.btnCong=view.findViewById(R.id.btnCong);
            viewHolder.btnTru=view.findViewById(R.id.btnTru);
            viewHolder.btnvalues=view.findViewById(R.id.btnvalues);
            viewHolder.imgGiohang=view.findViewById(R.id.imgGiohang);
            view.setTag(viewHolder);


        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Giohang giohang = (Giohang) getItem(i);
        viewHolder.txtTengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiagiohang.setText(decimalFormat.format(giohang.getGiasp())+"Đ");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.load1)
                .error(R.drawable.cancel)
                .into(viewHolder.imgGiohang);
        viewHolder.btnvalues.setText(giohang.getSoluongsp()+"");
        int sl= Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if(sl>=10){
            viewHolder.btnCong.setVisibility(View.INVISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        }else if (sl<=1){
            viewHolder.btnCong.setVisibility(View.VISIBLE);
            viewHolder.btnTru.setVisibility(View.INVISIBLE);
        }else if(sl>1){
            viewHolder.btnCong.setVisibility(View.VISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;
        final ViewHolder finalViewHolder2 = viewHolder;
        final ViewHolder finalViewHolder3 = viewHolder;
        final ViewHolder finalViewHolder4 = viewHolder;
        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString())+1;
                int slht = MainActivity.arrGiohang.get(i).getSoluongsp();
                long giaht =  MainActivity.arrGiohang.get(i).getGiasp();
                MainActivity.arrGiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat= giaht*slmoinhat / slht;
                MainActivity.arrGiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtGiagiohang.setText(decimalFormat.format(giamoinhat)+"Đ");
                GiohangActivity.EventUtil();
                if(slmoinhat>9){
                    finalViewHolder2.btnCong.setVisibility(View.INVISIBLE);
                    finalViewHolder3.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder4.btnvalues.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewHolder2.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder3.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder4.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString())-1;
                int slht = MainActivity.arrGiohang.get(i).getSoluongsp();
                long giaht =  MainActivity.arrGiohang.get(i).getGiasp();
                MainActivity.arrGiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat= giaht*slmoinhat / slht;
                MainActivity.arrGiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtGiagiohang.setText(decimalFormat.format(giamoinhat)+"Đ");
                GiohangActivity.EventUtil();
                if(slmoinhat<2){
                    finalViewHolder2.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder3.btnTru.setVisibility(View.INVISIBLE);
                    finalViewHolder4.btnvalues.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewHolder2.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder3.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder4.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });

        return view;
    }
}

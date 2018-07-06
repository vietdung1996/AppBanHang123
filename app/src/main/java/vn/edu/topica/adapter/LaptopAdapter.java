package vn.edu.topica.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import vn.edu.topica.appbanhang.R;
import vn.edu.topica.model.Sanpham;

/**
 * Created by admin on 11/30/2017.
 */

public class LaptopAdapter extends ArrayAdapter<Sanpham>{
    @NonNull
    Activity context;
    @LayoutRes
    int resource;
    @NonNull
    List<Sanpham> objects;

    public LaptopAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Sanpham> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);

        ImageView imghinhlaptop = row.findViewById(R.id.imghinhlaptop);
        TextView txtTenlaptop= row.findViewById(R.id.txtTenlaptop);
        TextView txtGialaptop=row.findViewById(R.id.txtGialaptop);
        TextView txtMotalaptop=row.findViewById(R.id.txtmota);

        Sanpham sanpham= (Sanpham) this.objects.get(position);
        txtTenlaptop.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        txtGialaptop.setText("Giá: "+decimalFormat.format(sanpham.getGiasanpham())+"Đ");
        txtMotalaptop.setMaxLines(2);
        txtMotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        txtMotalaptop.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.load1)
                .error(R.drawable.cancel)
                .into(imghinhlaptop);
        return row;
    }
}

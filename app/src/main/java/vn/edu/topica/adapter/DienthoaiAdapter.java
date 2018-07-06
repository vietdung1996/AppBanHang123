package vn.edu.topica.adapter;

import android.app.Activity;
import android.content.Context;
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
 * Created by admin on 10/27/2017.
 */

public class DienthoaiAdapter extends ArrayAdapter<Sanpham> {
    @NonNull Activity context;
    @LayoutRes int resource;
    @NonNull List<Sanpham> objects;

    public DienthoaiAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Sanpham> objects) {
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

        ImageView imghinhdt = row.findViewById(R.id.imghinhdt);
        TextView txtTendt= row.findViewById(R.id.txtTendt);
        TextView txtGiadt=row.findViewById(R.id.txtGiadt);
        TextView txtMotadt=row.findViewById(R.id.txtmota);

        Sanpham sanpham= (Sanpham) this.objects.get(position);
        txtTendt.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        txtGiadt.setText("Giá: "+decimalFormat.format(sanpham.getGiasanpham())+"Đ");
        txtMotadt.setMaxLines(2);
        txtMotadt.setEllipsize(TextUtils.TruncateAt.END);

        txtMotadt.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.load1)
                .error(R.drawable.cancel)
                .into(imghinhdt);
        return row;
    }
};


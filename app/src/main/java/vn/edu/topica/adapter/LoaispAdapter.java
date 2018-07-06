package vn.edu.topica.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.edu.topica.appbanhang.R;
import vn.edu.topica.model.Loaisp;

/**
 * Created by admin on 10/15/2017.
 */

public class LoaispAdapter extends ArrayAdapter<Loaisp> {
    @NonNull Activity context;
    @LayoutRes int resource;
    @NonNull List<Loaisp> objects;
    public LoaispAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Loaisp> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row =inflater.inflate(this.resource,null);

        ImageView imgHinhloaisp= (ImageView) row.findViewById(R.id.imgHinhloaisp);
        TextView txtloaisp=(TextView) row.findViewById(R.id.txtLoaisp);

        Loaisp loaisp=this.objects.get(position);
        txtloaisp.setText(loaisp.getTensp());
        Picasso.with(context).load(loaisp.getHinhanh())
                .placeholder(R.drawable.load1)
                .error(R.drawable.cancel)
                .into(imgHinhloaisp);
        return row;
    }
}

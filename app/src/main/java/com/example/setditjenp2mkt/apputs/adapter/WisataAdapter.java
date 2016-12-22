package com.example.setditjenp2mkt.apputs.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.setditjenp2mkt.apputs.R;
import com.example.setditjenp2mkt.apputs.utils.Global;
import com.example.setditjenp2mkt.apputs.utils.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.data;

/**
 * Created by Gregorius Andito on 12/22/2016.
 */

public class WisataAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public WisataAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (convertView == null)
            rowView = inflater.inflate(R.layout.layout_listdesc, null,true);
        TextView id_wisata = (TextView) rowView.findViewById(R.id.kode);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        HashMap<String, String> daftar_wisata = new HashMap<String, String>();
        daftar_wisata = data.get(position);

        id_wisata.setText(daftar_wisata.get(Global.ID_WISATA));
        txtTitle.setText(daftar_wisata.get(Global.NAMA_TEMPAT));
        imageLoader.DisplayImage(daftar_wisata.get(Global.GAMBAR_WISATA),imageView);
        return rowView;
    }

}

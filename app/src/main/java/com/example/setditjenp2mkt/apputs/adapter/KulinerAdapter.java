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

/**
 * Created by Gregorius Andito on 12/22/2016.
 */

public class KulinerAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public KulinerAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
            rowView = inflater.inflate(R.layout.layout_listdesc, null, true);
        TextView id_kuliner = (TextView) rowView.findViewById(R.id.kode);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        HashMap<String, String> daftar_kuliner = new HashMap<String, String>();
        daftar_kuliner = data.get(position);

        id_kuliner.setText(daftar_kuliner.get(Global.ID_KULINER));
        txtTitle.setText(daftar_kuliner.get(Global.NAMA_TEMPAT));
        imageLoader.DisplayImage(daftar_kuliner.get(Global.GAMBAR),imageView);
        return rowView;
    }

}

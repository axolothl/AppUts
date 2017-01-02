package com.example.setditjenp2mkt.apputs.old;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.setditjenp2mkt.apputs.utils.ImageNicer;
import com.example.setditjenp2mkt.apputs.R;

/**
 * Created by setditjen P2MKT on 27/10/2016.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] itemname;
    private Integer[] imgid;

    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.layout_listawal, itemname);

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_listawal, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
//        imageView.setImageResource(imgid[position]);
        imageView.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(), imgid[position], 100, 100));
        extratxt.setText("Pilih untuk info tentang " + itemname[position]);
        return rowView;

    };
}
package com.example.setditjenp2mkt.apputs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by setditjen P2MKT on 27/10/2016.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> itemname = new ArrayList<>();
    private ArrayList<Integer> imgid = new ArrayList<>();

    public CustomListAdapter(Activity context, ArrayList<String> itemname, ArrayList<Integer> imgid) {
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

        txtTitle.setText(itemname.get(position));
//        imageView.setImageResource(imgid[position]);
        imageView.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(), imgid.get(position), 100, 100));
        if (itemname.get(position) != "kosong"){
            extratxt.setText("Pilih untuk info tentang " + itemname.get(position));
        }
        return rowView;

    };
}
package com.example.setditjenp2mkt.apputs.old;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.setditjenp2mkt.apputs.R;
import com.example.setditjenp2mkt.apputs.utils.ImageNicer;

/**
 * Created by setditjen P2MKT on 27/10/2016.
 */

public class ListDesc extends ArrayAdapter<String> {
    private Activity context;
    private String[] itemname;
    private Integer[] imgid;

    public ListDesc(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.layout_listdesc, itemname);

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_listdesc, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(itemname[position]);
        imageView.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(), imgid[position], 100, 100));
        return rowView;

    };
}

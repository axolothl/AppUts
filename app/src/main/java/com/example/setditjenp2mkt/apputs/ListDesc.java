package com.example.setditjenp2mkt.apputs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by setditjen P2MKT on 27/10/2016.
 */

public class ListDesc extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> akun = new ArrayList<>();
    private ArrayList<Integer> imgid = new ArrayList<>();
    private ArrayList<String> comment = new ArrayList<>();

    public ListDesc(Activity context, ArrayList<String> akun, ArrayList<Integer> imgid, ArrayList<String> comment) {
        super(context, R.layout.layout_listdesc, akun);

        this.context=context;
        this.akun=akun;
        this.imgid=imgid;
        this.comment=comment;
    }

    public static class ViewHolderItem {
        TextView deletecomment;
    }


    public View getView(final int position_v, View view, ViewGroup parent) {
        ViewHolderItem viewHolder;

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.layout_listdesc, null,true);
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View rowView = inflater.inflate(R.layout.layout_listdesc, parent, false);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        viewHolder = new ViewHolderItem();
        viewHolder.deletecomment = (TextView) rowView.findViewById(R.id.deletecomment);
        viewHolder.deletecomment.setTag(position_v);

        txtTitle.setText(akun.get(position_v));
        imageView.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(), imgid.get(position_v), 100, 100));
        extratxt.setText(comment.get(position_v));

        return rowView;
    }
}

package com.example.setditjenp2mkt.apputs;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    TextView deletecomment;
    public TextView like;
    public int check_del = 0;

    public ListDesc(Activity context, ArrayList<String> akun, ArrayList<Integer> imgid, ArrayList<String> comment) {
        super(context, R.layout.layout_listdesc, akun);

        this.context=context;
        this.akun=akun;
        this.imgid=imgid;
        this.comment=comment;

    }

    public View getView(int position, View view, ViewGroup parent) {
        //LayoutInflater inflater=context.getLayoutInflater();
        //View rowView=inflater.inflate(R.layout.layout_listdesc, null,true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_listdesc, parent, false);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
        like = (TextView) rowView.findViewById(R.id.like);
        deletecomment = (TextView) rowView.findViewById(R.id.deletecomment);

        txtTitle.setText(akun.get(position));
        imageView.setImageBitmap(ImageNicer.decodeSampledBitmapFromResource(context.getResources(), imgid.get(position), 100, 100));
        extratxt.setText(comment.get(position));
        like.setText("Suka");
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like.getText().toString() == "Suka"){
                    like.setText("Batal Suka");
                    Toast.makeText(context.getApplicationContext(), "Anda menyukai komentar ini", Toast.LENGTH_SHORT).show();
                } else if (like.getText().toString() == "Batal Suka"){
                    like.setText("Suka");
                    Toast.makeText(context.getApplicationContext(), "Anda batal menyukai komentar ini", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deletecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_del = 1;
            }
        });


        return rowView;

    };
}

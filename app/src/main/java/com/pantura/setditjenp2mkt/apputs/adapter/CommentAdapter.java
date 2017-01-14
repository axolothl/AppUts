package com.pantura.setditjenp2mkt.apputs.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pantura.setditjenp2mkt.apputs.R;
import com.pantura.setditjenp2mkt.apputs.helpers.SQLiteHandler;
import com.pantura.setditjenp2mkt.apputs.utils.Global;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gregorius Andito on 1/6/2017.
 */

public class CommentAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    private SQLiteHandler db;

    public CommentAdapter(Activity a, ArrayList<HashMap<String, String>> d){
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public static class ViewHolderItem {
        TextView deletecomment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        View rowView = convertView;
        if (convertView == null)
            rowView = inflater.inflate(R.layout.layout_listdesc, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        HashMap<String, String> daftar_comment = new HashMap<String, String>();
        daftar_comment = data.get(position);

        db = new SQLiteHandler(CommentAdapter.inflater.getContext());
        HashMap<String, String> user = db.getUserDetails();
        final String id_user_sqlite = user.get("id_user");
        final String email = user.get("email");

        final String id_user = daftar_comment.get(Global.ID_USER);

        if(id_user.equals(id_user_sqlite)) {
            viewHolder = new ViewHolderItem();
            viewHolder.deletecomment = (TextView) rowView.findViewById(R.id.deletecomment);
            viewHolder.deletecomment.setVisibility(View.VISIBLE);
            viewHolder.deletecomment.setTag(position);
        } else {
            viewHolder = new ViewHolderItem();
            viewHolder.deletecomment = (TextView) rowView.findViewById(R.id.deletecomment);
            viewHolder.deletecomment.setVisibility(View.GONE);
        }

        txtTitle.setText(daftar_comment.get(Global.NAMA));
        if(id_user.equals(id_user_sqlite) && email.equals("admin")){
            Picasso.with(CommentAdapter.inflater.getContext()).load(R.mipmap.logo).into(imageView);
        } else {
            Picasso.with(CommentAdapter.inflater.getContext()).load(R.drawable.user).into(imageView);
        }

        extratxt.setText(daftar_comment.get(Global.FEEDBACK));
        return rowView;
    }
}

package com.example.setditjenp2mkt.apputs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.setditjenp2mkt.apputs.adapter.ListAdapter;
import com.example.setditjenp2mkt.apputs.utils.JSONParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class KotaActivity extends AppCompatActivity {
    ListView wisata, makanan;
    String[] namawisata = {
            "Masjid Raya Baiturrahman",
            "Air Terjun Blang Kolam",
            "Pantai Lampuuk",
            "Museum Tsunami"
    };

    String[] kuliner = {
            "Ayam Tangkap",
            "Mie Aceh",
            "Kuah Beulangong",
            "Timphan"
    };

    Integer[] imgwisata = {
            R.mipmap.masjidraya,
            R.mipmap.blangkolam,
            R.mipmap.pantailampuuk,
            R.mipmap.museumtsunami
    };

    Integer[] imgkuliner = {
            R.mipmap.ayamtangkap,
            R.mipmap.miaceh,
            R.mipmap.beulangong,
            R.mipmap.timphan
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kota);
        setTitle("Tentang Aceh");

        ListDesc adapter = new ListDesc(this, namawisata, imgwisata);
        wisata = (ListView)findViewById(R.id.wisata_aceh);
        wisata.setAdapter(adapter);

        wisata.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(KotaActivity.this, WisataActivity.class);
                String Selecteditem = namawisata[position];
                Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        ListDesc adapter2 = new ListDesc(this, kuliner, imgkuliner);
        makanan = (ListView)findViewById(R.id.kuliner_aceh);
        makanan.setAdapter(adapter2);

        makanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(KotaActivity.this, KulinerActivity.class);
                String Selecteditem = kuliner[position];
                Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }


}

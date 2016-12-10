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
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.setditjenp2mkt.apputs.adapter.ListAdapter;
import com.example.setditjenp2mkt.apputs.utils.Global;
import com.example.setditjenp2mkt.apputs.utils.ImageLoader;
import com.example.setditjenp2mkt.apputs.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KotaActivity extends AppCompatActivity {
    String id;
    private JSONParser jsonParser = new JSONParser();
    private ProgressDialog progressDialog;
    JSONArray jsonArray = null;

    ImageLoader imageLoader;
    {
        imageLoader = new ImageLoader(null);
    }


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

        id = getIntent().getStringExtra(Global.ID);
        new detailKota().execute();

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

    private class detailKota extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> detailKota = new ArrayList<>();
            detailKota.add(new BasicNameValuePair("id_kota",id));
            JSONObject jsonObject = jsonParser.makeHttpRequest(Global.DETAIL_KOTA, "GET", detailKota);
            try {
                jsonArray = jsonObject.getJSONArray("kota");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Data Json : ", "" + jsonObject);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageView img = (ImageView)findViewById(R.id.imageKota);
                    TextView jdl = (TextView)findViewById(R.id.namakota);
                    TextView konten = (TextView)findViewById(R.id.kontendeskripsi);
                    try{
                        JSONObject js = jsonArray.getJSONObject(0);
                        String judul = js.getString("kota");
                        String isi = js.getString("deskripsi");
//                        String gbr = js.getString("gambar");
                        String gbr = Global.BASE_IMG+ js.getString("gambar");
                        //set data
                        jdl.setText(judul);
                        konten.setText(isi);
                        imageLoader.DisplayImage(gbr, img);
                    }catch (JSONException e){
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(KotaActivity.this);
            progressDialog.setTitle("Harap Tunggu");
            progressDialog.setMessage("Sedang mengambil data");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
        }
    }


}

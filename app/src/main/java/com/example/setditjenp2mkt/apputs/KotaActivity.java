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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.setditjenp2mkt.apputs.adapter.CommentAdapter;
import com.example.setditjenp2mkt.apputs.adapter.KulinerAdapter;
import com.example.setditjenp2mkt.apputs.adapter.ListAdapter;
import com.example.setditjenp2mkt.apputs.adapter.WisataAdapter;
import com.example.setditjenp2mkt.apputs.helpers.SQLiteHandler;
import com.example.setditjenp2mkt.apputs.utils.Global;
import com.example.setditjenp2mkt.apputs.utils.ImageLoader;
import com.example.setditjenp2mkt.apputs.utils.JSONParser;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KotaActivity extends AppCompatActivity {
    String id, add_code;
    private JSONParser jsonParser = new JSONParser();
    private ProgressDialog progressDialog;
    JSONArray jsonArray = null;
    ArrayList<HashMap<String, String>> DaftarKuliner = new ArrayList<>();
    ArrayList<HashMap<String, String>> DaftarWisata = new ArrayList<HashMap<String, String>>();
    WisataAdapter wisataAdapter;
    KulinerAdapter kulinerAdapter;
    private SQLiteHandler db;
    private ProgressBar progressBarW, progressBarK;

    TextView empty_wisata, empty_kuliner;
    ListView wisata, makanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kota);

        id = getIntent().getStringExtra(Global.ID);
        new detailKota().execute();

        empty_wisata = (TextView)findViewById(R.id.emptytext);
        empty_kuliner = (TextView)findViewById(R.id.emptytext1);

        DaftarWisata = new ArrayList<HashMap<String, String>>();
        DaftarKuliner = new ArrayList<HashMap<String, String>>();

        wisata = (ListView)findViewById(R.id.wisata_aceh);
        wisata.setEmptyView(empty_wisata);

        progressBarW = (ProgressBar)findViewById(R.id.progressBarW);
        progressBarK = (ProgressBar)findViewById(R.id.progressBarK);

        new tampilWisata().execute();



        wisata.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map_wisata = DaftarWisata.get(position);
                Toast.makeText(getApplicationContext(), "Data "+map_wisata.get(Global.NAMA_TEMPAT), Toast.LENGTH_SHORT).show();
                Intent a = new Intent(getApplicationContext(),WisataActivity.class);
                a.putExtra(Global.ID, getIntent().getStringExtra(Global.ID));
                a.putExtra(Global.ID_WISATA, map_wisata.get(Global.ID_WISATA));
                a.putExtra(Global.LONGI, map_wisata.get(Global.LONGI));
                a.putExtra(Global.LATI, map_wisata.get(Global.LATI));
                a.putExtra(Global.NAMA_TEMPAT, map_wisata.get(Global.NAMA_TEMPAT));
                startActivity(a);
                finish();
            }
        });

        makanan = (ListView)findViewById(R.id.kuliner_aceh);
        makanan.setEmptyView(empty_kuliner);
        new tampilKuliner().execute();

        makanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map_kuliner = DaftarKuliner.get(position);
                Toast.makeText(getApplicationContext(), "Data "+map_kuliner.get(Global.NAMA_TEMPAT), Toast.LENGTH_SHORT).show();
                Intent a = new Intent(getApplicationContext(), KulinerActivity.class);
                a.putExtra(Global.ID, getIntent().getStringExtra(Global.ID));
                a.putExtra(Global.ID_KULINER, map_kuliner.get(Global.ID_KULINER));
                a.putExtra(Global.LONGI, map_kuliner.get(Global.LONGI));
                a.putExtra(Global.LATI, map_kuliner.get(Global.LATI));
                a.putExtra(Global.NAMA_TEMPAT, map_kuliner.get(Global.NAMA_TEMPAT));
                startActivity(a);
                finish();
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
                        String gbr = Global.BASE_IMG + js.getString("gambar");
                        //set data
                        jdl.setText(judul);
                        konten.setText(isi);
                        Picasso.with(KotaActivity.this).load(gbr).into(img);
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

    private class tampilWisata extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarK.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> listWisata = new ArrayList<>();
            listWisata.add(new BasicNameValuePair("id_kota",id));
            JSONObject jsonObject = jsonParser.makeHttpRequest(Global.GET_WISATA,"GET",listWisata);
            Log.i("Data Json : ", "" + jsonObject);
            try {
                jsonArray = jsonObject.getJSONArray("wisata");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    String id_kota = c.getString("id_kota");
                    String id_wisata = c.getString("id_wisata");
                    String nama_tempat = c.getString("nama_tempat");
                    String lati = c.getString("langi");
                    String longi = c.getString("longi");
                    String gbr = Global.GET_IMAGE_WISATA+ c.getString("gambar");
                    HashMap<String,String> map_wisata = new HashMap<>();
                    map_wisata.put(Global.GAMBAR_WISATA,gbr);
                    map_wisata.put(Global.ID_WISATA,id_wisata);
                    map_wisata.put(Global.ID_KOTA,id_kota);
                    map_wisata.put(Global.NAMA_TEMPAT,nama_tempat);
                    map_wisata.put(Global.LATI,lati);
                    map_wisata.put(Global.LONGI,longi);
                    DaftarWisata.add(map_wisata);
                }
            } catch(JSONException e){

            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SetListWisata(DaftarWisata);
                    progressBarK.setVisibility(View.GONE);
                }
            });
        }
    }

    private class tampilKuliner extends AsyncTask<String,String,String >{

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> listKuliner = new ArrayList<>();
            listKuliner.add(new BasicNameValuePair("id_kota",id));
            JSONObject jsonObject = jsonParser.makeHttpRequest(Global.GET_KULINER,"GET",listKuliner);
            Log.i("Data Json : ", "" + jsonObject);
            try {
                jsonArray = jsonObject.getJSONArray("kuliner");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    String id_kota = c.getString("id_kota");
                    String id_kuliner = c.getString("id_kuliner");
                    String nama_tempat = c.getString("nama_tempat");
                    String lati = c.getString("langi");
                    String longi = c.getString("longi");
                    String gbr = Global.GET_IMAGE_KULINER+ c.getString("gambar");
                    HashMap<String,String> map_kuliner = new HashMap<>();
                    map_kuliner.put(Global.GAMBAR_KULINER,gbr);
                    map_kuliner.put(Global.ID_KULINER,id_kuliner);
                    map_kuliner.put(Global.ID_KOTA,id_kota);
                    map_kuliner.put(Global.NAMA_TEMPAT,nama_tempat);
                    map_kuliner.put(Global.LATI,lati);
                    map_kuliner.put(Global.LONGI,longi);
                    DaftarKuliner.add(map_kuliner);
                }
            } catch(JSONException e){

            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SetListKuliner(DaftarKuliner);
                    progressBarW.setVisibility(View.GONE);
                }
            });
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarW.setVisibility(View.VISIBLE);
        }
    }

    private void SetListWisata(ArrayList<HashMap<String, String>> daftarWisata) {
        if(daftarWisata.size() == 0) {
            wisataAdapter = new WisataAdapter(this, new ArrayList<HashMap<String, String>>());
            empty_wisata.setText("Tidak ada tempat wisata");
            wisataAdapter.notifyDataSetInvalidated();
        } else {
            wisataAdapter = new WisataAdapter(this, daftarWisata);
            wisataAdapter.notifyDataSetChanged();
        }
        wisata.setAdapter(wisataAdapter);
    }

    private void SetListKuliner(ArrayList<HashMap<String, String>> daftarKuliner) {
        if(daftarKuliner.size() == 0) {
            kulinerAdapter = new KulinerAdapter(this, new ArrayList<HashMap<String, String>>());
            empty_kuliner.setText("Tidak ada tempat wisata");
            kulinerAdapter.notifyDataSetInvalidated();
        } else {
            kulinerAdapter = new KulinerAdapter(this, daftarKuliner);
            kulinerAdapter.notifyDataSetChanged();
        }
        makanan.setAdapter(kulinerAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KotaActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        db = new SQLiteHandler(KotaActivity.this);
        HashMap<String, String> user = db.getUserDetails();
        final String id_user_sqlite = user.get("email");
        if (id_user_sqlite.matches("admin")){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_edit_kota, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_deskripsi:
                Intent intent = new Intent(KotaActivity.this, EditActivity.class);
                intent.putExtra(Global.ID, getIntent().getStringExtra(Global.ID));
                startActivity(intent);
                finish();
                return true;
            case R.id.edit_wisata:
                Intent w = new Intent(KotaActivity.this, NewActivity.class);
                w.putExtra(Global.ID, getIntent().getStringExtra(Global.ID));
                w.putExtra(Global.ADD_CODE, "wisata");
                startActivity(w);
                finish();
                return true;
            case R.id.edit_kuliner:
                Intent k = new Intent(KotaActivity.this, NewActivity.class);
                k.putExtra(Global.ID, getIntent().getStringExtra(Global.ID));
                k.putExtra(Global.ADD_CODE, "kuliner");
                startActivity(k);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

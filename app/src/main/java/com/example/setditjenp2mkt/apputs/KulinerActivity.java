package com.example.setditjenp2mkt.apputs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.setditjenp2mkt.apputs.utils.Global;
import com.example.setditjenp2mkt.apputs.utils.ImageLoader;
import com.example.setditjenp2mkt.apputs.utils.JSONParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KulinerActivity extends AppCompatActivity implements OnMapReadyCallback {

    String id, id_kuliner;
    double lati, longi;
    private JSONParser jsonParser = new JSONParser();
    private ProgressDialog progressDialog;
    JSONArray jsonArray = null;
    ImageLoader imageLoader;
    {
        imageLoader = new ImageLoader(null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuliner);
        id = getIntent().getStringExtra(Global.ID);
        id_kuliner = getIntent().getStringExtra(Global.ID_KULINER);
        new detailKuliner().execute();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setTitle("Info Kuliner");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lati,longi)));
    }

    private class detailKuliner extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> detailKuliner = new ArrayList<>();
            detailKuliner.add(new BasicNameValuePair("id_kota",id));
            detailKuliner.add(new BasicNameValuePair("id_tempat_kuliner",id_kuliner));
            JSONObject jsonObject = jsonParser.makeHttpRequest(Global.DETAIL_KULINER, "GET", detailKuliner);
            try {
                jsonArray = jsonObject.getJSONArray("kuliner");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("Data Json : ", "" + jsonObject);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageView img = (ImageView)findViewById(R.id.icon);
                    TextView jdl = (TextView)findViewById(R.id.namakuliner);
                    TextView konten = (TextView)findViewById(R.id.kontendeskripsi);
                    try{
                        JSONObject js = jsonArray.getJSONObject(0);
                        String judul = js.getString("nama_tempat");
                        String isi = js.getString("deskripsi");
                        String gbr = Global.BASE_IMG+ js.getString("gambar");
                        String lat = js.getString("latitude");
                        String longit = js.getString("longitude");
                        //set data
                        lati = Double.valueOf(lat);
                        longi = Double.valueOf(longit);
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
            progressDialog = new ProgressDialog(KulinerActivity.this);
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

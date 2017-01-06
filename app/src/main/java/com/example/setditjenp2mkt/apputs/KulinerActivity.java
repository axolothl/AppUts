package com.example.setditjenp2mkt.apputs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.setditjenp2mkt.apputs.helpers.SQLiteHandler;
import com.example.setditjenp2mkt.apputs.utils.Global;
import com.example.setditjenp2mkt.apputs.utils.ImageLoader;
import com.example.setditjenp2mkt.apputs.utils.JSONParser;
import com.example.setditjenp2mkt.apputs.utils.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KulinerActivity extends AppCompatActivity implements OnMapReadyCallback {

    String id, id_kuliner;
    Double lati, longi;
    private static final String TAG = KulinerActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private boolean mapReady=false;
    private GoogleMap mMap;
    JSONArray jsonArray = null;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuliner);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        id = getIntent().getStringExtra(Global.ID);
        id_kuliner = getIntent().getStringExtra(Global.ID_KULINER);
        lati = Double.parseDouble(getIntent().getStringExtra(Global.LATI));
        longi = Double.parseDouble(getIntent().getStringExtra(Global.LONGI));
        detailKuliner(id,id_kuliner);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_kuliner);
        mapFragment.getMapAsync(KulinerActivity.this);
    }

    private void detailKuliner(final String id_kota, final String id_kuliner) {
        String tag_string_req = "req_load_kuliner";

        progressDialog.setMessage("Memuat ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, Global.DETAIL_KULINER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Data Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    jsonArray = jObj.getJSONArray("kuliner");
                    Log.i("Data Json : ", "" + jObj);

                    ImageView img = (ImageView)findViewById(R.id.icon);
                    TextView jdl = (TextView)findViewById(R.id.namakuliner);
                    TextView konten = (TextView)findViewById(R.id.kontendeskripsi);

                    JSONObject js = jsonArray.getJSONObject(0);
                    String judul = js.getString("nama_tempat");
                    String isi = js.getString("deskripsi");
                    String gbr = Global.GET_IMAGE_KULINER+ js.getString("gambar");
                    //set data
                    jdl.setText(judul);
                    konten.setText(isi);
//                    imageLoader.DisplayImage(gbr, img);
                    Picasso.with(KulinerActivity.this).load(gbr).into(img);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Tidak Dapat Memuat Data: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_kota", id_kota);
                params.put("id_kuliner", id_kuliner);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void onMapReady(GoogleMap googleMap) {
        mapReady=true;
        mMap = googleMap;
        LatLng currentPos = new LatLng(lati,longi);
        CameraPosition target = CameraPosition.builder().target(currentPos).zoom(14).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(lati,longi)));
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}

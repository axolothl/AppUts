package com.example.setditjenp2mkt.apputs;

import android.app.ProgressDialog;
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
import com.example.setditjenp2mkt.apputs.utils.Global;
import com.example.setditjenp2mkt.apputs.utils.ImageLoader;
import com.example.setditjenp2mkt.apputs.utils.JSONParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WisataActivity extends AppCompatActivity implements OnMapReadyCallback {

    String id, id_wisata;
    private static final String TAG = WisataActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    Double lati, longi;
    private boolean mapReady=false;
    private GoogleMap mMap;
    JSONArray jsonArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        id = getIntent().getStringExtra(Global.ID);
        id_wisata = getIntent().getStringExtra(Global.ID_WISATA);
        lati = Double.parseDouble(getIntent().getStringExtra(Global.LATI));
        longi = Double.parseDouble(getIntent().getStringExtra(Global.LONGI));
        detailWisata(id,id_wisata);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_wisata);
        mapFragment.getMapAsync(WisataActivity.this);
    }

    private void detailWisata(final String id_kota, final String id_wisata) {
        String tag_string_req = "req_load_wisata";

        pDialog.setMessage("Memuat ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, Global.DETAIL_WISATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Data Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    jsonArray = jObj.getJSONArray("wisata");
                    Log.i("Data Json : ", "" + jObj);

                    ImageView img = (ImageView)findViewById(R.id.icon);
                    TextView jdl = (TextView)findViewById(R.id.namawisata);
                    TextView konten = (TextView)findViewById(R.id.kontendeskripsi);

                    JSONObject js = jsonArray.getJSONObject(0);
                    String judul = js.getString("nama_tempat");
                    String isi = js.getString("deskripsi");
                    String gbr = Global.GET_IMAGE_WISATA+ js.getString("gambar");
                    //set data
                    jdl.setText(judul);
                    konten.setText(isi);
//                    imageLoader.DisplayImage(gbr, img);
                    Picasso.with(WisataActivity.this).load(gbr).into(img);



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
                params.put("id_wisata", id_wisata);

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
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}

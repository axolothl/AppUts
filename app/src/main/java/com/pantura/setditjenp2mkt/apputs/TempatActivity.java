package com.pantura.setditjenp2mkt.apputs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.pantura.setditjenp2mkt.apputs.utils.Global;
import com.pantura.setditjenp2mkt.apputs.utils.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TempatActivity extends AppCompatActivity {

    private static final String TAG = TempatActivity.class.getSimpleName();
    private EditText namaTempat, deskripsiTempat, mapLatitude, mapLongitude;
    private FloatingActionButton ok;
    private FloatingActionButton cancel;
    String id_kota, id_tempat, jenis, url, nama_tempat;
    Double lati, longi;
    JSONArray jsonArray = null;
    JSONParser jsonParser;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat);

        id_kota = getIntent().getStringExtra(Global.ID);
        jenis = getIntent().getStringExtra(Global.JENIS);
        lati = Double.parseDouble(getIntent().getStringExtra(Global.LATI));
        longi = Double.parseDouble(getIntent().getStringExtra(Global.LONGI));
        nama_tempat = getIntent().getStringExtra(Global.NAMA_TEMPAT);

        if(jenis.equals("wisata")){
            id_tempat = getIntent().getStringExtra(Global.ID_WISATA);
        } else if(jenis.equals("kuliner")){
            id_tempat = getIntent().getStringExtra(Global.ID_KULINER);
        }

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        ok = (FloatingActionButton)findViewById(R.id.ok);
        cancel = (FloatingActionButton)findViewById(R.id.cancel);

        namaTempat = (EditText)findViewById(R.id.inputNamaTempat);
        deskripsiTempat = (EditText)findViewById(R.id.editdeskripsi);
        mapLatitude =(EditText) findViewById(R.id.inputMapLatitude);
        mapLongitude =(EditText) findViewById(R.id.inputMapLongitude);

        detailTempat(jenis,id_kota,id_tempat);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_tempat = namaTempat.getText().toString();
                String deskripsi = deskripsiTempat.getText().toString();
                String lati = mapLatitude.getText().toString();
                String longi = mapLongitude.getText().toString();
                editTempat(jenis,id_tempat,id_kota,nama_tempat,deskripsi,lati,longi);
                if(jenis.equals("wisata")){
                    Intent intent = new Intent(TempatActivity.this, WisataActivity.class);
                    intent.putExtra(Global.ID,id_kota);
                    intent.putExtra(Global.ID_WISATA,id_tempat);
                    intent.putExtra(Global.LATI,lati.toString());
                    intent.putExtra(Global.LONGI,longi.toString());
                    intent.putExtra(Global.NAMA_TEMPAT,nama_tempat);
                    startActivity(intent);
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                } else if(jenis.equals("kuliner")){
                    Intent intent = new Intent(TempatActivity.this, KulinerActivity.class);
                    intent.putExtra(Global.ID,id_kota);
                    intent.putExtra(Global.ID_KULINER,id_tempat);
                    intent.putExtra(Global.LATI,lati.toString());
                    intent.putExtra(Global.LONGI,longi.toString());
                    intent.putExtra(Global.NAMA_TEMPAT,nama_tempat);
                    startActivity(intent);
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                if(jenis.equals("wisata")){
                    Toast.makeText(getApplicationContext(), "Tempat Wisata Berhasil Diedit", Toast.LENGTH_LONG).show();
                } else if(jenis.equals("kuliner")){
                    Toast.makeText(getApplicationContext(), "Tempat Kuliner Berhasil Diedit", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jenis.equals("wisata")){
                    Intent intent = new Intent(TempatActivity.this, WisataActivity.class);
                    intent.putExtra(Global.ID,id_kota);
                    intent.putExtra(Global.ID_WISATA,id_tempat);
                    intent.putExtra(Global.LATI,lati.toString());
                    intent.putExtra(Global.LONGI,longi.toString());
                    intent.putExtra(Global.NAMA_TEMPAT,nama_tempat);
                    startActivity(intent);
                    finish();
                } else if(jenis.equals("kuliner")){
                    Intent intent = new Intent(TempatActivity.this, KulinerActivity.class);
                    intent.putExtra(Global.ID,id_kota);
                    intent.putExtra(Global.ID_KULINER,id_tempat);
                    intent.putExtra(Global.LATI,lati.toString());
                    intent.putExtra(Global.LONGI,longi.toString());
                    intent.putExtra(Global.NAMA_TEMPAT,nama_tempat);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    private void detailTempat(final String jenis, final String id_kota, final String id_tempat) {
        if(jenis.equals("wisata")){
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

                        JSONObject js = jsonArray.getJSONObject(0);
                        String judul = js.getString("nama_tempat");
                        String isi = js.getString("deskripsi");
                        String lati = js.getString("langi");
                        String longi = js.getString("longi");

                        //set data
                        namaTempat.setText(judul);
                        deskripsiTempat.setText(isi);
                        mapLatitude.setText(lati);
                        mapLongitude.setText(longi);
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
                    params.put("id_wisata", id_tempat);

                    return params;
                }

            };
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        } else if(jenis.equals("kuliner")){
            String tag_string_req = "req_load_kuliner";

            pDialog.setMessage("Memuat ...");
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

                        JSONObject js = jsonArray.getJSONObject(0);
                        String judul = js.getString("nama_tempat");
                        String isi = js.getString("deskripsi");
                        String lati = js.getString("langi");
                        String longi = js.getString("longi");

                        //set data
                        namaTempat.setText(judul);
                        deskripsiTempat.setText(isi);
                        mapLatitude.setText(lati);
                        mapLongitude.setText(longi);
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
                    params.put("id_kuliner", id_tempat);

                    return params;
                }

            };
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }

    }


    private void editTempat(final String jenis, final String id_tempat, final String id_kota, final String nama_tempat, final String deskripsi, final String map_latitude, final String map_longitude){
        if(jenis.equals("wisata")){
            url = Global.EDIT_DATA_WISATA;
            String tag_string_req = "req_edit_wisata";
            pDialog.setMessage("Mengubah Data ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Edit Wisata Response: " + response.toString());

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");
                        if (!error) {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "Data Wisata Berhasil Diubah", Toast.LENGTH_LONG).show();
                        } else {
                            String errorMsg = jsonObject.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Edit Wisata Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();

                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_kota", id_kota);
                    params.put("id_wisata", id_tempat);
                    params.put("nama_tempat", nama_tempat);
                    params.put("lati", map_latitude);
                    params.put("longi", map_longitude);
                    params.put("nama_tempat", nama_tempat);
                    params.put("deskripsi", deskripsi);
                    return params;
                }
            };
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        } else if(jenis.equals("kuliner")){
            url = Global.EDIT_DATA_KULINER;
            String tag_string_req = "req_edit_kuliner";
            pDialog.setMessage("Mengubah Data ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Edit Kuliner Response: " + response.toString());

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");
                        if (!error) {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "Data Kuliner Berhasil Diubah", Toast.LENGTH_LONG).show();
                        } else {
                            String errorMsg = jsonObject.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Edit Kuliner Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();

                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id_kota", id_kota);
                    params.put("id_kuliner", id_tempat);
                    params.put("nama_tempat", nama_tempat);
                    params.put("lati", map_latitude);
                    params.put("longi", map_longitude);
                    params.put("nama_tempat", nama_tempat);
                    params.put("deskripsi", deskripsi);
                    return params;
                }
            };
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }

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

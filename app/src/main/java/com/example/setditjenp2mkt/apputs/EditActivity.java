package com.example.setditjenp2mkt.apputs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.setditjenp2mkt.apputs.utils.Global;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.setditjenp2mkt.apputs.R.id.editdeskripsi;
import static com.example.setditjenp2mkt.apputs.R.id.editnamakota;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = EditActivity.class.getSimpleName();
    EditText editDeskripsi, editKota;
    TextView juduldeskripsi, namaKota;
    private FloatingActionButton ok;
    private FloatingActionButton cancel;
    String id_kota;
    JSONArray jsonArray = null;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        id_kota = getIntent().getStringExtra(Global.ID);
        editDeskripsi = (EditText)findViewById(R.id.editdeskripsi);
        juduldeskripsi = (TextView)findViewById(R.id.inputdeskripsi);
        editKota = (EditText)findViewById(editnamakota);
        namaKota = (TextView)findViewById(R.id.inputnamakota);

        LoadDataKota(id_kota);

        ok = (FloatingActionButton)findViewById(R.id.ok);
        cancel = (FloatingActionButton)findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, KotaActivity.class);
                String namakota = editKota.getText().toString();
                String deskripsi = editDeskripsi.getText().toString();
                String gambar = "";
                EditKota(id_kota,deskripsi,gambar,namakota);
                startActivity(intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, KotaActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void EditKota(final String id_kota, final String deskripsi, final String gambar, final String nama_kota){

    }

    private void LoadDataKota(final String id_kota){
        String tag_string_req = "req_load_kota";

        pDialog.setMessage("Memuat ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, Global.DETAIL_KOTA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Data Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    jsonArray = jObj.getJSONArray("kota");
                    Log.i("Data Json : ", "" + jObj);

                    JSONObject js = jsonArray.getJSONObject(0);
                    String judul = js.getString("nama_tempat");
                    String isi = js.getString("deskripsi");
                    String gbr = Global.GET_IMAGE_WISATA+ js.getString("gambar");

                    //set data
                    editKota.setText(judul);
                    editDeskripsi.setText(isi);
//                    Picasso.with(EditActivity.this).load(gbr).into(img);
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
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
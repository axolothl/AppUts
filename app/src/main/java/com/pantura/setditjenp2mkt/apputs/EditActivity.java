package com.pantura.setditjenp2mkt.apputs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import static com.pantura.setditjenp2mkt.apputs.R.id.editnamakota;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = EditActivity.class.getSimpleName();
    EditText editDeskripsi, editKota;
    TextView juduldeskripsi, namaKota;
    private FloatingActionButton ok;
    private FloatingActionButton cancel;
    String id_kota;
    JSONArray jsonArray = null;
    JSONParser jsonParser;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        id_kota = getIntent().getStringExtra(Global.ID);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

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
                String namakota = editKota.getText().toString();
                String deskripsi = editDeskripsi.getText().toString();
                EditKota(id_kota,deskripsi,namakota);
                Toast.makeText(getApplicationContext(), "Kota Berhasil Disunting", Toast.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, KotaActivity.class);
                intent.putExtra(Global.ID, id_kota);
                startActivity(intent);
                finish();
            }
        });

    }

    private void EditKota(final String id_kota, final String deskripsi, final String nama_kota){
        String tag_string_req = "req_edit_kota";
        pDialog.setMessage("Mengubah Data ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, Global.EDIT_DATA_KOTA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Input Kota Response: " + response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        hideDialog();
                        Toast.makeText(getApplicationContext(), "Data Kota Berhasil Diubah", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditActivity.this, KotaActivity.class);
                        intent.putExtra(Global.ID, id_kota);
                        overridePendingTransition( 0, 0);
                        startActivity(intent);
                        finish();
                        System.gc();
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
                Log.e(TAG, "Edit Kota Error: " + error.getMessage());
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
                params.put("nama_kota", nama_kota);
                params.put("deskripsi", deskripsi);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }


    private void LoadDataKota(final String id_kota){
        String tag_string_req = "req_load_kota";

        pDialog.setMessage("Memuat ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET, Global.DETAIL_KOTA+"?id_kota="+id_kota, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Data Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    jsonArray = jObj.getJSONArray("kota");
                    Log.i("Data Json : ", "" + jObj);

                    JSONObject js = jsonArray.getJSONObject(0);
                    String judul = js.getString("kota");
                    String isi = js.getString("deskripsi");

                    //set data
                    editKota.setText(judul);
                    editDeskripsi.setText(isi);
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
                /*
                   Caution:
                   This part is not used since the method request created in PANTURA API was GET
                */
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditActivity.this, KotaActivity.class);
        intent.putExtra(Global.ID, id_kota);
        startActivity(intent);
        finish();
        return;
    }

}
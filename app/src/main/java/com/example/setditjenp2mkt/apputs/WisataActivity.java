package com.example.setditjenp2mkt.apputs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.setditjenp2mkt.apputs.adapter.CommentAdapter;
import com.example.setditjenp2mkt.apputs.helpers.SQLiteHandler;
import com.example.setditjenp2mkt.apputs.utils.Global;
import com.example.setditjenp2mkt.apputs.utils.ImageLoader;
import com.example.setditjenp2mkt.apputs.utils.JSONParser;
import com.example.setditjenp2mkt.apputs.utils.SessionManager;
import com.example.setditjenp2mkt.apputs.utils.UIUtils;
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
    private SQLiteHandler db;
    private SessionManager session;
    EditText komen;
    Button submit;
    ListView komentar;
    CommentAdapter commentAdapter;
    public boolean komen_check = false;
    ArrayList<HashMap<String, String>> DaftarKomen = new ArrayList<>();
    TextView emptyTV;

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
        DaftarKomen = new ArrayList<HashMap<String, String>>();

        detailWisata(id,id_wisata);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_wisata);
        mapFragment.getMapAsync(WisataActivity.this);

        komentar = (ListView)findViewById(R.id.komen);
        komentar.setItemsCanFocus(true);

        emptyTV = (TextView)findViewById(R.id.emptytext);

        db = new SQLiteHandler(getApplicationContext());

        komen = (EditText)findViewById(R.id.editKomentar);
        submit = (Button)findViewById(R.id.submit);

        HashMap<String, String> user = db.getUserDetails();
        final String id_user = user.get("id_user");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (komen.getText().toString() != "") {
                    submitKomentar(id, id_wisata, id_user, komen.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Lengkapi field", Toast.LENGTH_SHORT).show();
                }
                Intent reOpen = new Intent (WisataActivity.this, WisataActivity.class);
                reOpen.putExtra(Global.ID, id);
                reOpen.putExtra(Global.ID_WISATA, id_wisata);
                reOpen.putExtra(Global.LONGI, String.valueOf(longi));
                reOpen.putExtra(Global.LATI, String.valueOf(lati));
                startActivity(reOpen);
                //finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
                komen_check = true;
                komen.setText("");
            }
        });

    }

    public void onDelete_click(View view) {
        final int position = (Integer) view.getTag();
        HashMap<String,String> map = DaftarKomen.get(position);
        String id_feedback_tw = map.get(Global.ID_FEEDBACK_TW);
        String id_kota = map.get(Global.ID_KOTA);
        String id_user = map.get(Global.ID_USER);
        String id_wisata = map.get(Global.ID_WISATA);
        deleteKomentar(id_kota,id_wisata,id_user,id_feedback_tw);
        Intent reOpen = new Intent (WisataActivity.this, WisataActivity.class);
        reOpen.putExtra(Global.ID, id);
        reOpen.putExtra(Global.ID_WISATA, id_wisata);
        reOpen.putExtra(Global.LONGI, String.valueOf(longi));
        reOpen.putExtra(Global.LATI, String.valueOf(lati));
        startActivity(reOpen);
        //finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
        Toast.makeText(getApplicationContext(), "Komentar telah dihapus", Toast.LENGTH_SHORT).show();
    }

    private void deleteKomentar(final String id_kota, final String id_wisata, final String id_user, final String id_feedback_tw){
        String tag_string_req = "req_delete_komentar";
        pDialog.setMessage("Deleting Comment ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, Global.DELETE_KOMENTAR_WISATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Comment Delete Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Komentar berhasil dihapus", Toast.LENGTH_LONG).show();
                    } else {
                        String errorMsg = jsonObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                loadKomentar(id_kota,id_wisata);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Comment Delete Error: " + error.getMessage());
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
                params.put("id_wisata", id_wisata);
                params.put("id_user", id_user);
                params.put("id_feedback_tw", id_feedback_tw);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void loadKomentar(final String id_kota, final String id_wisata){
        String tag_string_req = "req_load_komentar";

        StringRequest strReq = new StringRequest(Request.Method.POST, Global.LOAD_KOMENTAR_WISATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Load Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    jsonArray = jObj.getJSONArray("comment_w");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject c = jsonArray.getJSONObject(i);
                        String id_kota = c.getString("id_kota");
                        String id_wisata = c.getString("id_wisata");
                        String feedback = c.getString("feedback");
                        String id_user = c.getString("id_user");
                        String nama = c.getString("name");
                        String id_feedback_tw = c.getString("id_feedback_tw");
                        HashMap<String,String> map_komen = new HashMap<>();
                        map_komen.put(Global.ID_WISATA,id_wisata);
                        map_komen.put(Global.ID_KOTA,id_kota);
                        map_komen.put(Global.FEEDBACK,feedback);
                        map_komen.put(Global.ID_USER,id_user);
                        map_komen.put(Global.NAMA,nama);
                        map_komen.put(Global.ID_FEEDBACK_TW,id_feedback_tw);
                        DaftarKomen.add(map_komen);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SetListComment(DaftarKomen);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Comment Load Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

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
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void submitKomentar(final String id_kota, final String id_wisata, final String id_user, final String feedback){
        String tag_string_req = "req_submit_komentar";
        pDialog.setMessage("Posting ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, Global.SUBMIT_KOMENTAR_WISATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Comment Submit Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Komentar berhasil ditambahkan", Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Comment Posting Error: " + error.getMessage());
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
                params.put("id_wisata", id_wisata);
                params.put("id_user", id_user);
                params.put("feedback", feedback);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
                    Picasso.with(WisataActivity.this).load(gbr).into(img);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                loadKomentar(id_kota,id_wisata);
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

    private void SetListComment(ArrayList<HashMap<String, String>> daftarKomen) {
        if(daftarKomen.size() == 0) {
            commentAdapter = new CommentAdapter(this, new ArrayList<HashMap<String, String>>());
            emptyTV.setText("Tidak ada komentar");
            komentar.setAdapter(commentAdapter);
            commentAdapter.notifyDataSetInvalidated();
        } else {
            commentAdapter = new CommentAdapter(this, daftarKomen);
            komentar.setAdapter(commentAdapter);
            UIUtils.setListViewHeightBasedOnItems(komentar);
            commentAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WisataActivity.this, KotaActivity.class);
        intent.putExtra(Global.ID, id);
        startActivity(intent);
        finish();
        return;
    }

}

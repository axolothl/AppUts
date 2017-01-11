package com.example.setditjenp2mkt.apputs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.setditjenp2mkt.apputs.utils.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewActivity extends AppCompatActivity {

    private EditText namaTempat, deskripsiTempat, mapLatitude, mapLongitude;
    private FloatingActionButton ok;
    private FloatingActionButton cancel;
    private Button buttonChoose;
    private Bitmap bitmap;
    private ImageView imageTempat;
    private static final String TAG = NewActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private int PICK_IMAGE_REQUEST = 1;
    private TextView imageName;
    private String url, s_add_code, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        id = getIntent().getStringExtra(Global.ID);
        s_add_code = getIntent().getStringExtra(Global.ADD_CODE);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        namaTempat = (EditText)findViewById(R.id.inputNamaTempat);
        deskripsiTempat = (EditText)findViewById(R.id.editdeskripsi);
        mapLatitude =(EditText) findViewById(R.id.inputMapLatitude);
        mapLongitude =(EditText) findViewById(R.id.inputMapLongitude);
        imageTempat  = (ImageView) findViewById(R.id.imageTempat);
        imageName = (TextView) findViewById(R.id.textName);

        ok = (FloatingActionButton)findViewById(R.id.ok);
        cancel = (FloatingActionButton)findViewById(R.id.cancel);
        buttonChoose = (Button) findViewById(R.id.uploadButton);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_tempat = namaTempat.getText().toString();
                String deskripsi = deskripsiTempat.getText().toString();
                String lati = mapLatitude.getText().toString();
                String longi = mapLongitude.getText().toString();
                String gambar = getStringImage(bitmap);
                String nama_gambar = imageName.getText().toString();
                submitTempat(s_add_code,id,nama_tempat,deskripsi,lati,longi,nama_gambar,gambar);
                Intent intent = new Intent(NewActivity.this, KotaActivity.class);
                intent.putExtra(Global.ID,id);
                startActivity(intent);
                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
                if(s_add_code.equals("wisata")){
                    Toast.makeText(getApplicationContext(), "Tempat Wisata Berhasil Ditambahkan", Toast.LENGTH_LONG).show();
                } else if(s_add_code.equals("kuliner")){
                    Toast.makeText(getApplicationContext(), "Tempat Kuliner Berhasil Ditambahkan", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewActivity.this, AddActivity.class);
                intent.putExtra(Global.ID,id);
                startActivity(intent);
                finish();
            }
        });

    }

    private void submitTempat(final String add_code, final String id_kota, final String nama_tempat, final String deskripsi, final String map_latitude, final String map_longitude, final String nama_gambar, final String gambar){
        if(add_code.equals("wisata")){
            url = Global.INPUT_DATA_WISATA;
            String tag_string_req = "req_submit_wisata";
            pDialog.setMessage("Memasukkan Data ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Input Wisata Response: " + response.toString());

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");
                        if (!error) {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "Data Wisata Berhasil Ditambahkan", Toast.LENGTH_LONG).show();
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
                    Log.e(TAG, "Input Wisata Error: " + error.getMessage());
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
                    params.put("nama_tempat", nama_tempat);
                    params.put("lati", map_latitude);
                    params.put("longi", map_longitude);
                    params.put("nama_tempat", nama_tempat);
                    params.put("deskripsi", deskripsi);
                    params.put("gambar", gambar);
                    params.put("nama_gambar", nama_gambar);
                    return params;
                }
            };
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        } else if(add_code.equals("kuliner")){
            url = Global.INPUT_DATA_KULINER;
            String tag_string_req = "req_submit_kuliner";
            pDialog.setMessage("Memasukkan Data ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Input Kuliner Response: " + response.toString());

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean error = jsonObject.getBoolean("error");
                        if (!error) {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), "Data Kuliner Berhasil Ditambahkan", Toast.LENGTH_LONG).show();
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
                    Log.e(TAG, "Input Kuliner Error: " + error.getMessage());
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
                    params.put("nama_tempat", nama_tempat);
                    params.put("lati", map_latitude);
                    params.put("longi", map_longitude);
                    params.put("nama_tempat", nama_tempat);
                    params.put("deskripsi", deskripsi);
                    params.put("gambar", gambar);
                    params.put("nama_gambar", nama_gambar);
                    return params;
                }
            };
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        }


    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public String getStringImage(Bitmap bitmap){
//        bitmap = Bitmap.createScaledBitmap(bitmap,100,100,true);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=null;
        try{
            System.gc();
            temp= Base64.encodeToString(b, Base64.DEFAULT);
        }catch(Exception e){
            e.printStackTrace();

        }catch(OutOfMemoryError e){
            baos=new  ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,50, baos);
            b=baos.toByteArray();
            temp=Base64.encodeToString(b, Base64.DEFAULT);
            Log.e("EWN", "Out of memory error catched");
        }
        return temp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageTempat.setImageBitmap(bitmap);
                String selectedPath = filePath.getPath();
                File f = new File(selectedPath);
                imageName.setText(f.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewActivity.this, KotaActivity.class);
        intent.putExtra(Global.ID,id);
        startActivity(intent);
        finish();
        return;
    }

}

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
import com.example.setditjenp2mkt.apputs.utils.ImageNicer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.bitmap;

public class AddActivity extends AppCompatActivity {
    private EditText editnamakota, editdeskripsi;
    private FloatingActionButton ok;
    private FloatingActionButton cancel;
    private Button buttonChoose;
    private Bitmap bitmap;
    private ImageView imageKota;
    private static final String TAG = AddActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private int PICK_IMAGE_REQUEST = 1;
    private TextView imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        editnamakota = (EditText)findViewById(R.id.editnamakota);
        editdeskripsi = (EditText)findViewById(R.id.editdeskripsi);
        imageKota  = (ImageView) findViewById(R.id.imageKota);
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
                String namakota = editnamakota.getText().toString();
                String deskripsi = editdeskripsi.getText().toString();
                String gambar = getStringImage(bitmap);
                String nama_gambar = imageName.getText().toString();
                tambahKota(namakota,deskripsi,gambar, nama_gambar);
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                overridePendingTransition(0,0);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Kota Berhasil Ditambahkan", Toast.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void tambahKota(final String nama_kota, final String deskripsi, final String gambar, final String nama_gambar){
        String tag_string_req = "req_input_kota";
        pDialog.setMessage("Memasukkan Data ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, Global.INPUT_DATA_KOTA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Input Kota Response: " + response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        hideDialog();
                        Toast.makeText(getApplicationContext(), "Data Kota Berhasil Ditambahkan", Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "Input Kota Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama_kota", nama_kota);
                params.put("deskripsi", deskripsi);
                params.put("gambar", gambar);
                params.put("nama_gambar", nama_gambar);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

//    public String getStringImage(Bitmap bmp){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        //bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
////        String bmps = bmp.toString();
////        Integer bmpInt = Integer.parseInt(bmps);
////        ImageNicer.decodeSampledBitmapFromResource(this.getResources(),bmpInt,100,100);
//        byte[] imageBytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        return encodedImage;
//    }

    public String getStringImage(Bitmap bitmap){
//        bitmap = Bitmap.createScaledBitmap(bitmap,100,100,true);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=null;
        try{
            System.gc();
            temp=Base64.encodeToString(b, Base64.DEFAULT);
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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
                imageKota.setImageBitmap(bitmap);
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
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return;
    }

}
package com.pantura.setditjenp2mkt.apputs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pantura.setditjenp2mkt.apputs.adapter.ListAdapter;
import com.pantura.setditjenp2mkt.apputs.helpers.SQLiteHandler;
import com.pantura.setditjenp2mkt.apputs.utils.Global;
import com.pantura.setditjenp2mkt.apputs.utils.JSONParser;
import com.pantura.setditjenp2mkt.apputs.utils.SessionManager;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> DaftarKota = new ArrayList<>();
    private ProgressDialog progressDialog;
    JSONArray jsonArray = null;
    ListAdapter listAdapter;
    static final int tampil_error=1;
    public String lo_Koneksi,isi ;
    private SQLiteHandler db;
    private SessionManager session;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        list = (ListView)findViewById(R.id.list);
        DaftarKota = new ArrayList<>();
        if (cekStatus(this)){
            Koneksi lo_Koneksi = new Koneksi();
            isi = lo_Koneksi.isi_koneksi();
            new tampilData().execute();
        } else{
            showDialog(tampil_error);
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map = DaftarKota.get(position);
                Toast.makeText(getApplicationContext(), "Data "+map.get(Global.KOTA), Toast.LENGTH_SHORT).show();
                Intent a = new Intent(getApplicationContext(),KotaActivity.class);
                a.putExtra(Global.ID, map.get(Global.ID));
                startActivity(a);
                finish();
            }
        });
    }

    public boolean cekStatus(Context cek){
        ConnectivityManager cm = (ConnectivityManager)cek.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null && info.isConnected()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch(id){
            case tampil_error:
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
                errorDialog.setTitle("Koneksi error");
                errorDialog.setMessage("Tidak dapat terhubung ke jaringan");
                errorDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent exit = new Intent(Intent.ACTION_MAIN);
                        exit.addCategory(Intent.CATEGORY_HOME);
                        exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.this.finish();
                        startActivity(exit);
                    }
                });
                AlertDialog errorAlert = errorDialog.create();
                return errorAlert;
            default:
                break;
        }
        return dialog;
    }

    private class tampilData extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Harap Tunggu");
            progressDialog.setMessage("Sedang mengambil data");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> listKota = new ArrayList<>();
            JSONObject jsonObject = jsonParser.makeHttpRequest(Global.GET_KOTA,"GET",listKota);
            Log.i("Data Json : ", "" + jsonObject);
            try {
                jsonArray = jsonObject.getJSONArray("kota");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    String id = c.getString("id");
                    String kota = c.getString("kota");
                    String gbr = Global.BASE_IMG+ c.getString("gambar");
                    HashMap<String,String> map = new HashMap<>();
                    map.put(Global.ID,id);
                    map.put(Global.KOTA,kota);
                    map.put(Global.GAMBAR,gbr);
                    DaftarKota.add(map);
                }
            }catch(JSONException e){

            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SetlistData(DaftarKota);
                }
            });
        }
    }

    private void SetlistData(ArrayList<HashMap<String, String>> daftarKota) {
        listAdapter = new ListAdapter(this, daftarKota);
        list.setAdapter(listAdapter);
    }

    private class Koneksi {
        public String isi_koneksi() {
            String isi = "http://mobcompfinal.pe.hu/";
            return isi;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        return;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        db = new SQLiteHandler(MainActivity.this);
        session = new SessionManager(MainActivity.this);
        HashMap<String, String> user = db.getUserDetails();
        final String id_user_sqlite = user.get("email");

        if (id_user_sqlite.matches("admin")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_utama_admin, menu);
//                return super.onCreateOptionsMenu(menu);
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_utama, menu);
//                return super.onCreateOptionsMenu(menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.logout:
                logoutUser();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
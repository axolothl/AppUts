package com.example.setditjenp2mkt.apputs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class WisataActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView namawisata, deskripsi_wisata, emptyTV, delete;
    ImageView imgwisata;
    public ListView komentar;
    EditText komen;
    Button submit;
    ListDesc listDesc;
    public int position, city_position, status_check;
    public boolean komen_check = false;
    public String city, username;
    public static ArrayList<ArrayList<ArrayList<String>>> all_account_wisata = new ArrayList<ArrayList<ArrayList<String>>>();
    public static ArrayList<ArrayList<ArrayList<Integer>>> all_profpict_wisata = new ArrayList<ArrayList<ArrayList<Integer>>>();
    public static ArrayList<ArrayList<ArrayList<String>>> all_comment_wisata = new ArrayList<ArrayList<ArrayList<String>>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata);
        namawisata = (TextView)findViewById(R.id.namawisata);
        deskripsi_wisata = (TextView)findViewById(R.id.kontendeskripsi);
        emptyTV = (TextView)findViewById(R.id.emptytext);
        imgwisata = (ImageView)findViewById(R.id.icon);
        komen = (EditText)findViewById(R.id.editKomentar);
        submit = (Button)findViewById(R.id.submit);
        komentar = (ListView)findViewById(R.id.komen);
        komentar.setItemsCanFocus(true);
        setTitle("Tempat Wisata");
        Intent intent = getIntent();
        status_check = intent.getIntExtra("status", 0);
        String tempatwisata = intent.getStringExtra("namawisata");
        position = intent.getIntExtra("position", 0);
        city_position = intent.getIntExtra("city_position", 0);
        city = intent.getStringExtra("city");
        username = intent.getStringExtra("username");
        namawisata.setText(tempatwisata);
        imgwisata.setImageResource(KotaActivity.imgwisata1.get(city_position).get(position));
        komentar.setEmptyView(emptyTV);
        if (all_comment_wisata.get(city_position).get(position).size() == 0){
            listDesc = new ListDesc(this, new ArrayList<String>(), new ArrayList<Integer>(), new ArrayList<String>());
            emptyTV.setText("Tidak ada komentar");
        } else {
            listDesc = new ListDesc(this, all_account_wisata.get(city_position).get(position), all_profpict_wisata.get(city_position).get(position), all_comment_wisata.get(city_position).get(position));
        }
        komentar.setAdapter(listDesc);
        UIUtils.setListViewHeightBasedOnItems(komentar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (komen.getText().toString() != ""){
                    all_account_wisata.get(city_position).get(position).add(username);
                    all_profpict_wisata.get(city_position).get(position).add(R.mipmap.potoprofil);
                    all_comment_wisata.get(city_position).get(position).add(komen.getText().toString());
                    Toast.makeText(getApplicationContext(), "Komentar telah di submit", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Lengkapi field", Toast.LENGTH_SHORT).show();
                }
                komen_check = true;
                Intent reOpen = new Intent (WisataActivity.this, WisataActivity.class);
                startActivity(reOpen);
                //finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
                listDesc.notifyDataSetChanged();
                UIUtils.setListViewHeightBasedOnItems(komentar);
            }
        });

    }

    public void onDelete_click(View view) {
        final int position_id = (Integer) view.getTag();
        all_account_wisata.get(city_position).get(position).remove(position_id);
        all_profpict_wisata.get(city_position).get(position).remove(position_id);
        all_comment_wisata.get(city_position).get(position).remove(position_id);
        Toast.makeText(getApplicationContext(), "Komentar telah di hapus", Toast.LENGTH_SHORT).show();
        Intent reOpen = new Intent (WisataActivity.this, WisataActivity.class);
        startActivity(reOpen);
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
        listDesc.notifyDataSetChanged();
        UIUtils.setListViewHeightBasedOnItems(komentar);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WisataActivity.this, KotaActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("city_position", city_position);
        intent.putExtra("city", city);
        intent.putExtra("status", status_check);
        intent.putExtra("username", username);
        startActivity(intent);
        return;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(5.547754,95.315221))
                .zoom(16)
                .bearing(0)
                .tilt(45)
                .build();

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(5.547754,95.315221))
                .title("Musium Tsunami Aceh"));
////                .snippet("Musium Tsunami Aceh: Aceh"));
////                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));


//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

    }
}

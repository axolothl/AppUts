package com.example.setditjenp2mkt.apputs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class KulinerActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView namakuliner, deskripsi_kuliner;
    ImageView imgkuliner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuliner);
        namakuliner = (TextView)findViewById(R.id.namakuliner);
        deskripsi_kuliner = (TextView)findViewById(R.id.kontendeskripsi);
        imgkuliner = (ImageView)findViewById(R.id.icon);
        setTitle("Info Kuliner");
        Intent intent = getIntent();
        String kuliner = intent.getStringExtra("namakuliner");
        int position = intent.getIntExtra("position", 0);
        int city_position = intent.getIntExtra("city_position", 0);
        namakuliner.setText(kuliner);
        imgkuliner.setImageResource(KotaActivity.imgkuliner1.get(city_position).get(position));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(5.547754,95.315221))
                .zoom(16)
                .bearing(0)
                .tilt(45)
                .build();

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(5.547754,95.315221))
                .title("Musium Tsunami Aceh"));
    }
}

package com.example.setditjenp2mkt.apputs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class KulinerActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuliner);
        setTitle("Info Kuliner");
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

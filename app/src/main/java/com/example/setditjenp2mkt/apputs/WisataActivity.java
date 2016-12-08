package com.example.setditjenp2mkt.apputs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class WisataActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView namawisata, deskripsi_wisata;
    ImageView imgwisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata);
        namawisata = (TextView)findViewById(R.id.namawisata);
        deskripsi_wisata = (TextView)findViewById(R.id.kontendeskripsi);
        imgwisata = (ImageView)findViewById(R.id.icon);
        setTitle("Tempat Wisata");
        Intent intent = getIntent();
        String tempatwisata = intent.getStringExtra("namawisata");
        int position = intent.getIntExtra("position", 0);
        int city_position = intent.getIntExtra("city_position", 0);
        namawisata.setText(tempatwisata);
        imgwisata.setImageResource(KotaActivity.imgwisata1.get(city_position).get(position));
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

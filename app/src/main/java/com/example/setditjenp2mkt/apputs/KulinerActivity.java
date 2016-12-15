package com.example.setditjenp2mkt.apputs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class KulinerActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView namakuliner, deskripsi_kuliner, emptyTV, like, delete;
    ImageView imgkuliner;
    ListView komentar;
    EditText nama, komen;
    Button submit;
    ListDesc listDesc;
    public static ArrayList<String> account = new ArrayList<>();
    public static ArrayList<Integer> profpict = new ArrayList<>();
    public static ArrayList<String> comment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuliner);
        namakuliner = (TextView)findViewById(R.id.namakuliner);
        deskripsi_kuliner = (TextView)findViewById(R.id.kontendeskripsi);
        emptyTV = (TextView)findViewById(R.id.emptytext);
        like = (TextView)findViewById(R.id.like);
        delete = (TextView)findViewById(R.id.deletecomment);
        imgkuliner = (ImageView)findViewById(R.id.icon);
        nama = (EditText)findViewById(R.id.editTextNama);
        komen = (EditText)findViewById(R.id.editKomentar);
        submit = (Button)findViewById(R.id.submit);
        komentar = (ListView)findViewById(R.id.komen);
        setTitle("Info Kuliner");
        Intent intent = getIntent();
        String kuliner = intent.getStringExtra("namakuliner");
        int position = intent.getIntExtra("position", 0);
        int city_position = intent.getIntExtra("city_position", 0);
        namakuliner.setText(kuliner);
        imgkuliner.setImageResource(KotaActivity.imgkuliner1.get(city_position).get(position));
        komentar.setEmptyView(emptyTV);
        if (comment.size() == 0){
            listDesc = new ListDesc(this, new ArrayList<String>(), new ArrayList<Integer>(), new ArrayList<String>());
            emptyTV.setText("Tidak ada komentar");
        } else{
            listDesc = new ListDesc(this, account, profpict, comment);
            /*like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (like.getText().toString() == "Suka"){
                        like.setText("Batal Suka");
                        Toast.makeText(getApplicationContext(), "Anda menyukai komentar ini", Toast.LENGTH_SHORT).show();
                    } else if (like.getText().toString() == "Batal Suka"){
                        like.setText("Suka");
                        Toast.makeText(getApplicationContext(), "Anda batal menyukai komentar ini", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
        komentar.setAdapter(listDesc);
        UIUtils.setListViewHeightBasedOnItems(komentar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama.getText().toString() != "" || komen.getText().toString() != ""){
                    account.add(nama.getText().toString());
                    profpict.add(R.mipmap.potoprofil);
                    comment.add(komen.getText().toString());
                    Toast.makeText(getApplicationContext(), "Komentar telah di submit", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Lengkapi field", Toast.LENGTH_SHORT).show();
                }
                Intent reOpen = new Intent (KulinerActivity.this, KulinerActivity.class);
                startActivity(reOpen);
                finish();
                overridePendingTransition( 0, 0);
                startActivity(getIntent());
                overridePendingTransition( 0, 0);
                listDesc.notifyDataSetChanged();
                UIUtils.setListViewHeightBasedOnItems(komentar);
            }
        });
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

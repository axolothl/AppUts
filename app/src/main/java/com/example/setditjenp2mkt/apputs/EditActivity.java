package com.example.setditjenp2mkt.apputs;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    EditText editDeskripsi;
    TextView judul;
    String wisata, kuliner;
    private FloatingActionButton ok;
    private FloatingActionButton cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editDeskripsi = (EditText)findViewById(R.id.editTextDeskripsi);
        judul = (TextView)findViewById(R.id.deskrip);
        Intent intent = getIntent();
        final int kode = intent.getIntExtra("kode", 0);
        if (kode == 1){
            judul.setText("Deskripsi:");
            String deskripsi = intent.getStringExtra("deskripsi");
            editDeskripsi.setText(deskripsi);
        }else if (kode == 2){
            judul.setText("Tambah Tempat Wisata:");
            //editDeskripsi.setText(wisata);
        }else if (kode == 3){
            judul.setText("Kuliner:");
            //editDeskripsi.setText(kuliner);
        }

        ok = (FloatingActionButton)findViewById(R.id.ok);
        cancel = (FloatingActionButton)findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, KotaActivity.class);
                if (kode == 1){
                    Intent inten = getIntent();
                    int position = inten.getIntExtra("city_position", 0);
                    KotaActivity.deskripsi1.set(position, editDeskripsi.getText().toString());
                } else if (kode == 2){
                    Intent inten = getIntent();
                    int position = inten.getIntExtra("city_position", 0);
                    wisata = editDeskripsi.getText().toString();
                    KotaActivity.namawisata1.get(position).add(wisata);
                    KotaActivity.imgwisata1.get(position).add(R.mipmap.noimageicon);
                } else if (kode == 3){
                    Intent inten = getIntent();
                    int position = inten.getIntExtra("city_position", 0);
                    kuliner = editDeskripsi.getText().toString();
                    KotaActivity.kuliner1.get(position).add(kuliner);
                    KotaActivity.imgkuliner1.get(position).add(R.mipmap.noimageicon);
                }
                startActivity(intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, KotaActivity.class);
                startActivity(intent);
            }
        });

    }
}

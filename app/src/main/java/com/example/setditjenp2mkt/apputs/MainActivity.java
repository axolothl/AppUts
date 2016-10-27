package com.example.setditjenp2mkt.apputs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    ImageView header;
    ListView list;
    String[] itemname = {
            "Aceh",
            "Medan",
            "Padang",
            "Bangka Belitung",
            "Banten",
            "Jakarta",
            "Bandung",
            "Semarang",
            "Yogyakarta",
            "Malang",
            "Surabaya"
    };

    Integer[] imgid = {
            R.mipmap.aceh,
            R.mipmap.medan,
            R.mipmap.padang,
            R.mipmap.babel,
            R.mipmap.banten,
            R.mipmap.jakarta,
            R.mipmap.bandung,
            R.mipmap.semarang,
            R.mipmap.jogja,
            R.mipmap.malang,
            R.mipmap.surabaya
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        header = (ImageView)findViewById(R.id.imageView7);

        CustomListAdapter adapter = new CustomListAdapter(this, itemname, imgid);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Selecteditem = itemname[+position];
                Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, KotaActivity.class);
                //intent.putExtra("position", position);
                //intent.putExtra("city", Selecteditem);
                startActivity(intent);
            }
        });
    }
}
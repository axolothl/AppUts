package com.example.setditjenp2mkt.apputs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{
    ImageView header;
    ListView list;
    public Boolean check;
    public static ArrayList<String> name = new ArrayList<>();
    String[] itemname = {
            "Aceh",
            //"Medan",
            //"Padang",
            //"Bangka Belitung",
            //"Banten",
            //"Jakarta",
            //"Bandung",
            //"Semarang",
            "Yogyakarta",
            //"Malang",
            "Surabaya"
    };

    public static ArrayList<Integer> img = new ArrayList<>();
    Integer[] imgid = {
            R.mipmap.aceh,
            //R.mipmap.medan,
            //R.mipmap.padang,
            //R.mipmap.babel,
            //R.mipmap.banten,
            //R.mipmap.jakarta,
            //R.mipmap.bandung,
            //R.mipmap.semarang,
            R.mipmap.jogja,
            //R.mipmap.malang,
            R.mipmap.surabaya
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String kota_baru = intent.getStringExtra("kota_baru");
        check = intent.getBooleanExtra("add", false);
        if (name.size() == 0){
            for (int i = 0;i < itemname.length;i++){
                name.add(itemname[i]);
                img.add(imgid[i]);
                KotaActivity.deskripsi1.add(KotaActivity.deskripsi[i]);
                KotaActivity.imgheaderkota1.add(KotaActivity.imgheaderkota[i]);
            }
            for (int i = 0;i < KotaActivity.wisataaceh.length;i++){
                KotaActivity.wisataaceh1.add(KotaActivity.wisataaceh[i]);
                KotaActivity.kulineraceh1.add(KotaActivity.kulineraceh[i]);
                KotaActivity.imgwisataaceh1.add(KotaActivity.imgwisataaceh[i]);
                KotaActivity.imgkulineraceh1.add(KotaActivity.imgkulineraceh[i]);
            }
            for (int i = 0;i < KotaActivity.wisatajogja.length;i++){
                KotaActivity.wisatajogja1.add(KotaActivity.wisatajogja[i]);
                KotaActivity.kulinerjogja1.add(KotaActivity.kulinerjogja[i]);
                KotaActivity.imgwisatajogja1.add(KotaActivity.imgwisatajogja[i]);
                KotaActivity.imgkulinerjogja1.add(KotaActivity.imgkulinerjogja[i]);
            }
            for (int i = 0;i < KotaActivity.wisatasurabaya.length;i++){
                KotaActivity.wisatasurabaya1.add(KotaActivity.wisatasurabaya[i]);
                KotaActivity.kulinersurabaya1.add(KotaActivity.kulinersurabaya[i]);
                KotaActivity.imgwisatasurabaya1.add(KotaActivity.imgwisatasurabaya[i]);
                KotaActivity.imgkulinersurabaya1.add(KotaActivity.imgkulinersurabaya[i]);
            }
            KotaActivity.namawisata1.add(KotaActivity.wisataaceh1);
            KotaActivity.namawisata1.add(KotaActivity.wisatajogja1);
            KotaActivity.namawisata1.add(KotaActivity.wisatasurabaya1);

            KotaActivity.imgwisata1.add(KotaActivity.imgwisataaceh1);
            KotaActivity.imgwisata1.add(KotaActivity.imgwisatajogja1);
            KotaActivity.imgwisata1.add(KotaActivity.imgwisatasurabaya1);

            KotaActivity.kuliner1.add(KotaActivity.kulineraceh1);
            KotaActivity.kuliner1.add(KotaActivity.kulinerjogja1);
            KotaActivity.kuliner1.add(KotaActivity.kulinersurabaya1);

            KotaActivity.imgkuliner1.add(KotaActivity.imgkulineraceh1);
            KotaActivity.imgkuliner1.add(KotaActivity.imgkulinerjogja1);
            KotaActivity.imgkuliner1.add(KotaActivity.imgkulinersurabaya1);
        }
        if(check){
            name.add(kota_baru);
            img.add(R.mipmap.noimageicon);
            KotaActivity.imgheaderkota1.add(R.mipmap.noimage);
            ArrayList<String> wisatabaru = new ArrayList<>();
            wisatabaru.add("kosong");
            ArrayList<String> kulinerbaru = new ArrayList<>();
            kulinerbaru.add("kosong");
            ArrayList<Integer> imgwisatabaru = new ArrayList<>();
            imgwisatabaru.add(R.mipmap.noimageicon);
            ArrayList<Integer> imgkulinerbaru = new ArrayList<>();
            imgkulinerbaru.add(R.mipmap.noimageicon);
            KotaActivity.namawisata1.add(wisatabaru);
            KotaActivity.kuliner1.add(kulinerbaru);
            KotaActivity.imgwisata1.add(imgwisatabaru);
            KotaActivity.imgkuliner1.add(imgkulinerbaru);
            KotaActivity.deskripsi1.add("Tidak ada deskripsi");
        }
        header = (ImageView)findViewById(R.id.imageView7);
        for (int i = 0; i < KotaActivity.namawisata1.size(); i++) {
            Log.d("namawisata1:", KotaActivity.namawisata1.get(i).toString());
        }
        for (int i = 0; i < name.size(); i++) {
            Log.d("name:", name.get(i).toString());
        }

        CustomListAdapter adapter = new CustomListAdapter(this, name, img);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Selecteditem = name.get(position);
                Toast.makeText(getApplicationContext(), Selecteditem, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, KotaActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("city", Selecteditem);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
//test push from android studio
package com.example.setditjenp2mkt.apputs;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.setditjenp2mkt.apputs.MainActivity.img;

public class KotaActivity extends AppCompatActivity {
    CustomListAdapter customListAdapter;
    ListView wisata, makanan;
    TextView tvdesc, tvname, empty_wisata, empty_kuliner;
    ImageView headerkota;
    public int city_position, position;
    public String city;
    public static String[] wisataaceh = {
            "Masjid Raya Baiturrahman",
            "Air Terjun Blang Kolam",
            "Pantai Lampuuk",
            "Museum Tsunami"
    };
    public static String[] wisatajogja = {
            "Candi Prambanan",
            "Pantai Parangtritis",
            "Malioboro"
    };
    public static String[] wisatasurabaya = {
            "Tugu Pahlawan",
            "Monumen Kapal Selam",
            "Jembatan Suramadu"
    };
    public static String[][] namawisata = {
            wisataaceh,
            wisatajogja,
            wisatasurabaya
    };
    public static ArrayList<ArrayList<String>> namawisata1 = new ArrayList<ArrayList<String>>();
    public static ArrayList<String> wisataaceh1 = new ArrayList<>();
    public static ArrayList<String> wisatajogja1 = new ArrayList<>();
    public static ArrayList<String> wisatasurabaya1 = new ArrayList<>();

    public static String[] kulineraceh = {
            "Ayam Tangkap",
            "Mie Aceh",
            "Kuah Beulangong",
            "Timphan"
    };
    public static String[] kulinerjogja = {
            "Gudeg",
            "Bakpia",
            "Yangko"
    };
    public static String[] kulinersurabaya = {
            "Sate Klopo",
            "Soto Lamongan",
            "Nasi Krawu"
    };
    public static String[][] kuliner = {
            kulineraceh,
            kulinerjogja,
            kulinersurabaya
    };
    public static ArrayList<ArrayList<String>> kuliner1=new ArrayList<ArrayList<String>>();
    public static ArrayList<String> kulineraceh1 = new ArrayList<>();
    public static ArrayList<String> kulinerjogja1 = new ArrayList<>();
    public static ArrayList<String> kulinersurabaya1 = new ArrayList<>();

    public static Integer[] imgwisataaceh = {
            R.mipmap.masjidraya,
            R.mipmap.blangkolam,
            R.mipmap.pantailampuuk,
            R.mipmap.museumtsunami
    };
    public static Integer[] imgwisatajogja = {
            R.mipmap.prambanan,
            R.mipmap.parangtritis,
            R.mipmap.malioboro
    };
    public static Integer[] imgwisatasurabaya = {
            R.mipmap.tugupahlawan,
            R.mipmap.monkasel,
            R.mipmap.suramadu
    };
    public static Integer[][] imgwisata = {
            imgwisataaceh,
            imgwisatajogja,
            imgwisatasurabaya
    };

    public static Integer[] imgheaderkota = {
            R.mipmap.masjidraya,
            R.mipmap.prambanan,
            R.mipmap.suramadu
    };
    public static ArrayList<ArrayList<Integer>> imgwisata1=new ArrayList<ArrayList<Integer>>();
    public static ArrayList<Integer> imgwisataaceh1 = new ArrayList<>();
    public static ArrayList<Integer> imgwisatajogja1 = new ArrayList<>();
    public static ArrayList<Integer> imgwisatasurabaya1 = new ArrayList<>();
    public static ArrayList<Integer> imgheaderkota1 = new ArrayList<>();

    public static Integer[] imgkulineraceh = {
            R.mipmap.ayamtangkap,
            R.mipmap.miaceh,
            R.mipmap.beulangong,
            R.mipmap.timphan
    };
    public static Integer[] imgkulinerjogja = {
            R.mipmap.gudeg,
            R.mipmap.bakpia,
            R.mipmap.yangko
    };
    public static Integer[] imgkulinersurabaya = {
            R.mipmap.sateklopo,
            R.mipmap.sotolamongan,
            R.mipmap.nasikrawu
    };
    public static Integer[][] imgkuliner = {
            imgkulineraceh,
            imgkulinerjogja,
            imgkulinersurabaya
    };
    public static ArrayList<ArrayList<Integer>> imgkuliner1=new ArrayList<ArrayList<Integer>>();
    public static ArrayList<Integer> imgkulineraceh1 = new ArrayList<>();
    public static ArrayList<Integer> imgkulinerjogja1 = new ArrayList<>();
    public static ArrayList<Integer> imgkulinersurabaya1 = new ArrayList<>();

    public static String[] deskripsi = {
            "Aceh adalah sebuah provinsi di Indonesia. Aceh terletak di ujung utara pulau Sumatera dan merupakan provinsi paling barat di Indonesia. Ibu kotanya adalah Banda Aceh. Jumlah penduduk provinsi ini sekitar 4.500.000 jiwa. Letaknya dekat dengan Kepulauan Andaman dan Nikobar di India dan terpisahkan oleh Laut Andaman. Aceh berbatasan dengan Teluk Benggala di sebelah utara, Samudra Hindia di sebelah barat, Selat Malaka di sebelah timur, dan Sumatera Utara di sebelah tenggara dan selatan.",
            "Daerah Istimewa Yogyakarta (bahasa Jawa: Dhaérah Istiméwa Ngayogyakarta) adalah Daerah Istimewa setingkat provinsi di Indonesia yang merupakan peleburan Negara Kesultanan Yogyakarta dan Negara Kadipaten Paku Alaman. Daerah Istimewa Yogyakarta terletak di bagian selatan Pulau Jawa, dan berbatasan dengan Provinsi Jawa Tengah dan Samudera Hindia. Penyebutan nomenklatur Daerah Istimewa Yogyakarta yang terlalu panjang menimbulkan penyingkatan nomenklatur menjadi DI Yogyakarta atau DIY.",
            "Kota Surabaya adalah ibu kota Provinsi Jawa Timur, Indonesia sekaligus menjadi kota metropolitan terbesar di provinsi tersebut. Surabaya merupakan kota terbesar kedua di Indonesia setelah Jakarta. Kota Surabaya juga merupakan pusat bisnis, perdagangan, industri, dan pendidikan di Jawa Timur serta wilayah Indonesia bagian timur. Kota ini terletak 796 km sebelah timur Jakarta, atau 415 km sebelah barat laut Denpasar, Bali. Surabaya terletak di tepi pantai utara Pulau Jawa dan berhadapan dengan Selat Madura serta Laut Jawa."
    };
    public static ArrayList<String> deskripsi1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kota);
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        setTitle("Tentang " + city);
        city_position = intent.getIntExtra("city_position", 0);
        position = intent.getIntExtra("position", 0);

        tvname = (TextView)findViewById(R.id.namakota);
        tvdesc = (TextView)findViewById(R.id.kontendeskripsi);
        empty_wisata = (TextView)findViewById(R.id.emptytext);
        empty_kuliner = (TextView)findViewById(R.id.emptytext1);
        headerkota = (ImageView)findViewById(R.id.icon);

        tvname.setText(city);
        tvdesc.setText(deskripsi1.get(city_position));
        headerkota.setImageResource(imgheaderkota1.get(city_position));

        for (int i = 0; i < namawisata1.get(city_position).size(); i++){
            WisataActivity.all_account_wisata.get(city_position).add(new ArrayList<String>());
            WisataActivity.all_profpict_wisata.get(city_position).add(new ArrayList<Integer>());
            WisataActivity.all_comment_wisata.get(city_position).add(new ArrayList<String>());
        }

        wisata = (ListView)findViewById(R.id.wisata_aceh);
        wisata.setEmptyView(empty_wisata);
        if (namawisata1.get(city_position).size() == 0){
            customListAdapter = new CustomListAdapter(this, new ArrayList<String>(), new ArrayList<Integer>());
            empty_wisata.setText("Tidak ada tempat wisata");
        } else {
            customListAdapter = new CustomListAdapter(this, namawisata1.get(city_position), imgwisata1.get(city_position));
        }
        wisata.setAdapter(customListAdapter);

        wisata.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(KotaActivity.this, WisataActivity.class);
                String selecteditem = namawisata1.get(city_position).get(position);
                intent.putExtra("namawisata", selecteditem);
                intent.putExtra("position", position);
                intent.putExtra("city_position", city_position);
                intent.putExtra("city", city);
                Toast.makeText(getApplicationContext(), selecteditem, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        makanan = (ListView)findViewById(R.id.kuliner_aceh);
        makanan.setEmptyView(empty_kuliner);
        if (kuliner1.get(city_position).size() == 0){
            customListAdapter = new CustomListAdapter(this, new ArrayList<String>(), new ArrayList<Integer>());
            empty_kuliner.setText("Tidak ada kuliner");
        } else {
            customListAdapter = new CustomListAdapter(this, kuliner1.get(city_position), imgkuliner1.get(city_position));
        }
        makanan.setAdapter(customListAdapter);

        makanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(KotaActivity.this, KulinerActivity.class);
                String selecteditem = kuliner1.get(city_position).get(position);
                intent.putExtra("namakuliner", selecteditem);
                intent.putExtra("position", position);
                intent.putExtra("city_position", city_position);
                Toast.makeText(getApplicationContext(), selecteditem, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KotaActivity.this, MainActivity.class);
        startActivity(intent);
        return;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editkota, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_deskripsi:
                Intent intent = new Intent(KotaActivity.this, EditActivity.class);
                Intent intent5 = getIntent();
                int city_position2 = intent5.getIntExtra("position", 0);
                String deskripsi = tvdesc.getText().toString();
                intent.putExtra("deskripsi", deskripsi);
                intent.putExtra("kode", 1);
                intent.putExtra("position", city_position2);
                startActivity(intent);
                return true;
            case R.id.edit_wisata:
                Intent intent1 = new Intent(KotaActivity.this, EditActivity.class);
                Intent intent2 = getIntent();
                int city_position = intent2.getIntExtra("position", 0);
                String wisata = namawisata1.get(city_position).toString();
                intent1.putExtra("wisata", wisata);
                intent1.putExtra("kode", 2);
                intent1.putExtra("position", city_position);
                startActivity(intent1);
                return true;
            case R.id.edit_kuliner:
                Intent intent3 = new Intent(KotaActivity.this, EditActivity.class);
                Intent intent4 = getIntent();
                int city_position1 = intent4.getIntExtra("position", 0);
                String kuliner = kuliner1.get(city_position1).toString();
                intent3.putExtra("kuliner", kuliner);
                intent3.putExtra("kode", 3);
                intent3.putExtra("position", city_position1);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

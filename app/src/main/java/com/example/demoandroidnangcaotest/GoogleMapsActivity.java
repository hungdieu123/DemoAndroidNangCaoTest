package com.example.demoandroidnangcaotest;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SearchView searchView;
    private ToaDo_db list;
    private List<ToaDo_db> listlist;
     private EditText edttitle,edtkinhdo,edtvido;
     ToaDoDAO toaDoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        edtkinhdo=findViewById(R.id.kinhdo);
        edtvido=findViewById(R.id.vido);
        edttitle=findViewById(R.id.titlee);

        toaDoDAO = new ToaDoDAO(GoogleMapsActivity.this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //code chạy
        searchView=findViewById(R.id.search_location);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                String location = searchView.getQuery().toString();
                List<Address> addresses=null;
                try{
                    if(location!=null || location.equals("")){
                        Geocoder geocoder=new Geocoder(GoogleMapsActivity.this);
                        try{
                            addresses=geocoder.getFromLocationName(location,1);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Address address=addresses.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
                    }
                }catch (Exception e){
                    Toast.makeText(GoogleMapsActivity.this,"Địa chỉ cần chính sác",Toast.LENGTH_SHORT).show();
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//         Add a marker in Sydney and move the camera
        Intent intent = this.getIntent();

        double kd= intent.getDoubleExtra("kd",-1);
        double vido = intent.getDoubleExtra("vido",-1);


        if (kd >0&&vido>0){
            LatLng sydney = new LatLng(kd, vido);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Bạn Vừa Chọn Điểm Này !!!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }else {
            LatLng sydney = new LatLng(12   , 12);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }


        listlist = toaDoDAO.getAll();
        for (int i = 0; i < listlist.size(); i++) {
            ToaDo_db toaDo_db=listlist.get(i);
            double x=listlist.get(i).getKinhdo();
            double y=listlist.get(i).getVido();
            String c=listlist.get(i).getTitle();
            final   LatLng vt = new LatLng(x,y);
            mMap.addMarker(new MarkerOptions().position(vt).title(c));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vt, 1));



        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) { ;
                edtkinhdo.setText(String.valueOf(marker.getPosition().latitude));
                edtvido.setText(String.valueOf(marker.getPosition().longitude));
                edttitle.setText(String.valueOf(marker.getTitle()));
                edttitle.setEnabled(false);
                return false;
            }
        });
    }

    public void them(View view) {

        final String title = edttitle.getText().toString().trim();
        try {
            String kd = edtkinhdo.getText().toString().trim();
            String vd = edtvido.getText().toString().trim();

            toaDoDAO = new ToaDoDAO(GoogleMapsActivity.this);
            if (edtkinhdo.getText().length() == 0||edtvido.getText().length() == 0||edttitle.getText().length() == 0) {


                Toast.makeText(GoogleMapsActivity.this, "mời nhập thông tin", Toast.LENGTH_SHORT).show();
            } else {

                 list = new ToaDo_db();
                list.setKinhdo(Double.parseDouble(kd));
                list.setVido(Double.parseDouble(vd));
                list.setTitle(title);
                long result = toaDoDAO.insertma(list);
                if (result > 0) {
                    Toast.makeText(GoogleMapsActivity.this, "thêm thành công", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(GoogleMapsActivity.this, "thêm thất bại", Toast.LENGTH_SHORT).show();
                }

                clear();
                ((GoogleMapsActivity.this)).recreate();
            }

        }catch (NumberFormatException x){
            Toast.makeText(GoogleMapsActivity.this, "không nhập chữ"+x, Toast.LENGTH_SHORT).show();
        }
    }

    public void sua(View view) {
        final String title = edttitle.getText().toString().trim();
        try {
            String kd = edtkinhdo.getText().toString().trim();
            String vd = edtvido.getText().toString().trim();

            toaDoDAO = new ToaDoDAO(GoogleMapsActivity.this);
            if (edtkinhdo.getText().length() == 0||edtvido.getText().length() == 0||edttitle.getText().length() == 0) {


                Toast.makeText(GoogleMapsActivity.this, "mời nhập thông tin", Toast.LENGTH_SHORT).show();
            } else {

                ToaDo_db toaDo = new ToaDo_db();
                toaDo.setKinhdo(Double.parseDouble(kd));
                toaDo.setVido(Double.parseDouble(vd));
                toaDo.setTitle(title);
                long result = toaDoDAO.updatema(toaDo);
                if (result > 0) {
                    Toast.makeText(GoogleMapsActivity.this, "thêm thành công", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(GoogleMapsActivity.this, "thêm thất bại", Toast.LENGTH_SHORT).show();
                }

                clear();
                ((GoogleMapsActivity.this)).recreate();
            }

        }catch (NumberFormatException x){
            Toast.makeText(GoogleMapsActivity.this, "không nhập chữ"+x, Toast.LENGTH_SHORT).show();
        }
    }

    public void xoa(View view) {

        String kd = edtkinhdo.getText().toString().trim();
        String vd = edtvido.getText().toString().trim();
        final String title = edttitle.getText().toString().trim();
        toaDoDAO = new ToaDoDAO(GoogleMapsActivity.this);
        edttitle.setEnabled(false);
        if (edtkinhdo.getText().length() == 0||edtvido.getText().length() == 0||edttitle.getText().length() == 0) {
            Toast.makeText(GoogleMapsActivity.this, "mời nhập thông tin", Toast.LENGTH_SHORT).show();
        } else {
            ToaDo_db toaDo_db = new ToaDo_db();
            toaDo_db.setKinhdo(Double.parseDouble(kd));
            toaDo_db.setVido(Double.parseDouble(vd));
            toaDo_db.setTitle(title);
            long result = toaDoDAO.deletema(toaDo_db);
            if (result > 0) {
                Toast.makeText(GoogleMapsActivity.this, "xóa thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GoogleMapsActivity.this, "xóa thất bại", Toast.LENGTH_SHORT).show();
            }
            clear();
            ((GoogleMapsActivity.this)).recreate();
        }
    }
    public void clear(){
        edtkinhdo.setText("");
        edtvido.setText("");
        edttitle.setText("");
    }
}

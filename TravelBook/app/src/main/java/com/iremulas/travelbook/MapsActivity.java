package com.iremulas.travelbook;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                //uygulamada her hareket ettiğimizde yerimizi güncellemke yerine ilk açtığımızdaki konumuzu kullanmasını sağlar
                SharedPreferences sharedPreferences = MapsActivity.this.getSharedPreferences("com.iremulas.travelbook",MODE_PRIVATE);
                boolean firstTimeChechk = sharedPreferences.getBoolean("notFirstTime",false);
                if (!firstTimeChechk){
                    LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));
                    sharedPreferences.edit().putBoolean("notFirstTime",true).apply();
                }

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            //eğer izin verilmiş ise görülen son lokasyonu kullanır
            mMap.clear();
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //izin alındığında son konum henüz yok ise
            if (lastLocation != null){
                LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));
            }

        }
        //click listener ı tanımladık
        mMap.setOnMapLongClickListener(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 ){
            if(requestCode == 1){
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                    Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    //izin alındığında son konum henüz yok ise
                    if (lastLocation != null){
                        LatLng lastUserLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15));
                    }
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String address="";


        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if (addressList != null && addressList.size() > 0){
                if (addressList.get(0).getThoroughfare() != null){
                    address += addressList.get(0).getThoroughfare();
                    if (addressList.get(0).getSubThoroughfare() != null){
                        address += addressList.get(0).getSubThoroughfare();
                    }
                }
            }
            else {  // addressList boş gelir ise
                address = "New Place";
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        mMap.addMarker(new MarkerOptions().title(address).position(latLng));    //marker ın göstereciği adresi ve konumunu ayarladık
        Toast.makeText(getApplicationContext(),"new Place Okey",Toast.LENGTH_SHORT).show();

        //SQLite işlemleri
        try {
            //enlem ve boylamımı double olarak aldım
            Double l1 = latLng.latitude;
            Double l2 = latLng.longitude;

            //konumları alırken string olarak almamız gerek
            String coard1 = l1.toString();
            String coard2 = l2.toString();

            database = this.openOrCreateDatabase("Places",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS places (name VARCHAR, latitude VARCHAR, longtitude VARCHAR ) ");

            String toCompile = "INSERT INTO places(name,latitude,longtitude) VALUES (?,?,?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(toCompile);

            //bağlama
            sqLiteStatement.bindString(1,address);
            sqLiteStatement.bindString(2,coard1);
            sqLiteStatement.bindString(1,coard2);

            sqLiteStatement.execute();
        }
        catch (Exception e){

        }
    }
}
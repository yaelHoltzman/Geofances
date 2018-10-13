package com.eatwell.yael.geofances.UI;


import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;
import com.eatwell.yael.geofances.Utils.GeofenceManager;
import com.eatwell.yael.geofances.Utils.Permission_Checker;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class LocationSelector extends AppCompatActivity {

    private static final String TAG = LocationSelector.class.getSimpleName();

    //private String locationType;
    private Button setLocationButton;
    private String locationType; //TODO change to enum

    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private Marker locationMarker;
    private static User user = User.getInstance();
    private Permission_Checker permissionChecker;
    private GeofenceManager geofenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);

        //user = User.getInstance();
        setLocationButton = (Button) findViewById(R.id.setLocationButton);
        TextView textView = (TextView) findViewById(R.id.skipText);
        permissionChecker = new Permission_Checker();
        geofenceManager = new GeofenceManager();
        locationType = "Home";

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);

                    if (user.isHomeLocationSet()) {
                        map.clear();
                        LatLng homeLatLng = user.getLocation(locationType);

                        if (homeLatLng == null) {
                            return;
                        }

                        locationMarker = map.addMarker(new MarkerOptions().position(homeLatLng).title(locationType));
                        map.moveCamera(CameraUpdateFactory.newLatLng(homeLatLng));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, 12.0f));
                    }

                }
            });
        }

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                map.clear();
                locationMarker = map.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                map.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
            }

            @Override
            public void onError(Status status) {

                Log.e(TAG, status.toString());
                Toast.makeText(LocationSelector.this, "Map error", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    protected void loadMap(GoogleMap googleMap) {

        if ( !permissionChecker.checkPermission(this) ) {
            permissionChecker.askPermission(this);
        }

        map = googleMap;
        map.setMyLocationEnabled(true);
    }


    public void onClick(View view) {

        user.removeLocation(locationType);

        geofenceManager.startGeofence(locationMarker, user.getmContext());
        user.addLocation(locationType, locationMarker.getPosition());
        user.setIsHomeLocationSet(true);

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void onClickSkip(View view) {

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
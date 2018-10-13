package com.eatwell.yael.geofances.UI;


import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class LocationSelector extends AppCompatActivity {

    private static final String TAG = LocationSelector.class.getSimpleName();

    //private String locationType;
    private Button setLocationButton;
    //TODO- add locationType option
    private String locationType;

    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private Marker locationMarker;
    private Location lastLocation;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mRequestingLocationUpdates;
    private static User user = User.getInstance();

    private Permission_Checker permissionChecker;
    private GeofenceManager geofenceManager;

    // TODO currently defined in mili seconds.
    // This number in extremely low, and should be used only for debug
    private final int UPDATE_INTERVAL =  1000 * 60;
    private final int FASTEST_INTERVAL = 900 * 60;

    // TODO check whether location settings are satisfied


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);

        //user = User.getInstance();
        setLocationButton = (Button) findViewById(R.id.setLocationButton);
        permissionChecker = new Permission_Checker();
        geofenceManager = new GeofenceManager();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
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

                //TODO- should be in OnClick
                geofenceManager.startGeofence(locationMarker, user.getmContext());
            }

            @Override
            public void onError(Status status) {

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

        geofenceManager.startGeofence(locationMarker, user.getmContext());

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
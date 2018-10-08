package com.eatwell.yael.geofances.Utils;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.eatwell.yael.geofances.Firebase_Utils.GeofenceManager;
import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UI.Notification;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class LocationSelector extends AppCompatActivity {

    private static final String TAG = LocationSelector.class.getSimpleName();

    //private String locationType;
    private Button setLocationButton;

    private GoogleMap map;
    private SupportMapFragment mapFragment;

    private Marker locationMarker;

    private Location lastLocation;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;

    private Permission_Checker permissionChecker;
    private GeofenceManager geofenceManager;

    private FusedLocationProviderClient mFusedLocationClient;

    //TODO
    // Defined in mili seconds.
    // This number in extremely low, and should be used only for debug
    private final int UPDATE_INTERVAL =  1000 * 60;
    private final int FASTEST_INTERVAL = 900 * 60;

    private boolean mRequestingLocationUpdates;

    // TODO check whether location settings are satisfied


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);

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
                geofenceManager.startGeofence(locationMarker, getApplicationContext());
            }

            @Override
            public void onError(Status status) {

            }
        });
    }

    protected void loadMap(GoogleMap googleMap) {
            map = googleMap;

            if ( !permissionChecker.checkPermission(this) ) {
                permissionChecker.askPermission(this);
            }
            map.setMyLocationEnabled(true);
        }


    public void onClick(View view) {

        geofenceManager.startGeofence(locationMarker, getApplicationContext());

        Intent intent = new Intent(this, Notification.class);
        startActivity(intent);
    }
}
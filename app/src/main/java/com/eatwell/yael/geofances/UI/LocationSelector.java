package com.eatwell.yael.geofances.UI;


import android.content.Intent;
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
import com.eatwell.yael.geofances.Utils.PermissionsChecker;
import com.google.android.gms.common.api.Status;
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

import java.util.Objects;

public class LocationSelector extends AppCompatActivity {

    private static final String TAG = LocationSelector.class.getSimpleName();
    private String mlocationType; //TODO change to enum

    private GoogleMap map;
    private Marker locationMarker;
    private User user;
    private GeofenceManager geofenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);

        user = User.getInstance();
        user.setmContext(getApplicationContext());

        Button setLocationButton;
        setLocationButton = /*(Button)*/findViewById(R.id.setLocationButton);
        setLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLocation();
                goToNextScreen();
            }
        });
        TextView textView = /*(TextView)*/findViewById(R.id.skipText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextScreen();
            }
        });



        geofenceManager = new GeofenceManager(this, mlocationType);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);

                    if (user.isLocationSet(mlocationType)) {

                        LatLng locationLatLng = user.getLocation(mlocationType);
                        if (locationLatLng == null) {
                            return;
                        }

                        map.clear();
                        locationMarker = map.addMarker(new MarkerOptions().position(locationLatLng).title(mlocationType));
                        map.moveCamera(CameraUpdateFactory.newLatLng(locationLatLng));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 12.0f));
                    }
                }
            });
        }

        mlocationType = Objects.requireNonNull(getIntent().getExtras()).getString("locationType");

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

        if (!PermissionsChecker.checkLocationPermission(this)) {
            PermissionsChecker.askLocationPermission(this);
        }

        map = googleMap;
        map.setMyLocationEnabled(true);
    }


    private void handleLocation() {

        if (user.isLocationSet(mlocationType)) {
            //user.removeLocation(mlocationType);
            geofenceManager.clearGeofence(mlocationType);
        }

        geofenceManager.startGeofence(locationMarker, user.getmContext());
        user.addLocation(mlocationType, locationMarker.getPosition());
        user.setIsLocationSet(mlocationType, true);

    }

    public void onClickSkip(View view) {

        goToNextScreen();
    }

    private void goToNextScreen() {

        Intent intent;
        /*if (user.isLocationSet("Work")) {
            intent = new Intent(this, Home.class);
        }
        else {
            intent = new Intent(this, LocationSelector.class);
            intent.putExtra("locationType", "Work");
        }
*/

        intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
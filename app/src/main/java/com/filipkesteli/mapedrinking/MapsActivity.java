package com.filipkesteli.mapedrinking;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        addHeatMap();
    }



    /**
     * Add a simple heat map to the map
     */
    private void addHeatMap() {
        List<LatLng> list = null;

        // Get the data: latitude/longitude positions of police stations.
        try {
            list = readItems(R.raw.police_stations);
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder().data(list).build();
        // Add a tile overlay to the map, using the heat map tile provider.
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
    }
    /**
     * Read the data (locations of police stations) from raw resources.
     */
    private ArrayList<LatLng> readItems(int resource) throws JSONException {
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        @SuppressWarnings("resource")
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            list.add(new LatLng(lat, lng));
        }

        String s = list.toString();

        Toast.makeText(MapsActivity.this, s, Toast.LENGTH_SHORT).show();
        return list;
    }



/*

    private void addHeatMap() {
        List<LatLng> list = null; //definiranje u memoriji

        List<Double> lat = new ArrayList<>();
        List<Double> lng = new ArrayList<>();

        lat.add(-37.1886);
        lat.add(-37.8361);
        lat.add(-38.4034);
        lat.add(-38.7597);
        lat.add(-36.9672);

        lng.add(145.708);
        lng.add(144.845);
        lng.add(144.192);
        lng.add(143.67);
        lng.add(141.083);

        //Get the data: latitude/longitude positions of police stations:

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        mProvider = new HeatmapTileProvider.Builder()
                .data(list)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }
    }
*/

    /*
    private void addHeatMap() {
        List<LatLng> list = null;

        // Get the data: latitude/longitude positions of police stations.
        try {
            list = readItems(R.raw.police_stations);
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        mProvider = new HeatmapTileProvider.Builder()
                .data(list)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

        // Create the gradient.
        int[] colors = {
                Color.rgb(102, 225, 0), // green
                Color.rgb(255, 0, 0)    // red
        };

        float[] startPoints = {
                0.2f, 1f
        };

        Gradient gradient = new Gradient(colors, startPoints);

// Create the tile provider.
        mProvider = new HeatmapTileProvider.Builder()
                .data(mList)
                .gradient(gradient)
                .build();

// Add the tile overlay to the map.
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }
    */

}

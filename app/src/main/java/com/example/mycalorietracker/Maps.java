package com.example.mycalorietracker;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Maps extends Fragment implements OnMapReadyCallback {
    private static final String PROXIMITY_RADIUS = "5000"; //5km = 5000m as google api take value in metre
    private GoogleMap myMap;
    View vmyMaps;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vmyMaps = inflater.inflate(R.layout.maps, container, false);
        MapFragment maps = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        maps.getMapAsync(this);
        return vmyMaps;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        SharedPreferences loggedinuser = getActivity().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
        String uaddress = loggedinuser.getString("address",null);
        String upostcode = loggedinuser.getString("postcode",null);
        Geocoder locCoder = new Geocoder(this.getActivity());
        List<Address>address;
        LatLng userLatLng = null;
        myMap = googleMap;
        try {
            address = locCoder.getFromLocationName(uaddress+","+upostcode,5);
            if (address==null){
                Toast.makeText(vmyMaps.getContext(),"Address not found",Toast.LENGTH_SHORT).show();
                return;
            }
            Address userAddress = address.get(0); // the first matching address
            userLatLng = new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

        } catch (IOException e) {
            Toast.makeText(vmyMaps.getContext(),"Address not found",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        googleMap.addMarker(new MarkerOptions().position(userLatLng).title(uaddress+","+upostcode));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,12.0f));
        SearchPlaces searchPlaces = new SearchPlaces();
        searchPlaces.execute(String.valueOf(userLatLng.latitude),String.valueOf(userLatLng.longitude),"5000","park");


    }
    private class SearchPlaces extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return PlacesGoogle.getPlaces(params[0],params[1],params[2],params[3]);//params[0]-latitude,params[1]-longitude,params[2]-PROXIMITY_RADIUS,params[3]-nearbyPlace
        }

        @Override
        protected void onPostExecute(String result) {
            final Park[] nearByParks=PlacesGoogle.getParks(result);
            for(int j =0;j<nearByParks.length;j++){

                myMap.addMarker(new MarkerOptions().position(new LatLng(nearByParks[j].getLatitude(),nearByParks[j].getLongitude())).title(nearByParks[j].getNamePark()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTag(j);

            }
            myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Object tagObject = marker.getTag(); //because red markup is not given any tag and if clicked, tag is null and it crashes
                    if(tagObject!=null) {
                        int tag = (int) tagObject;
                        new AlertDialog.Builder(getActivity()).setTitle("Park details").setMessage(nearByParks[tag].getNamePark()).setPositiveButton("Ok", null).show();
                    }
                    return false;
                }
            });
        }
    }
}



package com.example.varsha.weatherforecast;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.tiles.AerisTile;

public class MapFragment extends MapViewFragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Marker marker;
    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // setting up secret key and client id for oauth to aeris
        //AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), "edu.usc.weatherapp");
    }

    /**
     * Inits the map with specific setting
     */
    private void initMap(Double latitude, Double longitude) {
        try {
            //Double latitude = 34.5;
            //Double longitude = -118.5;
            mapView.moveToLocation(new LatLng(latitude, longitude), 9);
            mapView.addLayer(AerisTile.RADSAT);
        } catch (Exception e)
        {
            e.getMessage();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        Double latitude = args.getDouble("latitude", 34);
        Double longitude = args.getDouble("longitude", -110);
       // Possible revert
       // View view = inflater.inflate(R.layout.fragment_interactive_maps, container, false);
        View view = inflater.inflate(R.layout.content_map_fragment, container, false);
        mapView = (AerisMapView) view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapView.AerisMapType.GOOGLE);
        //mapView.setsOnAerisMapLongClickListener(this);
        initMap(latitude,longitude);
        //return inflater.inflate(R.layout.fragment_interactive_maps, container, false);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}

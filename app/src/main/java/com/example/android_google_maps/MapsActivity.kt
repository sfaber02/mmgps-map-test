package com.example.android_google_maps

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.Polygon
import com.example.android_google_maps.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var polygon: Polygon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val polygonPoints = mutableListOf(LatLng(-34.0, 151.0))
        polygon = mMap.addPolygon(createPolygonOptions(polygonPoints))

        // Listen to map clicks to add points to polygonOptions
        mMap.setOnMapClickListener { latLng ->
            // if the polygonPoints only has the initial point, replace it
            if (polygonPoints.size == 1 && polygonPoints.first() == LatLng(0.0, 0.0)) {
                polygonPoints[0] = latLng
            } else {
                // otherwise, add the new point
                polygonPoints.add(latLng)
            }

            // then recreate the polygon with the new points
            polygon?.remove()
            polygon = mMap.addPolygon(createPolygonOptions(polygonPoints))
        }

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun createPolygonOptions(polygonPoints: MutableList<LatLng>): PolygonOptions {
        return PolygonOptions()
            .addAll(polygonPoints)
            .strokeColor(Color.RED)
            .fillColor(Color.BLUE)
    }
}

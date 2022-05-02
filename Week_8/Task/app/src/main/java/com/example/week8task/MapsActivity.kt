package com.example.week8task

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.week8task.databinding.ActivityMapsBinding
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.Marker
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener




class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false
    private val myKey = "ORGANIZER"
    private val noelKey = "NOEL"
    private val segunKey = "SEGUN"

    private var myMarker: Marker? = null
    private var noelMarker: Marker? = null
    private var segunMarker: Marker? = null

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = database.getReference(myKey)
    private val noelRef: DatabaseReference = database.getReference(noelKey)
    private val segunRef: DatabaseReference = database.getReference(segunKey)

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val PLACE_PICKER_REQUEST = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = p0.lastLocation
                myRef.setValue(CustomLatLng(lastLocation.latitude.toString(), lastLocation.longitude.toString()))
                if (myMarker == null) {
                    placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
                } else {
                    myMarker?.position = LatLng(lastLocation.latitude, lastLocation.longitude)
                }
            }
        }

        binding.fab.setOnClickListener {
            placePartnersOnMap()
//            loadPlacePicker()
        }

        binding.fab2.setOnClickListener {
            findSegun()
//            loadPlacePicker()
        }

        createLocationRequest()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                val place = PlacePicker.getPlace(this, data)
                var addressText = place.name.toString()
                addressText += "\n" + place.address.toString()

                placeMarkerOnMap(place.latLng)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean = false

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setUpMap()
    }

    private fun setUpMap() {
        checkPermission()

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                myRef.setValue(CustomLatLng(location.latitude.toString(), location.longitude.toString()))
                val currentLatLng = LatLng(location.latitude, location.longitude)
                if (myMarker == null) {
                    placeMarkerOnMap(currentLatLng, "Organizer")
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                } else {
                    myMarker?.position = currentLatLng
                }
//                findSegun()
//                placePartnersOnMap()
            }
        }
    }

    private fun startLocationUpdates() {
        checkPermission()
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }

        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(this@MapsActivity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()

        try {
            startActivityForResult(builder.build(this@MapsActivity), PLACE_PICKER_REQUEST)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }

    private fun placePartnersOnMap() {
        noelRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var lat = ""
                var lng = ""
                dataSnapshot.children.forEach {
                    if (it.key == "latitude") {
                        lat = it.value.toString()
                    }

                    if (it.key == "longitude") {
                        lng = it.value.toString()
                    }
                }

                val noelLatLng = LatLng(lat.toDouble(), lng.toDouble())
                if (noelMarker == null) {
                    placeNoelMarkerOnMap(noelLatLng, "Noel")
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(noelLatLng, 15f))
                } else {
                    noelMarker?.position = noelLatLng
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
//                          Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    private fun findSegun() {
        segunRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var lat = ""
                var lng = ""
                dataSnapshot.children.forEach {
                    if (it.key == "latitude") {
                        lat = it.value.toString()
                    }

                    if (it.key == "longitude") {
                        lng = it.value.toString()
                    }
                }

                val segunLatLng = LatLng(lat.toDouble(), lng.toDouble())
                if (segunMarker == null) {
                    placeSegunMarkerOnMap(segunLatLng, "Segun")
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(segunLatLng, 15f))
                } else {
                    segunMarker?.position = segunLatLng
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
//                          Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (null != addresses && addresses.isNotEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
        return addressText
    }

    private fun placeMarkerOnMap(location: LatLng, title: String? = null) {
        val address = getAddress(location)
        val markerOptions = MarkerOptions().position(location).title(title ?: address)
        myMarker = map.addMarker(markerOptions)
    }

    private fun placeNoelMarkerOnMap(location: LatLng, title: String? = null) {
        val address = getAddress(location)
        val markerOptions = MarkerOptions().position(location).title(title ?: address)
        noelMarker = map.addMarker(markerOptions)
    }

    private fun placeSegunMarkerOnMap(location: LatLng, title: String? = null) {
        val address = getAddress(location)
        val markerOptions = MarkerOptions().position(location).title(title ?: address)
        noelMarker = map.addMarker(markerOptions)
    }
}
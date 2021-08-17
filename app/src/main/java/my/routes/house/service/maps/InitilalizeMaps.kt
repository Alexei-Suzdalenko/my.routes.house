package my.routes.house.service.maps
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import my.routes.house.MapsActivity
object InitilalizeMaps {
    fun initializeMap(c: Context, mapsActivity: MapsActivity, googleMap: GoogleMap){
        if (ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mapsActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),  PackageManager.PERMISSION_GRANTED)
            Toast.makeText(mapsActivity, "return case", Toast.LENGTH_LONG).show()
            return
        }

        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
    }
}
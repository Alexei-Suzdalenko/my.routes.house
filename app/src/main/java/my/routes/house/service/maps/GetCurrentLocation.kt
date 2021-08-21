package my.routes.house.service.maps
import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng
import my.routes.house.MapsActivity
import my.routes.house.R

object GetCurrentLocation {
    private var locationManager : LocationManager? = null
    private var comandCurrent = ""
    private lateinit var context: MapsActivity
    private var locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
                // Toast.makeText(context, "" + location.longitude + ":" + location.latitude, Toast.LENGTH_LONG).show()
                context.putCamaraToCurrentPosition(LatLng(location.latitude,  location.longitude))
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun getCurrentLocation(c: MapsActivity, comand: String) {
        context = c
        locationManager = c.getSystemService(LOCATION_SERVICE) as LocationManager?
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),  PackageManager.PERMISSION_GRANTED)
            return
        }
        if(comand == "start_location") {
            comandCurrent = "start_location"
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
            // Toast.makeText(context, context.resources.getString(R.string.followMe), Toast.LENGTH_LONG).show()
        } else {
            locationManager?.removeUpdates(locationListener)
            // Toast.makeText(context, context.resources.getString(R.string.doNotFollowMe), Toast.LENGTH_LONG).show()
        }
    }

}
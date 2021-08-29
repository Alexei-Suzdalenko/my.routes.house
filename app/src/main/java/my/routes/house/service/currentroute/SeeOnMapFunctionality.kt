package my.routes.house.service.currentroute
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import my.routes.house.service.all.App
import my.routes.house.service.currentroute.activities.SeeOnMapPointActivity

object SeeOnMapFunctionality {
    fun seeOnMapFunctionality(c: SeeOnMapPointActivity, googleMap: GoogleMap){
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(c, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),  PackageManager.PERMISSION_GRANTED)
            Toast.makeText(c, "return case", Toast.LENGTH_LONG).show()
            return
        }
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
    }

    fun setClikedPointInMap(googleMap: GoogleMap){
        googleMap.addMarker(MarkerOptions()
                .position(LatLng(App.pointRouteEmptyApp.latitude, App.pointRouteEmptyApp.longitude))
                .title(App.pointRouteEmptyApp.name)
                .snippet( App.pointRouteEmptyApp.city + " " + App.pointRouteEmptyApp.address + " " + App.pointRouteEmptyApp.description )
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(App.pointRouteEmptyApp.latitude, App.pointRouteEmptyApp.longitude), 13F))
    }
}
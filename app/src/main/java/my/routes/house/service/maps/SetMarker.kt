package my.routes.house.service.maps
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import my.routes.house.service.all.App

object SetMarker {
    fun setMarker(mMap: GoogleMap){
        for (i in App.ordenListPoint.indices) {
            if (i == 0) {
                mMap.addMarker(
                    MarkerOptions()
                    .position(LatLng(App.ordenListPoint[0].latitude, App.ordenListPoint[0].longitude))
                    .title("Start " + App.ordenListPoint[0].name)
                    .snippet(App.ordenListPoint[0].description)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
            } else {
                mMap.addMarker(
                    MarkerOptions()
                    .position(LatLng(App.ordenListPoint[i].latitude, App.ordenListPoint[i].longitude))
                    .title((i + 1).toString() + " " + App.ordenListPoint[i].name)
                    .snippet(App.ordenListPoint[i].description))
            }
        }
    }
}
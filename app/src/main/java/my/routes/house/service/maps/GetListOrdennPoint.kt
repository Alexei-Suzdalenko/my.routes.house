package my.routes.house.service.maps
import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import my.routes.house.dataclass.PointRoute
import my.routes.house.service.all.App
import my.routes.house.service.all.App.Companion.ordenListPoint

object GetListOrdennPoint {
    fun getListOrdenPoint(point: PointRoute, currentList: ArrayList<PointRoute>){ // point: LatLng, currentList: ArrayList<LatLng>
        val loc1 = Location("")
        val loc2 = Location("")
        var i = 0
        var noneDistanceMeter = 9999999999999.toFloat()
           // Log.d("tag", "currentLocation ==> > >" + currentLocation.toString())
           // Log.d("tag", "currentList ==> > >" + currentList.size + "  " + currentList.toString())
            var current = point
            for(y in currentList.indices) {
                loc1.latitude = point.latitude
                loc1.longitude = point.longitude
                loc2.latitude = currentList[y].latitude
                loc2.longitude = currentList[y].longitude
                if( loc1.latitude == loc2.latitude ) continue
                val distanceInMeters: Float = loc1.distanceTo(loc2)
                if ( distanceInMeters < noneDistanceMeter ){
                    current = currentList[y]
                    noneDistanceMeter = distanceInMeters
                    i = y;
                }
            }
            ordenListPoint.add(current)
            currentList.removeAt(i)
            if(currentList.size > 0){
                getListOrdenPoint(current, currentList)
            }
    }
}
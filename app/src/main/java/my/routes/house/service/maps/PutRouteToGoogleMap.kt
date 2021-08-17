package my.routes.house.service.maps
import android.graphics.Color
import com.beust.klaxon.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import my.routes.house.MapsActivity
import my.routes.house.service.all.App
import java.lang.Exception
import java.net.URL
object PutRouteToGoogleMap {
    var i = 0
    fun putRouteToGoogleMap(c: MapsActivity, mMap: GoogleMap){
        val thread = Thread {
            try {
                val parser = Parser()
                val options = PolylineOptions()
                options.color(Color.BLUE)
                options.width(7f)

                for (i in App.ordenListPoint.indices) {
                    if (i + 1 >= App.ordenListPoint.size) break;
                    val one = LatLng(App.ordenListPoint[i].latitude, App.ordenListPoint[i].longitude)
                    val two = LatLng(App.ordenListPoint[i + 1].latitude, App.ordenListPoint[i + 1].longitude)
                    val urls = Maps_DecodePoly.getURL(one, two)
                    val result = URL(urls).readText()
                    val stringBuilder: StringBuilder = StringBuilder(result)
                    val json: JsonObject = parser.parse(stringBuilder) as JsonObject
                    val routes = json.array<JsonObject>("routes")
                    val points: JsonArray<JsonObject> = routes!!["legs"]["steps"][0] as JsonArray<JsonObject>
                    val polypts = points.flatMap { Maps_DecodePoly.decodePoly(it.obj("polyline")?.string("points")!!); }

                    for (point in polypts) { options.add(point); }
                    c.runOnUiThread { mMap.addPolyline(options); }
                }
                c.runOnUiThread{
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(App.ordenListPoint[0].latitude, App.ordenListPoint[0].longitude), 13F))
                }
            } catch (e: Exception){
                i++; if(i < 11){ putRouteToGoogleMap(c, mMap); }
            }
        }
        thread.start()
    }
}
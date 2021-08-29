package my.routes.house.service.currentroute
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_point_to_route.*
import my.routes.house.R
import my.routes.house.service.all.App
import my.routes.house.service.currentroute.activities.AddPointToRouteActivity
import my.routes.house.service.currentroute.activities.EditSelectedPointActivity
import org.json.JSONObject
import java.net.URL

object SetDatalayoutFile {
    fun setDataToLayoutFile(c: EditSelectedPointActivity){
       c.nameEditText.setText( App.pointRouteEmptyApp.name )
       c.descriptionEditText.setText( App.pointRouteEmptyApp.description )
       c.zipcode.setText( App.pointRouteEmptyApp.zipcode )
       c.city.setText( App.pointRouteEmptyApp.city )
       c.address.setText( App.pointRouteEmptyApp.address )
       c.latitudeEditText.setText( App.pointRouteEmptyApp.latitude.toString() )
       c.longitudeEditText.setText( App.pointRouteEmptyApp.longitude.toString() )
    }

    fun getCoordinatesEditActivity(c: EditSelectedPointActivity, zipcode: String, city: String, address: String ) {
        if(city.isEmpty() || city.isBlank()) return
        Thread {
            try {
                val uri = "https://maps.google.com/maps/api/geocode/json?address=+$zipcode+$city+$address&key=AIzaSyBJcJoJrGaUb_Gx1GJ2Swnh2N3gdF926Gw";
                val strResponse = URL(uri).readText() // getJSONObject, getJSONArray, JSONObject
                val objResponse = JSONObject( strResponse )
                val arrayResponse = objResponse.getJSONArray("results")
                val itemResponse = JSONObject(arrayResponse[0].toString())
                val result = JSONObject(itemResponse.get("geometry").toString())
                val objResult = JSONObject(result.get("location").toString())
                c.runOnUiThread {
                    val latitudeEditText = c.findViewById<EditText>(R.id.latitudeEditText)
                    val latitude =  objResult.get("lat").toString()
                    latitudeEditText.setText(latitude)
                    val longitudeEditText = c.findViewById<EditText>(R.id.longitudeEditText)
                    val longitude = objResult.get("lng").toString()
                    longitudeEditText.setText(longitude)
                }
            } catch (exception: Exception) { c.runOnUiThread { Toast.makeText(c, "Error, comment to Alexei Suzdalenko", Toast.LENGTH_LONG).show(); }; }
        }.start()
    }
}
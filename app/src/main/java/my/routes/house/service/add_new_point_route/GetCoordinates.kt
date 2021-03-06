package my.routes.house.service.add_new_point_route
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_point_to_route.*
import org.json.JSONObject
import java.net.URL
import my.routes.house.service.currentroute.activities.AddPointToRouteActivity
import my.routes.house.R
import my.routes.house.dataclass.PointRoute
import my.routes.house.service.all.App
import java.lang.Thread.sleep

object GetCoordinates {
    fun getCoordinates(c: AddPointToRouteActivity, zipcode: String, city: String, address: String ) {
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



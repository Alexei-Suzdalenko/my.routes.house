package my.routes.house.service.add_new_point_route
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_point_to_route.*
import my.routes.house.AddPointToRouteActivity
import my.routes.house.R
import my.routes.house.dataclass.PointRoute
import my.routes.house.service.add_new_point_route.GetCoordinates.getCoordinates
import my.routes.house.service.all.App

object AddNewPointFunc {
    val uid = Firebase.auth.currentUser!!.uid
    val idRoute = App.sharedPreferences.getString("idRoute", "").toString()

    fun addNewPointFuncnality(c: AddPointToRouteActivity) {
        // get coordinates button in add new point page
        c.get_coordinates.setOnClickListener {
            getCoordinates(c, c.zipcode.text.toString(), c.city.text.toString(),  c.address.text.toString())
        }
        // save button in add new point page
        c.save_new_point.setOnClickListener {
            val nameInsertPoint = c.nameEditText.text.toString()
            if( nameInsertPoint.isBlank() ) return@setOnClickListener
            var idPoint = App.sharedPreferences.getString("idPoint", "none").toString()
                  if( idPoint == "none" ) {
                      idPoint = System.currentTimeMillis().toString()
                      App.editor.putString("idPoint", idPoint); App.editor.apply()
                  }
            var latitude = 0.0 ; var longitude = 0.0
            try {
                 latitude = c.latitudeEditText.text.toString().replace(',', '.').toDouble()
                 longitude =  c.longitudeEditText.text.toString().replace(',', '.').toDouble()
                if(latitude == 0.0 || longitude == 0.0) return@setOnClickListener
            } catch (e: java.lang.Exception){}
            val zipcode = c.zipcode.text.toString()
            val city = c.city.text.toString()
            val address = c.address.text.toString()

            Firebase.firestore.collection("list_points").document(uid).collection(idRoute).document(idPoint)
                .set(PointRoute(idPoint, nameInsertPoint, c.descriptionEditText.text.toString(), latitude, longitude, zipcode, city, address))
                .addOnSuccessListener {
                    Toast.makeText(c, c.resources.getString(R.string.point_created) + " " + nameInsertPoint, Toast.LENGTH_LONG).show()
                    App.playDefaultSound(c)
                    c.finish()
                } .addOnFailureListener { Toast.makeText(c, "Error, comment to Alexei Suzdalenko", Toast.LENGTH_LONG).show(); }
        }
    }
}
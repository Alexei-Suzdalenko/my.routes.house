package my.routes.house.service.currentroute
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.routes.house.CurrentRouteActivity
import my.routes.house.R
import my.routes.house.dataclass.PointRoute
import my.routes.house.service.all.App
import my.routes.house.service.all.App.Companion.pointList
import my.routes.house.service.listroutes.ListRoutes_GetListRoutes
object GetListPointCurrentRoute {
    fun getListPointsCurrentRoute(idRoute: String, c: CurrentRouteActivity){
        val uid = Firebase.auth.currentUser!!.uid
        val customRouteTextview = c.findViewById<TextView>(R.id.custom_route_textview)
        customRouteTextview.visibility = View.VISIBLE
        customRouteTextview.text = c.resources.getString(R.string.loading)
        pointList = ArrayList()
        Firebase.firestore.collection("list_points").document(uid).collection(idRoute)
            .orderBy("id")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val id = document.data["id"].toString()
                    val name = document.data["name"].toString()
                    val description =  document.data["description"].toString()
                    val latitude = document.data["latitude"].toString().toDouble()
                    val longitude = document.data["longitude"].toString().toDouble()
                    pointList.add(PointRoute(id, name, description, latitude, longitude))
                }
                if( documents.documents.size == 0 ){
                    customRouteTextview.visibility = View.VISIBLE
                    customRouteTextview.text = c.resources.getString(R.string.not_points)
                } else {
                    customRouteTextview.visibility = View.GONE
                    val pointsListView = c.findViewById<ListView>(R.id.list_points)
                        pointsListView.adapter = PointersListAdapter(c, pointList)
                }
            }
            .addOnFailureListener { getListPointsCurrentRoute(idRoute, c); Toast.makeText(c, "Error, comment to Alexei Suzdalenko", Toast.LENGTH_LONG).show(); }
    }
}

package my.routes.house.service.listroutes
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.routes.house.ListRoutesActivity
import my.routes.house.R
import my.routes.house.dataclass.Route
import my.routes.house.service.listroutes.ListViewRoutesFunctionality.Companion.routesLoaded
import kotlin.collections.ArrayList
class ListRoutes_GetListRoutes {
    companion object {
        fun getListRoutes(c: ListRoutesActivity): ArrayList<Route>? {
            val listRoutesPrepared = ArrayList<Route>()
            val dontHaveRoutesTextView = c.findViewById<TextView>(R.id.dont_have_routes)
            val listView = c.findViewById<ListView>(R.id.list_routes)
            loadingRoutesInfoTextView(dontHaveRoutesTextView)

            val uid = Firebase.auth.currentUser!!.uid
            Firebase.firestore.collection("routes").document(uid).collection(uid).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val id = document.data["id"].toString()
                        val name = document.data["name"].toString()
                        val date = document.data["date"].toString()
                        val route = Route(id, name, date)
                        listRoutesPrepared.add(route)
                    }
                    listRoutesPrepared.reverse()
                    routesLoaded(dontHaveRoutesTextView, listView, listRoutesPrepared, c)
                    if( documents.documents.size == 0 ){ showZeroroutes(dontHaveRoutesTextView); }

                }
                .addOnFailureListener { _ -> routesLoaded(dontHaveRoutesTextView, listView, listRoutesPrepared, c); }
            return null
        }

        fun loadingRoutesInfoTextView(textView: TextView){
            textView.visibility = View.VISIBLE
            textView.setText(textView.resources.getString(R.string.loading))
        }
        fun showZeroroutes(textView: TextView){
            textView.visibility = View.VISIBLE
            textView.setText(textView.resources.getString(R.string.zero_routes))
        }
    }
}

// Log.d("tag", " + + + + + ${document.id} => ${document.data}")
// .document("1628958480440") => document.data
// Log.d("tag", " + + + + + ${documents.toString()} => ${documents.documents.toString()}" + " ===> " + documents.documents.size)
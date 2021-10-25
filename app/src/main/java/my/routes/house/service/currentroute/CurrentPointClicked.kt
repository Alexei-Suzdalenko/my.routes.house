package my.routes.house.service.currentroute
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.routes.house.CurrentRouteActivity
import my.routes.house.R
import my.routes.house.dataclass.PointRoute
import my.routes.house.service.all.App
import my.routes.house.service.all.App.Companion.pointRouteEmptyApp
import my.routes.house.service.currentroute.ShowMeOptionsEditPoint.showMeOptionsEditPoint
import my.routes.house.service.currentroute.activities.DetailPointActivity
import my.routes.house.service.currentroute.activities.EditSelectedPointActivity
import my.routes.house.service.currentroute.activities.SeeOnMapPointActivity

object CurrentPointClicked {
    fun clickCurrentPoint(idRoute: String, pointsListView: ListView, c: CurrentRouteActivity, pointList: ArrayList<PointRoute>) {
        pointsListView.setOnItemClickListener { _, _, i, _ ->
            val arrayOptions = arrayOf ( c.resources.getString(R.string.detail), c.resources.getString(R.string.show_in_map ), c.resources.getString(R.string.none), c.resources.getString(R.string.edit_point), c.resources.getString(R.string.none), c.resources.getString(R.string.delete_point ) )
            AlertDialog.Builder(c).setTitle(R.string.choose_option).setItems( arrayOptions) { dialog, which ->
                pointRouteEmptyApp = pointList[i]
                App.editor.putString("idPoint", pointList[i].id); App.editor.apply()
                    if( which == 0 ) c.startActivity( Intent( c, DetailPointActivity::class.java ))
                    if( which == 1 ) {
                        // aqui otro punto de anuncio
                        c.startActivity( Intent( c, SeeOnMapPointActivity::class.java ))
                    }
                    if( which == 3 ) c.startActivity( Intent( c, EditSelectedPointActivity::class.java ))
                    if( which == 5 ) showMeOptionsRemovePoint( idRoute,  c , pointList[i]  )
                    dialog.dismiss()
                }.create().show()
        }
    }

    private fun showMeOptionsRemovePoint(idRoute: String, c: CurrentRouteActivity, pointRoute: PointRoute ) {
        AlertDialog.Builder(c).setTitle(R.string.delete_point).setMessage(pointRoute.name).setPositiveButton("OK") { dialog, _ ->
            secondConfirmToDeletePoint( idRoute, c, pointRoute ); dialog.dismiss(); }.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }.create().show()
    }

    private fun secondConfirmToDeletePoint( idRoute: String, c: CurrentRouteActivity, pointRoute: PointRoute ) {
        AlertDialog.Builder(c).setTitle(R.string.delete_point_yes).setMessage(pointRoute.name).setPositiveButton("OK") { dialog, _ ->
                val uid = Firebase.auth.currentUser!!.uid
                Firebase.firestore.collection("list_points").document(uid).collection(idRoute).document(pointRoute.id).delete().addOnSuccessListener {
                        Toast.makeText(c, c.resources.getString(R.string.deleted) + " " + pointRoute.name, Toast.LENGTH_LONG).show()
                        GetListPointCurrentRoute.getListPointsCurrentRoute(idRoute, c)
                        App.playDefaultSound(c)
                        dialog.dismiss()
                    }.addOnFailureListener { dialog.dismiss(); }
            }.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }.create().show()
    }

}
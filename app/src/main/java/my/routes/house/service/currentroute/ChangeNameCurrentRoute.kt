package my.routes.house.service.currentroute
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.text.InputType
import android.view.Gravity
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.routes.house.CurrentRouteActivity
import my.routes.house.R
import my.routes.house.dataclass.Route
import my.routes.house.service.listroutes.ListRoutes_GetListRoutes
import java.util.*
object ChangeNameCurrentRoute {
    fun changeRouteName(routeName: String, idRoute:String, c: CurrentRouteActivity){
        val builder: AlertDialog.Builder = AlertDialog.Builder(c)
        builder.setTitle(c.resources.getString(R.string.change_name))

        val linearLayout = LinearLayout(c)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER

        val editText = EditText(c)
        editText.hint = c.resources.getString(R.string.write_name_route)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        editText.setText(routeName)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(22, 44, 22, 44)
        editText.layoutParams = params

        linearLayout.addView(editText)
        builder.setView(linearLayout)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ ->
            val nameInsertRoute = editText.text.toString()
            if( nameInsertRoute.isBlank() || nameInsertRoute.isEmpty()) { dialog.dismiss(); return@OnClickListener; }
            val uid = Firebase.auth.currentUser!!.uid

            Firebase.firestore.collection("routes").document(uid).collection(uid).document(idRoute).update("name", nameInsertRoute)
                .addOnSuccessListener {
                    Toast.makeText(c, c.resources.getString(R.string.route_name_changed) + " " + nameInsertRoute, Toast.LENGTH_LONG).show()
                    val intent = Intent(c, CurrentRouteActivity::class.java)
                    intent.putExtra("id", idRoute)
                    intent.putExtra("name", nameInsertRoute)
                    c.startActivity(intent)
                    c.finish()
                }
                .addOnFailureListener { Toast.makeText(c, "Error, comment to Alexei Suzdalenko", Toast.LENGTH_LONG).show(); }
            dialog.dismiss()
        })
        builder.setNegativeButton(c.resources.getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
        builder.show()
    }
}
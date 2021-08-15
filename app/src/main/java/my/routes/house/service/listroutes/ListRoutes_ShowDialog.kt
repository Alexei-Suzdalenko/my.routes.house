package my.routes.house.service.listroutes
import android.content.DialogInterface
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
import my.routes.house.ListRoutesActivity
import my.routes.house.R
import my.routes.house.dataclass.Route
import my.routes.house.service.listroutes.ListRoutes_GetListRoutes.Companion.getListRoutes
import java.util.*
object ListRoutes_ShowDialog {
    fun showdialog(c: ListRoutesActivity){
        val builder: AlertDialog.Builder = AlertDialog.Builder(c)
        builder.setTitle(c.resources.getString(R.string.add_route))

        val linearLayout = LinearLayout(c)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER

        val editText = EditText(c)
        editText.hint = c.resources.getString(R.string.write_name_route)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(22, 44, 22, 44)
        editText.layoutParams = params

        linearLayout.addView(editText)
        builder.setView(linearLayout)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ ->
            val nameInsertRoute = editText.text.toString()
            if( nameInsertRoute.isBlank() || nameInsertRoute.isEmpty()) { dialog.dismiss(); return@OnClickListener; }

            val cal: Calendar = Calendar.getInstance()
            val year: Int = cal.get(Calendar.YEAR)
            val month: Int = cal.get(Calendar.MONTH)
            val day: Int = cal.get(Calendar.DAY_OF_MONTH)
            val date = "$day/$month/$year"

            val uid = Firebase.auth.currentUser!!.uid
            val time = System.currentTimeMillis().toString()

            Firebase.firestore.collection("routes").document(uid).collection(uid).document(time).set(Route(time, nameInsertRoute, date))
                  .addOnSuccessListener {
                      Toast.makeText(c, nameInsertRoute + " " + c.resources.getString(R.string.new_route_added), Toast.LENGTH_LONG).show()
                      getListRoutes(c)
                  }
                  .addOnFailureListener { Toast.makeText(c, "Error, comment to Alexei Suzdalenko", Toast.LENGTH_LONG).show(); getListRoutes(c); }
            dialog.dismiss()
        })
        builder.setNegativeButton(c.resources.getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
        builder.show()
    }
}
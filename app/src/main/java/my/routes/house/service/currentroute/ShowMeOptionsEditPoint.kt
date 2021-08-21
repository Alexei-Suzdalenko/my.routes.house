package my.routes.house.service.currentroute
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
import my.routes.house.CurrentRouteActivity
import my.routes.house.R
import my.routes.house.dataclass.PointRoute
import my.routes.house.service.all.App

object ShowMeOptionsEditPoint {
    fun showMeOptionsEditPoint(idRoute: String, c: CurrentRouteActivity, pointRoute: PointRoute ){
        val builder: AlertDialog.Builder = AlertDialog.Builder(c)
        builder.setTitle(c.resources.getString(R.string.edit_point))

        val linearLayout = LinearLayout(c)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.VERTICAL
        layoutParams.gravity = Gravity.CENTER
        linearLayout.setPadding(33, 33,33,33);
        linearLayout.layoutParams = layoutParams

        val nameEditText = EditText(c)
        nameEditText.hint = c.resources.getString(R.string.name); nameEditText.inputType = InputType.TYPE_CLASS_TEXT; nameEditText.setText( pointRoute.name )
        val descriptionEditText = EditText(c)
        descriptionEditText.hint = c.resources.getString(R.string.description); descriptionEditText.inputType = InputType.TYPE_CLASS_TEXT; descriptionEditText.setText( pointRoute.description )
        val latitudeEditText = EditText(c)
        latitudeEditText.hint = c.resources.getString(R.string.latitude); latitudeEditText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL; latitudeEditText.setText( pointRoute.latitude.toString() )
        val longitudeEditText = EditText(c)
        longitudeEditText.hint = c.resources.getString(R.string.longitude); longitudeEditText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL; longitudeEditText.setText( pointRoute.longitude.toString() )

        linearLayout.addView(nameEditText); linearLayout.addView(descriptionEditText); linearLayout.addView(latitudeEditText);  linearLayout.addView(longitudeEditText);
        builder.setView(linearLayout)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ ->
            val nameInsertPoint = nameEditText.text.toString()
            if( nameInsertPoint.isBlank() || longitudeEditText.text.toString().isBlank() || latitudeEditText.text.toString().isBlank() ) { dialog.dismiss(); return@OnClickListener; }
            val uid = Firebase.auth.currentUser!!.uid
            val idPoint = pointRoute.id
            val longitude =  longitudeEditText.text.toString().replace(',', '.').toDouble()
            val latitude = latitudeEditText.text.toString().replace(',', '.').toDouble()
            Firebase.firestore.collection("list_points").document(uid).collection(idRoute).document(idPoint)
                .set(PointRoute(idPoint, nameInsertPoint, descriptionEditText.text.toString(), latitude, longitude))
                .addOnSuccessListener {
                    Toast.makeText(c, c.resources.getString(R.string.point_updated) + " " + nameInsertPoint, Toast.LENGTH_LONG).show()
                    GetListPointCurrentRoute.getListPointsCurrentRoute(idRoute, c)
                    App.playDefaultSound(c)
                }
                .addOnFailureListener { Toast.makeText(c, "Error, comment to Alexei Suzdalenko", Toast.LENGTH_LONG).show(); }
            dialog.dismiss()
        })
        builder.setNegativeButton(c.resources.getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
        builder.show()
    }
}
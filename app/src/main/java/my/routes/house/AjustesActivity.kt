package my.routes.house
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_ajustes.*
import my.routes.house.service.all.App
class AjustesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        settings_switch.isChecked = App.sharedPreferences.getBoolean("optimization_route_active", false)
        settings_switch.setOnClickListener {
            App.editor.putBoolean( "optimization_route_active", settings_switch.isChecked ); App.editor.apply()
            if ( settings_switch.isChecked ) Toast.makeText(this, resources.getString(R.string.optimizacion_active), Toast.LENGTH_LONG ).show()
            else  Toast.makeText(this, resources.getString(R.string.optimizacion_desabled), Toast.LENGTH_LONG ).show()
        }
    }
}
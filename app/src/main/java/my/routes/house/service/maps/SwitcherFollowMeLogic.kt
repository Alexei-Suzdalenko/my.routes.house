package my.routes.house.service.maps
import android.annotation.SuppressLint
import android.view.View
import android.widget.Switch
import android.widget.TextView
import my.routes.house.MapsActivity
import my.routes.house.R
import my.routes.house.service.all.App
import android.widget.CheckBox
import android.widget.Toast
import my.routes.house.service.maps.GetCurrentLocation.getCurrentLocation
import java.lang.Thread.sleep

object SwitcherFollowMeLogic {
    @SuppressLint("UseSwitchCompatOrMaterialCode")

    fun followMeButtonLogic(c: MapsActivity) {
        val  followMeSwitch     = c.findViewById<Switch>(R.id.follow_me_switch)
              followMeSwitch.isChecked = App.sharedPreferences.getBoolean("is_enabled", false)
              followMeSwitch.setOnClickListener {
                  App.editor.putBoolean("is_enabled", followMeSwitch.isChecked); App.editor.apply()
                  showInfoTextView( c, followMeSwitch.isChecked )
              }
        if (App.sharedPreferences.getBoolean("is_enabled", false))  getCurrentLocation(c, "start_location")
        else getCurrentLocation(c, "end_location")
    }

    private fun showInfoTextView(c: MapsActivity, xxx: Boolean) {
        val  followMeTextView = c.findViewById<TextView>(R.id.follow_me_textview)
               followMeTextView.visibility = View.VISIBLE
        val messageInfo = if ( xxx ) {
            getCurrentLocation(c, "start_location"); c.resources.getString(R.string.followMe)
        } else {
            getCurrentLocation(c, "end_location"); c.resources.getString(R.string.doNotFollowMe)
        }
        followMeTextView.text = messageInfo
        val thread = Thread {
            sleep(2000)
            c.runOnUiThread{ followMeTextView.visibility = View.GONE }
        }; thread.start()
    }
}
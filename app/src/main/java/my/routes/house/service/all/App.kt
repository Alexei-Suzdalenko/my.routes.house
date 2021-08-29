package my.routes.house.service.all
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import my.routes.house.dataclass.PointRoute
import android.media.RingtoneManager

import android.media.Ringtone
import android.net.Uri
import com.firebase.ui.auth.AuthUI.getApplicationContext
import java.lang.Exception
class App: Application() {
    companion object{
        lateinit var pointList: ArrayList<PointRoute>
        lateinit var ordenListPoint: ArrayList<PointRoute>
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        var listRoutesLoaded = false
        var flatButtonClicked = false
        lateinit var pointRouteEmptyApp: PointRoute
        fun playDefaultSound(c: Context){
            try {
                val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val r = RingtoneManager.getRingtone(c, notification)
                r.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        pointList = ArrayList()
        ordenListPoint = ArrayList()
        sharedPreferences = getSharedPreferences("alexei_suzdalenko", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
}
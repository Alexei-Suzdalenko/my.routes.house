package my.routes.house.service.all
import android.app.Application
import android.content.Context
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
    override fun onCreate() {
        super.onCreate()
        pointList = ArrayList()
        ordenListPoint = ArrayList()
    }
}
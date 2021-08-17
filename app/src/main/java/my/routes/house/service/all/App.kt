package my.routes.house.service.all
import android.app.Application
import my.routes.house.dataclass.PointRoute
class App: Application() {
    companion object{
        lateinit var pointList: ArrayList<PointRoute>
        lateinit var ordenListPoint: ArrayList<PointRoute>
    }
    override fun onCreate() {
        super.onCreate()
        pointList = ArrayList()
        ordenListPoint = ArrayList()
    }
}
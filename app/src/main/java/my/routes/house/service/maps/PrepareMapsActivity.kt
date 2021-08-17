package my.routes.house.service.maps
import my.routes.house.service.all.App
object PrepareMapsActivity {
    fun prepareArrayMaps(){
        App.ordenListPoint = ArrayList()
        App.ordenListPoint.add(App.pointList[0])
        App.pointList.removeAt(0)
    }
}
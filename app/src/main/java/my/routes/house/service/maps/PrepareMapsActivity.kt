package my.routes.house.service.maps
import my.routes.house.service.all.App

object PrepareMapsActivity {

    fun prepareArrayMaps(){
        App.ordenListPoint = ArrayList()
        // si no tengo ningun punto creado en el mapa
        if( App.pointList.size > 0 ) {
            App.ordenListPoint.add( App.pointList[0] )
            App.pointList.removeAt(0)
        }
        // si tengio al menos un punto
        if( App.pointList.size >= 1 ) {
            if ( App.sharedPreferences.getBoolean("optimization_route_active", false) ) GetListOrdennPoint.getListOrdenPoint( App.pointList[0], App.pointList )
            else {
                for (i in App.pointList.indices) {
                    App.ordenListPoint.add( App.pointList[i] )
                }
            }
        }
    }

}
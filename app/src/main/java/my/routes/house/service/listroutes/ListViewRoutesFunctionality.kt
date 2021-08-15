package my.routes.house.service.listroutes
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import my.routes.house.ListRoutesActivity
import my.routes.house.dataclass.Route
class ListViewRoutesFunctionality {
    companion object {
        fun routesLoaded(textView: TextView, listView: ListView, listRoutesPrepared: ArrayList<Route>, c: ListRoutesActivity){
            textView.visibility = View.GONE
            listView.adapter = RouterListAdapter(c, listRoutesPrepared)
            listView.setOnItemClickListener { _, _, i, _ ->
                Toast.makeText(c, listRoutesPrepared[i].name, Toast.LENGTH_LONG).show()
            }
        }
    }
}
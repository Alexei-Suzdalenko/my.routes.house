package my.routes.house.service.listroutes
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import my.routes.house.R
import my.routes.house.dataclass.Route

class RouterListAdapter (private val c: Context, private val listRoutes: ArrayList<Route>) : BaseAdapter() {
    override fun getCount(): Int {
        return listRoutes.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItem(position: Int): Any {
        return listRoutes[position]
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val layotInflater  = LayoutInflater.from(c)
        val rowMain        = layotInflater.inflate(R.layout.route_layout , p2, false)
        val nameRoute      = rowMain.findViewById<TextView>(R.id.name_route)
            nameRoute.text = listRoutes.get(index = p0).name
        val dateRoute      = rowMain.findViewById<TextView>(R.id.date_route)
            dateRoute.text = listRoutes.get(index = p0).date
        return rowMain
    }

}
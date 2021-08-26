package my.routes.house.service.currentroute
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import my.routes.house.R
import my.routes.house.dataclass.PointRoute
import my.routes.house.dataclass.Route
class PointersListAdapter (private val c: Context, private val listPoints: ArrayList<PointRoute>) : BaseAdapter() {
    override fun getCount(): Int {
        return listPoints.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItem(position: Int): Any {
        return listPoints[position]
    }
    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val layotInflater    = LayoutInflater.from(c)
        val rowMain          = layotInflater.inflate(R.layout.point_layout , p2, false)
        val namePoint        = rowMain.findViewById<TextView>(R.id.name_point)
            namePoint.text   = listPoints.get(index = p0).name
        val description      = rowMain.findViewById<TextView>(R.id.description_point)
             description.text = listPoints.get(index = p0).description.toString()
        val city      = rowMain.findViewById<TextView>(R.id.city)
             city.text = listPoints.get(index = p0).city.toString()
        val address      = rowMain.findViewById<TextView>(R.id.address)
             address.text = listPoints.get(index = p0).address.toString()
        val latitude         = rowMain.findViewById<TextView>(R.id.latitude_point)
            latitude.text    = "Lat: " + listPoints.get(index = p0).latitude.toString()
        val longitude        = rowMain.findViewById<TextView>(R.id.longitude_point)
            longitude.text   = "Long: " + listPoints.get(index = p0).longitude.toString()
        return rowMain
    }
}
package my.routes.house
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import my.routes.house.service.currentroute.AddNewRoutePoint.addNewRoutePoint
import my.routes.house.service.currentroute.ChangeNameCurrentRoute.changeRouteName
import my.routes.house.service.currentroute.GetListPointCurrentRoute.getListPointsCurrentRoute
import my.routes.house.service.listroutes.ListRoutes_ShowDialog
class CurrentRouteActivity : AppCompatActivity() {
    var routeName = ""; var idRoute = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_route)

        routeName = intent.getStringExtra("name").toString()
        idRoute   = intent.getStringExtra("id").toString()
        supportActionBar?.title = routeName
    }

    override fun onResume() {
        super.onResume()
        getListPointsCurrentRoute(idRoute, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.current_route_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.open_google_map -> {
                startActivity(Intent(this, MapsActivity::class.java))
                true
            }
            R.id.change_name -> {
                changeRouteName(routeName, idRoute, this)
                true
            }
            R.id.add_point -> {
                addNewRoutePoint(idRoute, this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
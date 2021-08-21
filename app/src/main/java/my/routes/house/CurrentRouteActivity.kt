package my.routes.house
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_current_route.*
import my.routes.house.service.all.App
import my.routes.house.service.all.App.Companion.flatButtonClicked
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
        add_new_point.setOnClickListener { flatButtonClicked= true;  addNewRoutePoint(idRoute, this);  }
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
                if (App.pointList.size == 0 ) { Toast.makeText(this, resources.getString(R.string.add_point_begin), Toast.LENGTH_LONG).show(); }
                else {
                    if ( App.listRoutesLoaded ) startActivity(Intent(this, MapsActivity::class.java))
                    else Toast.makeText(this, resources.getString(R.string.try_again), Toast.LENGTH_LONG).show()
                }
                true
            }
            R.id.author -> { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site)))); true; }
            R.id.change_name -> { changeRouteName(routeName, idRoute, this); true; }
            // R.id.add_point -> { addNewRoutePoint(idRoute, this); true; }
            R.id.your_comment -> {startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=my.routes.house"))); true; }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
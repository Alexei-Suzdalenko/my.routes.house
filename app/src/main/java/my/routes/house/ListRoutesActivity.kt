package my.routes.house
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import my.routes.house.service.listroutes.ListRoutes_GetListRoutes.Companion.getListRoutes
import my.routes.house.service.listroutes.ListRoutes_ShowDialog
class ListRoutesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_routes)
    }

    override fun onResume() {
        super.onResume()
        getListRoutes(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_routes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_route -> { Toast.makeText(this, resources.getString(R.string.add_route), Toast.LENGTH_LONG).show(); ListRoutes_ShowDialog.showdialog(this); true; }
            R.id.author -> { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site)))); true; }
            R.id.settings -> { startActivity(Intent(this, AjustesActivity::class.java)); true; }
            R.id.logout -> { Firebase.auth.signOut(); startActivity(Intent(this, LoginActivity::class.java)); finish(); true; }
            R.id.your_comment -> {startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=my.routes.house"))); true; }
            else -> super.onOptionsItemSelected(item)
        }
    }





}
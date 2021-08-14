package my.routes.house
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ListRoutesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_routes)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_routes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_route -> {
                Toast.makeText(this, resources.getString(R.string.add_route), Toast.LENGTH_LONG).show()
                startActivity(Intent(this, AddNewRouteActivity::class.java));
                true
            }
            R.id.logout -> {
                Firebase.auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java)); finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
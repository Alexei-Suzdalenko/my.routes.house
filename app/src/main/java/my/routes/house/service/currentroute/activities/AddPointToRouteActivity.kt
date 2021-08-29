package my.routes.house.service.currentroute.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.routes.house.R
import my.routes.house.service.add_new_point_route.AddNewPointFunc.addNewPointFuncnality
class AddPointToRouteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_point_to_route)

        addNewPointFuncnality(this)
    }
}
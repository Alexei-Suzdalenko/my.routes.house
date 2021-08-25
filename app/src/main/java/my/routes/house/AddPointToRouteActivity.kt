package my.routes.house
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_point_to_route.*
import my.routes.house.dataclass.PointRoute
import my.routes.house.service.add_new_point_route.AddNewPointFunc.addNewPointFuncnality
import my.routes.house.service.all.App

class AddPointToRouteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_point_to_route)

        addNewPointFuncnality(this)
    }
}
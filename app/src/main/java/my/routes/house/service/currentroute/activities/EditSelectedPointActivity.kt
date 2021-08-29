package my.routes.house.service.currentroute.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_point_to_route.*
import my.routes.house.R
import my.routes.house.service.add_new_point_route.AddNewPointFunc
import my.routes.house.service.all.App.Companion.pointRouteEmptyApp
import my.routes.house.service.currentroute.SetButtonFunctionality.setButtonFunctionality
import my.routes.house.service.currentroute.SetDatalayoutFile.setDataToLayoutFile

class EditSelectedPointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_point_to_route)
        title = resources.getString(R.string.edit_point) + " " + pointRouteEmptyApp.name

        setDataToLayoutFile(this)
        setButtonFunctionality(this)
     //   get_coordinates
     //   save_new_point
    }

}
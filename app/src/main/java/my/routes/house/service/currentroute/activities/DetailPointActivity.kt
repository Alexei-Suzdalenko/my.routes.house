package my.routes.house.service.currentroute.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.detail_point_activity.*
import my.routes.house.R
import my.routes.house.dataclass.PointRoute
import my.routes.house.service.all.App
import java.io.Serializable

class DetailPointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_point_activity)

        nameDetail.text          = App.pointRouteEmptyApp.name
        descriptionDetail.text = App.pointRouteEmptyApp.description
        zipcodeDetail.text       = App.pointRouteEmptyApp.zipcode
        cityDetail.text             = App.pointRouteEmptyApp.city
        addressDetail.text       = App.pointRouteEmptyApp.address
        latitudeDetail.text       = App.pointRouteEmptyApp.latitude.toString()
        longitudeDetail.text     = App.pointRouteEmptyApp.longitude.toString()
    }
}
package my.routes.house.service.currentroute.activities
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_maps.*
import my.routes.house.R
import my.routes.house.databinding.ActivityMapsBinding
import my.routes.house.service.all.App
import my.routes.house.service.currentroute.SeeOnMapFunctionality.seeOnMapFunctionality
import my.routes.house.service.currentroute.SeeOnMapFunctionality.setClikedPointInMap
import my.routes.house.service.maps.*
class SeeOnMapPointActivity : AppCompatActivity() , OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {
    private var mMap: GoogleMap? = null
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        title = App.pointRouteEmptyApp.name
        follow_me_switch.visibility = View.GONE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        seeOnMapFunctionality(this, mMap!!)
        mMap!!.setOnMyLocationButtonClickListener(this);
        mMap!!.setOnMyLocationClickListener(this)
        setClikedPointInMap(mMap!!)
    }

    override fun onMyLocationButtonClick(): Boolean { return false; }
    override fun onMyLocationClick(p0: Location) {}
}
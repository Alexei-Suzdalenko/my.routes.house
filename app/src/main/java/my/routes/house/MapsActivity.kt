package my.routes.house
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import my.routes.house.databinding.ActivityMapsBinding
import my.routes.house.service.all.App
import my.routes.house.service.all.App.Companion.ordenListPoint
import my.routes.house.service.all.App.Companion.pointList
import my.routes.house.service.maps.GetListOrdennPoint.getListOrdenPoint
import my.routes.house.service.maps.InitilalizeMaps.initializeMap
import my.routes.house.service.maps.PrepareMapsActivity.prepareArrayMaps
import my.routes.house.service.maps.PutRouteToGoogleMap.putRouteToGoogleMap
import my.routes.house.service.maps.SetMarker.setMarker
import my.routes.house.service.maps.SwitcherFollowMeLogic.followMeButtonLogic
import java.lang.Thread.sleep

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {
    private var mMap: GoogleMap? = null
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        prepareArrayMaps()
        followMeButtonLogic(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        initializeMap(this, this, mMap!!)
        mMap!!.setOnMyLocationButtonClickListener(this);
        mMap!!.setOnMyLocationClickListener(this)
        setMarker(mMap!!)
        putRouteToGoogleMap(this, mMap!!)
    }

    override fun onMyLocationButtonClick(): Boolean { return false; }
    override fun onMyLocationClick(p0: Location) {}

    fun putCamaraToCurrentPosition(latLng: LatLng){
        if (App.sharedPreferences.getBoolean("is_enabled", false)) mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13F))
    }
}



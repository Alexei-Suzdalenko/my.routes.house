package my.routes.house
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.beust.klaxon.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import my.routes.house.databinding.ActivityMapsBinding
import my.routes.house.service.maps.Maps_DecodePoly.decodePoly
import my.routes.house.service.maps.Maps_DecodePoly.getURL
import java.net.URL
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {
    lateinit var listResult : ArrayList<LatLng>
    lateinit var listLocations : ArrayList<LatLng>
    lateinit var listLocAsc : ArrayList<LatLng>
    lateinit var options: PolylineOptions
    lateinit var LatLongB: LatLngBounds.Builder
    lateinit var loc1: Location
    lateinit var loc2: Location
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var noneDistanceMeter = 9999999999999.toFloat()
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loc1 = Location("")
        loc2 = Location("")


        listResult = ArrayList<LatLng>()
        listLocations = ArrayList<LatLng>()
        listLocAsc = ArrayList<LatLng>()
        listLocations.add(LatLng(40.207962, 16.456076))
        listLocations.add(LatLng(48.023765, 37.766702))
        listLocations.add(LatLng(41.207962, 36.456076))
        listLocations.add(LatLng(37.241179, -5.360650))
        listLocations.add(LatLng(55.692987,37.428547))
        listLocations.add(LatLng(57.251634, 22.595117))
        listLocations.add(LatLng(53.913404,27.588817))



        var i = 0;
        var currentLocation = listLocations[0]
        listResult.add(listLocations[0])
        listLocations.removeAt(0)

        getNextSite(currentLocation, listLocations)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    fun getNextSite(currentLocation: LatLng, currentList: ArrayList<LatLng>){
        noneDistanceMeter = 9999999999999.toFloat()
        Log.d("tag", "currentLocation ==> > >" + currentLocation.toString())
        Log.d("tag", "currentList ==> > >" + currentList.size + "  " + currentList.toString())
        var current = currentLocation
        for(y in currentList.indices){
            loc1.latitude = currentLocation.latitude
            loc1.longitude = currentLocation.longitude
            loc2.latitude = listLocations[y].latitude
            loc2.longitude = listLocations[y].longitude
            if(loc1.latitude == loc2.latitude) continue
            val distanceInMeters: Float = loc1.distanceTo(loc2)
            if(distanceInMeters < noneDistanceMeter){
                current = listLocations[y]
                noneDistanceMeter = distanceInMeters
                i = y;
            }
        }
        listResult.add(current)
        currentList.removeAt(i)
        if(currentList.size > 0){
            getNextSite(current, currentList)
        }
    }






    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),  PackageManager.PERMISSION_GRANTED)
            Toast.makeText(this, "return case", Toast.LENGTH_LONG).show(); return
        }
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.isMyLocationEnabled = true;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this)
        mMap.uiSettings.isMapToolbarEnabled = true

        val perthLocation = LatLng(-31.90, 115.86)
        val perth = mMap.addMarker(
            MarkerOptions()
                .position(perthLocation)
                .draggable(true)
                .snippet("Population: 4,137,400")
                .flat(true)
        )

        val melbourneLatLng = LatLng(37.241179, 38.360650)
        val melbourne = mMap.addMarker(
            MarkerOptions()
                .position(melbourneLatLng)
                .title("Melbourne").snippet("Population: 4,137,400 dfghfdgh dfg hdfg hdfgh dfgh dfgh dfgh dfgh dfg hdfgh dfgh dfgh dfhd ")
        )
        melbourne.showInfoWindow()

        LatLongB = LatLngBounds.Builder()


        options = PolylineOptions()
        options.color(Color.BLUE)
        options.width(7f)

        // getPath(first, sydney, opera)
        for (i in listResult.indices) {
            if (i == 0) { mMap.addMarker(MarkerOptions().position(listResult[0]).title("START 1").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))) }
            else mMap.addMarker(MarkerOptions().position(listResult[i]).title((i + 1).toString()))
        }


        val thread = Thread {
            val parser: Parser = Parser()
            val options = PolylineOptions()
            val LatLongB = LatLngBounds.Builder()
            for (i in listResult.indices) {
                if (i + 1 >= listResult.size) break;
                val one = listResult[i]
                val two = listResult[i + 1]
                val urls = getURL(one, two)
                val result = URL(urls).readText()
                val stringBuilder: StringBuilder = StringBuilder(result)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject
                val routes = json.array<JsonObject>("routes")
                val points: JsonArray<JsonObject> =
                    routes!!["legs"]["steps"][0] as JsonArray<JsonObject>
                val polypts = points.flatMap { decodePoly(it.obj("polyline")?.string("points")!!) }
                options.add(listResult[i])
                LatLongB.include(listResult[i])
                for (point in polypts) {
                    options.add(point)
                    LatLongB.include(point)
                }
                options.add(listResult[i + 1])
                runOnUiThread {
                    val bounds = LatLongB.build()
                    mMap.addPolyline(options)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))

                }
            }
        }
        thread.start()


    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Current location:", Toast.LENGTH_LONG).show()
    }


}



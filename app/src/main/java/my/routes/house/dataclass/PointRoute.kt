package my.routes.house.dataclass
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class PointRoute (
    var id: String,
    var name: String,
    var description: String,
    var latitude: Double,
    var longitude: Double,
    var zipcode: String,
    var city: String,
    var address: String,
        ) : Parcelable
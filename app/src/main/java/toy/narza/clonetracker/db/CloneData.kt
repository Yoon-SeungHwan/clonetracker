package toy.narza.clonetracker.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["region", "ladder", "mode"])
class CloneData (
    @field:SerializedName("progress")
    val progress: Int,

    @field:SerializedName("region")
    val region: Int,

    @field:SerializedName("ladder")
    val ladder: Int,

    @field:SerializedName("hc")
    val mode: Int,

    @field:SerializedName("timestamped")
    val timestamped: Long,
) {
}
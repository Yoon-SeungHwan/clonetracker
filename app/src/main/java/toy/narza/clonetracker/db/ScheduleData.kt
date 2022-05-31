package toy.narza.clonetracker.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class ScheduleData(
    @field:SerializedName("thick")
    val thick: Long,
    @PrimaryKey
    val pk: Int = 1,
)
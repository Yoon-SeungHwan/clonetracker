package toy.narza.clonetracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThick(data: ScheduleData)

    @Query("SELECT thick FROM ScheduleData WHERE pk = 1")
    suspend fun selectThick(): Long
}
package toy.narza.clonetracker.db

import androidx.room.*

@Dao
interface CloneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllData(data: List<CloneData>)

    @Query("DELETE FROM CloneData")
    suspend fun deleteAll()

    @Query("SELECT * FROM CloneData WHERE ladder = ${LadderMode.Ladder}")
    suspend fun getLadder(): List<CloneData>

    @Query("SELECT * FROM CloneData WHERE ladder = ${LadderMode.Standard}")
    suspend fun getStandard(): List<CloneData>
}
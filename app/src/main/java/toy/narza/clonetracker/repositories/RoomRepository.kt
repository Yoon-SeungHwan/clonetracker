package toy.narza.clonetracker.repositories

import android.content.Context
import toy.narza.clonetracker.db.AppDataBase
import toy.narza.clonetracker.db.CloneDao
import toy.narza.clonetracker.db.CloneData

class RoomRepository(context: Context) {

    private val roomDatabase = AppDataBase.getInstance(context)
    private val cloneDao: CloneDao = roomDatabase.CloneDao()

    suspend fun insert(list: List<CloneData>) {
        cloneDao.insertAllData(list)
    }

    suspend fun getLadder(): List<CloneData> {
        return cloneDao.getLadder()
    }

    suspend fun getStandard(): List<CloneData> {
        return cloneDao.getStandard()
    }
}
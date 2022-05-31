package toy.narza.clonetracker.repositories

import android.content.Context
import android.util.Log
import toy.narza.clonetracker.db.AppDataBase
import toy.narza.clonetracker.db.CloneDao
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.db.ScheduleData

class RoomRepository(context: Context) {

    private val roomDatabase = AppDataBase.getInstance(context)
    private val cloneDao: CloneDao = roomDatabase.CloneDao()
    private val scheduleDao = roomDatabase.ScheduleDao()

    suspend fun insert(list: List<CloneData>) {
        cloneDao.insertAllData(list)
    }

    suspend fun getLadder(): List<CloneData> {
        return cloneDao.getLadder()
    }

    suspend fun getStandard(): List<CloneData> {
        return cloneDao.getStandard()
    }

    suspend fun insertThick(thick: Long) {
        scheduleDao.insertThick(ScheduleData(thick, 1) )
    }

    suspend fun getThick(): Long {
        return scheduleDao.selectThick()
    }
}
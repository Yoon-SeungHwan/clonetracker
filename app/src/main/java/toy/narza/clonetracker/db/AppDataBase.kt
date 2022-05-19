package toy.narza.clonetracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CloneData::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun CloneDao(): CloneDao

    companion object {
        private const val TAG = "AppDataBase"
        private var instance: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    "name")
                    .build()
            }
        }

    }
}
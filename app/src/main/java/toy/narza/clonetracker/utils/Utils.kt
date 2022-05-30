package toy.narza.clonetracker.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import toy.narza.clonetracker.R
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.network.constants.Region
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

object Utils {
    fun regionToString(context: Context, region: Int): String {
        return context.resources.getStringArray(R.array.region_title)
            .getOrElse(region) { "Unknown" }
    }

    fun progressToString(context: Context, progress: Int): String {
        return context.resources.getStringArray(R.array.step_messages).getOrElse(progress - 1) { "" }
    }

    fun timestampToString(context: Context, timestamp: Long): String {
        return SimpleDateFormat("HH:mm  dd/MMM", getCurrentLocale(context)).format(Date(timestamp * 1000))
    }

    private fun getCurrentLocale(context: Context): Locale {
        val config: Configuration = context.resources.configuration
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locales.get(0)
        } else {
            config.locale
        }
    }

}


fun <T: Any> printPretty(src: Any) {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val itemType = object : TypeToken<T>() {}.type
    Log.e("TEST", gson.toJson(src, itemType))
}

package toy.narza.clonetracker.ui.custom

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import toy.narza.clonetracker.R
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.utils.Utils

class ProgressCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private val titleView: TextView
    private val messageView: TextView
    private val tvLastUpdatedTime: TextView
    private val indicator: ImageView
    private val cardViewWidth: Int

    init {
        inflate(context, R.layout.step_progress_bar, this)
        val stepPoint1 = findViewById<StepPoint>(R.id.step_point_1)
        val stepPoint2 = findViewById<StepPoint>(R.id.step_point_2)
        val stepPoint3 = findViewById<StepPoint>(R.id.step_point_3)
        val stepPoint4 = findViewById<StepPoint>(R.id.step_point_4)
        val stepPoint5 = findViewById<StepPoint>(R.id.step_point_5)
        val stepPoint6 = findViewById<StepPoint>(R.id.step_point_6)

        Handler().postDelayed({
            stepPoint1.isSelected = true
        }, 2000)

        titleView = findViewById(R.id.title)
        messageView = findViewById(R.id.message)
        tvLastUpdatedTime = findViewById(R.id.tv_timestamp)
        indicator = findViewById(R.id.indicator)

        cardViewWidth = findViewById<MaterialCardView>(R.id.card_view).width
    }

    private fun setTitle(title: String) {
        titleView.text = title
    }

    private fun setMessage(message: String) {
        messageView.text = message
    }

    fun setData(data: CloneData) {
        val region: String = Utils.regionToString(context, data.region)
        val message: String = Utils.progressToString(context, data.progress)
        setTitle(region)
        setMessage(message)
        val width = findViewById<MaterialCardView>(R.id.card_view).width

        val indicatorWidthOffset = indicator.width / 2
        val widthPerStep = width.toFloat() / 7
        indicator.animate()
            .setDuration(1200L)
            .translationX(widthPerStep * data.progress - indicatorWidthOffset)

        tvLastUpdatedTime.text = Utils.timestampToString(context, data.timestamped)
        Log.e("TEST", "Mode-> ${data.mode}")
    }

}
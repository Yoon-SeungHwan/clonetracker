package toy.narza.clonetracker.ui.custom

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.animation.Animation.AnimationListener
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
    private val pointViews: Array<StepPoint>

    init {
        inflate(context, R.layout.step_progress_bar, this)
        pointViews = arrayOf(
            findViewById(R.id.step_point_1),
            findViewById(R.id.step_point_2),
            findViewById(R.id.step_point_3),
            findViewById(R.id.step_point_4),
            findViewById(R.id.step_point_5),
            findViewById(R.id.step_point_6),
        )

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
            .setListener(object: AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })

        tvLastUpdatedTime.text = Utils.timestampToString(context, data.timestamped)
    }

}
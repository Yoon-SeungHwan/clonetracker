package toy.narza.clonetracker.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import toy.narza.clonetracker.R

class StepPoint : ConstraintLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleArr: Int)
            : super(context, attrs, defStyleArr)
    init {
        inflate(context, R.layout.step_point, this)

    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        Log.e("TEST", "isSelected ${isSelected}")
    }
}
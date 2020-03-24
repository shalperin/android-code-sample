package com.blauhaus.android.redwood.components.abstractcalendar

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.blauhaus.android.redwood.R


class DayView : ImageView {

    // Note to self: get more muscle memory of the Code->Generate menu above.
    constructor(context: Context?) : super(context) {init(null)}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)  {init(attrs)}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) { init(attrs)}

    private var metVector = 0
    private var futureVector = 0
    private var metTodayVector =  0
    private var notMetVector = 0
    private var notMetTodayVector = 0

    private var d: ViewState =
        ViewState.Future()

    private val metTodayAnim = AnimationUtils.loadAnimation(context, android.R.anim.fade_out).apply{
        setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
               // setImageResource(metVector)
            }
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                setImageResource(metTodayVector)
                startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
            }
        })
    }

    private fun init(attrs:AttributeSet?) {
        attrs?.let {
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.DayView,
                0, 0
            ).apply {
                try {
                    metVector = getResourceId(R.styleable.DayView_metVector, R.drawable.ic_last4weeks_circle_met)
                    notMetVector = getResourceId(R.styleable.DayView_notMetVector, R.drawable.ic_last4weeks_x_mark)
                    notMetTodayVector = getResourceId(R.styleable.DayView_notMetTodayVector, R.drawable.ic_last4weeks_stroked_circle)
                    futureVector = getResourceId(R.styleable.DayView_futureVector, R.drawable.ic_last4weeks_circle_future)
                    metTodayVector = getResourceId(R.styleable.DayView_metTodayVector, R.drawable.ic_last4weeks_check_circle)
                } finally {
                    recycle()
                }
            }

        }
    }

    fun show (d: ViewState) {
        this.d = d
        show()
    }

    private fun show() {
        when(d) {
            is ViewState.Skipped -> {
                setImageResource(notMetVector)
            }
            is ViewState.Met -> {
                setImageResource(metVector)
            }
            is ViewState.MetToday -> {
                startAnimation(metTodayAnim)
            }
            is ViewState.DidntMeetYetToday -> {
                setImageResource(notMetTodayVector)
            }
            is ViewState.Future -> {
                setImageResource(futureVector)
            }
        }
    }

//    fun tintDrawable(drawableR_Id:Int, colorR_Id:Int):Drawable {
//        var d: Drawable? =
//            VectorDrawableCompat.create(resources, drawableR_Id, null)
//        d = DrawableCompat.wrap(d!!)
//        DrawableCompat.setTint(d, ContextCompat.getColor(context, colorR_Id))
//        return d
//    }

    sealed class ViewState() {
        class Skipped() : ViewState()
        class Met(): ViewState()
        class MetToday(): ViewState()
        class DidntMeetYetToday(): ViewState()
        class Future(): ViewState()
   }

}
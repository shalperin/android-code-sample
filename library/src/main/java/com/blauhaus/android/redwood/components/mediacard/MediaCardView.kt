package com.blauhaus.android.redwood.components.mediacard

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.blauhaus.android.redwood.R
import kotlinx.android.synthetic.main.view_media_card.view.*

class MediaCardView : FrameLayout {

    var image = R.drawable.placeholder
        set(value) {
            field = value
            invalidateCustom()
        }
    var preTitle = ""
        set(value) {
            field = value
            invalidateCustom()
        }
    var title = ""
        set(value) {
            field = value
            invalidateCustom()
        }

    var description = ""
        set(value) {
            field = value
            invalidateCustom()
        }

    var onPlay:(() -> Unit)? = null
        set(value) {
            field = value
            invalidateCustom()
        }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.view_media_card, this)
        invalidateCustom()
    }

    fun invalidateCustom() {
        _image.setImageResource(image)
        _pretitle.text = preTitle
        _title.text = title
        _description.text = description

        if (onPlay == null) {
            _playButton.visibility = GONE
        } else {
            _playButton.visibility = View.VISIBLE
            _playButton.setOnClickListener {
                _ -> onPlay!!() }
        }
    }
}

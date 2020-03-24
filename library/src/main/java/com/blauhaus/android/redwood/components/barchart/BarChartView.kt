package com.blauhaus.android.redwood.components.barchart

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat.getColor

import com.blauhaus.android.redwood.R


class BarChartView: View {

    constructor(context: Context?) : super(context) {
        init(null)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) { init(attrs) }


    //These are calculated - Modifications will be overwritten.
    private lateinit var barPaint: Paint
    private lateinit var gridLinesPaint: Paint
    private lateinit var labelStemPaint: Paint
    private lateinit var labelTextPaint: Paint
    private lateinit var labelBackgroundPaint: Paint
    private var barWidth = 0f
    private var plotArea = Rect()

    //Attrs to twiddle.
    // These also can be exposed, see barColor et al below.
    private var barPadding = 8f
    private var gridLineStrokeSize = 5f
    private var graphPadding = 100f
    private var labelPosition = 40f
    private var labelTextPosition = 12f
    private var labelTextSize = 30f
    private var labelHeight = 70f
    private var labelWidth = 225f

    var model: List<Pair<Float,String>>? = null
        set(value) {
            if (value != null) {
                if (value.isEmpty()) {
                    throw(Exception("Model can't be 0 length."))
                }
            }
            labelIndex = null
            field = value
            invalidate()
        }

   var listener: BarChartViewListener? = null
    get() {
        if (field == null) {
            throw Exception("Missing BarChartFragmentListener")
        }
        return field
    }

    var labelIndex:Int? = null
     set(value) {
        field = value
        invalidate()
     }

    var barColor: Int = getColor(context, R.color.defaultBarColor)
    set(value) {
        field = value
        initPaints()
    }
    var gridLineColor = getColor(context, R.color.defaultGridLineColor)
        set(value) {
            field = value
            initPaints()
        }
    var labelColor = getColor(context, R.color.defaultLabelColor)
        set(value) {
            field = value
            initPaints()
        }
    var textColor = getColor(context, R.color.defaultTextColor)
        set(value) {
            field = value
            initPaints()
        }

    private fun init(attrs: AttributeSet?) {
        attrs?.let {
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BarChartView,
                0,0
            ).apply{
                try {
                    barColor = getColor(R.styleable.BarChartView_barColor, getColor(context, R.color.defaultBarColor))
                    gridLineColor = getColor(R.styleable.BarChartView_gridLineColor, getColor(context, R.color.defaultGridLineColor))
                    labelColor = getColor(R.styleable.BarChartView_labelColor, getColor(context, R.color.defaultLabelColor))
                    textColor = getColor(R.styleable.BarChartView_textColor, getColor(context, R.color.defaultTextColor))
                    gridLineStrokeSize = getFloat(R.styleable.BarChartView_gridLineStrokeSize, 5f)
                    barPadding = getFloat(R.styleable.BarChartView_barPadding, 8f)
                    graphPadding = getFloat(R.styleable.BarChartView_graphPadding, 100f)
                    labelPosition = getFloat(R.styleable.BarChartView_labelPosition, 40f)
                    labelTextPosition = getFloat(R.styleable.BarChartView_labelTextPosition, 12f)
                    labelTextSize = getFloat(R.styleable.BarChartView_labelTextSize, 30f)
                    labelHeight = getFloat(R.styleable.BarChartView_labelHeight, 70f)
                    labelWidth = getFloat(R.styleable.BarChartView_labelWidth, 225f)
                } finally {
                    recycle()
                }
            }
        }

        initPaints()
    }

    private fun initPaints() {
        barPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        barPaint.color = barColor
        barPaint.style = Paint.Style.FILL

        gridLinesPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        gridLinesPaint.color = gridLineColor
        gridLinesPaint.style = Paint.Style.STROKE
        gridLinesPaint.strokeWidth = gridLineStrokeSize
        gridLinesPaint.pathEffect = DashPathEffect(floatArrayOf(35f, 5f), 0f)

        labelStemPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        labelStemPaint.color = labelColor
        labelStemPaint.style = Paint.Style.STROKE
        labelStemPaint.strokeWidth = gridLineStrokeSize

        labelBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        labelBackgroundPaint.color = labelColor
        labelBackgroundPaint.style = Paint.Style.STROKE
        labelBackgroundPaint.strokeCap = Paint.Cap.ROUND
        labelBackgroundPaint.strokeWidth = labelHeight

        labelTextPaint = Paint()
        labelTextPaint.color = textColor
        labelTextPaint.textAlign = Paint.Align.CENTER
        labelTextPaint.textSize = labelTextSize
    }

    override fun onSizeChanged(
        viewWidth: Int,
        viewHeight: Int,
        oldw: Int,
        oldh: Int
    ) {
        plotArea.left = graphPadding.toInt()
        plotArea.right = viewWidth - graphPadding.toInt()
        plotArea.top = graphPadding.toInt()
        plotArea.bottom = viewHeight - graphPadding.toInt()
    }


    private fun recomputeBarWidth() {
        val modelSize = model?.size ?: 0
        barWidth = if (modelSize != 0) {
            (plotArea.width() - (model!!.size + 1) * barPadding) / modelSize
        } else {
            0f
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (model != null) {
            recomputeBarWidth()
            drawGrid(canvas)
            drawLabel(canvas)
            drawBars(canvas)
        } else {
            drawGrid(canvas)
        }
    }


    private fun drawGrid(canvas:Canvas) {
        drawGridLine(canvas, .5f * gridLineStrokeSize + graphPadding)
        drawGridLine(canvas, plotArea.bottom/2 + graphPadding/2)
        drawGridLine(canvas, plotArea.bottom - .5f * gridLineStrokeSize)
    }


    private fun drawBars(canvas:Canvas) {
        val bar = RectF()

        for (i in model!!.indices) {
            var topY = 0f
            if (model!!.map{it.first}.max() != 0f) {
                topY = plotArea.bottom - plotArea.height() * model!!.map{it.first}.get(i) / model!!.map{it.first}.max()!!
            }
            bar.left = barLeftX(i)
            bar.right = barRightX(i)
            bar.top = topY
            bar.bottom = plotArea.bottom.toFloat()
            canvas.drawRect(bar, barPaint)
        }
    }


    private fun drawGridLine(canvas: Canvas, y:Float) {
        val leftX = graphPadding + barPadding
        val rightX = plotArea.width() + graphPadding
        val path = Path()
        path.moveTo(leftX, y)
        path.lineTo(rightX, y)
        canvas.drawPath(path, gridLinesPaint)
    }


    private fun whichBarClicked(x: Float): Int? {
        if (model != null) {
            for (i in model!!.indices) { //yeah, I know.
                if (x  in barLeftX(i)..barRightX(i)) {
                    return i
                }
            }
        }
        return null
    }


    private fun barLeftX(barIndexInModel: Int):Float {
        return barPadding * (barIndexInModel + 1) + barWidth * barIndexInModel + graphPadding
    }


    private fun barRightX(barIndexInModel:Int):Float {
        return barLeftX(barIndexInModel) + barWidth
    }


    private fun barCenterX(barIndexInModel: Int):Float {
        return barLeftX(barIndexInModel) + .5f * barWidth
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null)  {
        val barIndex = whichBarClicked(event.x)
            if (barIndex != null) {
                listener?.onActiveItemSet(barIndex)
            } else {
                listener?.onActiveItemSet(null)
            }
        }
        return true
    }


    private fun drawLabel(canvas:Canvas) {
        if (labelIndex != null) {
            val x = barCenterX(labelIndex!!)

            val labelBackground = Path()
            labelBackground.moveTo(x - .5f * labelWidth, labelPosition)
            labelBackground.lineTo(x + .5f * labelWidth, labelPosition)

            val labelStem = Path()
            labelStem.moveTo(x, labelPosition)
            labelStem.lineTo(x, plotArea.bottom.toFloat())

            val labelText = model!!.getOrNull(labelIndex!!)?.second ?: ""

            canvas.drawPath(labelStem, labelStemPaint)
            canvas.drawPath(labelBackground,labelBackgroundPaint)
            canvas.drawText(labelText, x, labelPosition + labelTextPosition, labelTextPaint)
        }
    }

}
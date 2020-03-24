package com.blauhaus.android.redwood.components.barchart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.blauhaus.android.redwood.R
import kotlinx.android.synthetic.main.fragment_bar_chart.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_ID = "id_param"
const val PROP_BAR_COLOR = "barColor"
const val PROP_GRID_LINE_COLOR = "gridLineColor"
const val PROP_LABEL_COLOR = "labelColor"
const val PROP_TEXT_COLOR = "textColor"

class BarChartFragment : Fragment(), BarChartViewListener {
    private val model: BarChartViewModel by viewModel()
    private lateinit var id: String

    companion object {
        fun newInstance(id: String) = BarChartFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_ID, id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (!it.containsKey(ARG_ID)) {
                throw Exception("ID is required.")
            } else {
                id = it.getString(ARG_ID)!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bar_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bar_chart_view.listener = this

        model.data(id).observe(this, Observer {
            bar_chart_view.model = it
        })

        model.activeItem(id).observe(this, Observer {index ->
            bar_chart_view.labelIndex = index
        })

        model.intProp(id, PROP_BAR_COLOR).observe(this, Observer {
            //TODO : why is 'it' type Int!?
            bar_chart_view.barColor = it
        })
        model.intProp(id, PROP_GRID_LINE_COLOR).observe(this, Observer {
            bar_chart_view.gridLineColor = it
        })
        model.intProp(id, PROP_LABEL_COLOR).observe(this, Observer {
            bar_chart_view.labelColor = it
        })
        model.intProp(id, PROP_TEXT_COLOR).observe(this, Observer {
            bar_chart_view.textColor = it
        })

    }

    override fun onActiveItemSet(index: Int?) {
        model.activeItem(id).postValue(index)
    }

}

interface BarChartViewListener {
    fun onActiveItemSet(index:Int?)
}

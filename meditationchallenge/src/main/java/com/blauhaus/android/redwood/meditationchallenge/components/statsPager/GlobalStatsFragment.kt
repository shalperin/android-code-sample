package com.blauhaus.android.redwood.meditationchallenge.components.statspager


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blauhaus.android.redwood.meditationchallenge.R
import kotlinx.android.synthetic.main.fragment_global_stats.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*


class GlobalStatsFragment : Fragment() {
    val model by viewModel<StatsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.globalAverage.observe(this, Observer {
            global_average.text = NumberFormat.getNumberInstance(Locale.US).format(it)
        })
        model.globalParticipants.observe(this, Observer {
            global_participants.text = NumberFormat.getNumberInstance(Locale.US).format(it)
        })
        model.globalMinutes.observe(this, Observer {
            mindful_minutes.text = NumberFormat.getNumberInstance(Locale.US).format(it)
        })


    }
}

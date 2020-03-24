package com.blauhaus.android.redwood.meditationchallenge.components.statspager


import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.blauhaus.android.redwood.components.barchart.BarChartFragment
import com.blauhaus.android.redwood.components.barchart.BarChartViewModel
import com.blauhaus.android.redwood.components.abstractcalendar.LastFourWeeksFragment
import com.blauhaus.android.redwood.components.abstractcalendar.LastFourWeeksViewModel
import com.blauhaus.android.redwood.meditationchallenge.R
import kotlinx.android.synthetic.main.fragment_stats_pager.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class StatsFragment : Fragment() {
    val barChartViewModel by viewModel<BarChartViewModel>()
    val last4ViewModel by viewModel<LastFourWeeksViewModel>()
    val model by viewModel<StatsViewModel>()

    private val BAR_CHART_ID = "1"
    private val LAST4_WEEKS_POSITION_IN_VIEW_PAGER = 0
    private val frag1 = LastFourWeeksFragment()
    private val frag2 = BarChartFragment.newInstance(BAR_CHART_ID)
    private val frag3 = GlobalStatsFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats_pager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vpAdapter =
            MyPagerAdapter(
                fragmentManager!!
            )
        vpAdapter.set(frag1, frag2, frag3)
        vpPager.adapter = vpAdapter
        vpPager.addOnPageChangeListener(viewPagerListener)

        model.lastFourWeeksBackingModel.observe( this, Observer {
            last4ViewModel.dayData.postValue(it)
        })

        model.barChartBackingModel.observe ( this, Observer {
            barChartViewModel.data(BAR_CHART_ID).postValue(it)
        })

        model.totalDaysMeditated.observe (this, Observer {
            achievedDayCount.text = it.toString()
        })

        model.averageMinutesMeditated.observe (this, Observer {
            achievedAverage.text = it.toString()
        })

        model.medalClass.observe (this, Observer {

            val drawable = when(it.first) {
                is StatsViewModel.MedalClass.Gold -> R.drawable.gold
                is StatsViewModel.MedalClass.Bronze -> R.drawable.bronze
                is StatsViewModel.MedalClass.Silver -> R.drawable.silver
                else -> null
            }
            if (drawable != null) {
                medal.background = ResourcesCompat.getDrawable(resources, drawable, null)
            }

            when (it.second) {
                is StatsViewModel.MedalProgress.OnTrack -> {
                    onTrackMedalText.visibility = View.VISIBLE
                    gotMedalText.visibility = View.GONE
                    medal.visibility = View.VISIBLE
                }
                is StatsViewModel.MedalProgress.Got -> {
                    onTrackMedalText.visibility = View.GONE
                    gotMedalText.visibility = View.VISIBLE
                    medal.visibility = View.VISIBLE
                }
                is StatsViewModel.MedalProgress.No -> {
                    onTrackMedalText.visibility = View.GONE
                    gotMedalText.visibility = View.GONE
                    medal.visibility = View.GONE
                }
            }
        })

    }

    private val viewPagerListener = object: ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            if (position == LAST4_WEEKS_POSITION_IN_VIEW_PAGER) {
                lastFourWeeksDecoration.visibility = View.VISIBLE
            } else {
                lastFourWeeksDecoration.visibility = View.GONE
            }

            val dots = listOf(dot0, dot1, dot2)
            dots.forEachIndexed{ index, view ->
                var color = ContextCompat.getColor(context!!, R.color.color_grey_1)
                if (index == position) {
                    color = ContextCompat.getColor(context!!, R.color.color_error)
                }
                ImageViewCompat.setImageTintList( view, ColorStateList.valueOf(color))
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {}
    }

    class MyPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val COUNT = 3
        private lateinit var frag1:Fragment
        private lateinit var frag2:Fragment
        private lateinit var frag3:Fragment

        fun set(frag1: Fragment, frag2:Fragment, frag3: Fragment) {
            this.frag1 = frag1
            this.frag2 = frag2
            this.frag3 = frag3
        }

        override fun getCount(): Int {
            return COUNT
        }

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> frag1
                1-> frag2
                else -> frag3
            }
        }
    }

}


package com.blauhaus.android.redwood.meditationchallenge.components.mycircle


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blauhaus.android.redwood.components.friendcircle.MyCircleView
import com.blauhaus.android.redwood.meditationchallenge.R
import kotlinx.android.synthetic.main.fragment_my_circle.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyCircleFragment : Fragment() {
    val model by viewModel<MyCircleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_circle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvAdapter = RVAdapter(listOf())
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(context)

        model.myCircle().observe(this, Observer {
            val adapted = it.mapIndexed {i, d ->
                AdapterData(
                    if(i == 0) {getString(R.string.you)} else {d.fname},
                    d.fname[0].toString(),
                    d.didCompleteChallenge,
                    getString(R.string.my_circle_message, d.days.toString(), d.avg.toString()),
                    d.progress
                )
            }
            rvAdapter.addData(adapted)
        })
    }

    data class AdapterData(val name: String, val firstLetter:String, val didComplete:Boolean, val message: String, val progress: Int)

    class RVAdapter(private var data: List<AdapterData> )
        : RecyclerView.Adapter<RVAdapter.MyViewHolder>() {
        class MyViewHolder (val view: MyCircleView) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = MyCircleView(parent.context)
            view.layoutParams =
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            return MyViewHolder(view)
        }


        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data = data[position]
            holder.view.name = data.name
            holder.view.message =data.message
            holder.view.didCompleteChallenge = data.didComplete
            holder.view.progress = data.progress
            holder.view.firstLetter = data.firstLetter
        }

        override fun getItemCount(): Int {
            return data.count()
        }

        fun addData(data: List<AdapterData>) {
            this.data = data
            //TODO note to self - I don't use RVs that frequently,
            // I need to do some research on updating them... I'm pretty sure
            // this approach might not always be optimal in the case where you want to replace one
            // row and have the right animation.
            notifyDataSetChanged()
        }
    }

}

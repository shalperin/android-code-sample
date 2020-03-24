package com.blauhaus.android.redwood.components.barchart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BarChartViewModel : ViewModel() {
   private var dataLdMap = mutableMapOf<String, MutableLiveData<List<Pair<Float,String>>>>()
   private  var activeItemLdMap = mutableMapOf<String, MutableLiveData<Int>>()
   private var intPropLdMap = mutableMapOf<String, MutableMap<String, MutableLiveData<Int>>>()

    fun data(id:String): MutableLiveData<List<Pair<Float,String>>> {
        if (! dataLdMap.containsKey(id)) {
            dataLdMap[id] = MutableLiveData()
        }
        return dataLdMap[id]!!
    }

    fun activeItem(id:String): MutableLiveData<Int>{
        if (! activeItemLdMap.containsKey(id)) {
            activeItemLdMap[id] = MutableLiveData()
        }
        return activeItemLdMap[id]!!
    }

    fun intProp(id:String, name: String): MutableLiveData<Int> {
        if (!intPropLdMap.containsKey(id)) {
            intPropLdMap[id] = mutableMapOf()
        }
        if (!intPropLdMap[id]!!.containsKey(name)) {
            intPropLdMap[id]!![name] = MutableLiveData()
        }
        return intPropLdMap[id]!![name]!!
    }

    fun setIntProp(id: String, name: String, value: Int) {
        intProp(id, name).postValue(value)
    }

    fun setData(id:String, data: List<Pair<Float,String>>) {
        this.data(id).postValue(data)
    }
}

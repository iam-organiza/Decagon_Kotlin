package com.example.week_4_task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class SliderAdapter(val viewPager2: ViewPager2, val imgList: ArrayList<SliderItem>): RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(var v: View): RecyclerView.ViewHolder(v) {
        val img = v.findViewById<ImageView>(R.id.imageSlider)
        val txt = v.findViewById<TextView>(R.id.slider_item_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return SliderViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val listImg = imgList[position]
        holder.img.setImageResource(listImg.img)
        holder.txt.text = listImg.txt

        if (position == imgList.size - 2) {
            viewPager2.post(run)
        }
    }

    private val run = Runnable {
        imgList.addAll(imgList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = imgList.size
}
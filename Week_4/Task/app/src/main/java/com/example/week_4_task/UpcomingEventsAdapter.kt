package com.example.week_4_task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UpcomingEventsAdapter(
    var events: List<UpcomingEvent>
) : RecyclerView.Adapter<UpcomingEventsAdapter.UpcomingEventHolder>() {

    inner class UpcomingEventHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.upcoming_event_img)
        var title = itemView.findViewById<TextView>(R.id.upcoming_event_title)
        var eventDate = itemView.findViewById<TextView>(R.id.upcoming_event_date)
        var eventDateLeft = itemView.findViewById<TextView>(R.id.upcoming_event_days_left)
        var containerDrawable = itemView.findViewById<LinearLayout>(R.id.upcoming_event_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingEventHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.upcoming_event_view, parent, false)
        return UpcomingEventHolder(view)
    }

    override fun onBindViewHolder(holder: UpcomingEventHolder, position: Int) {
        holder.image.setImageResource(events[position].image)
        holder.title.text = events[position].title
        holder.eventDate.text = events[position].eventDate
        holder.eventDateLeft.text = events[position].eventDaysLeft
        holder.containerDrawable.setBackgroundResource(events[position].containerDrawable)
    }

    override fun getItemCount(): Int = events.size

}
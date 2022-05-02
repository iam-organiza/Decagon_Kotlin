package com.example.week_5_task

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomArrayAdapter(var _context: Context, var layoutID: Int, var gender: List<String>): ArrayAdapter<String>(_context, layoutID, gender) {

    override fun getCount(): Int {
        return gender.size
    }

    override fun getItem(position: Int): String {
        return gender[position]
    }

    override fun getItemId(position: Int): Long {
        return gender.indexOf(gender[position]).toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (_context as Activity).layoutInflater
            convertView = inflater.inflate(layoutID, parent, false)
        }

        try {
            val view = getItem(position)
            val textView = convertView!!.findViewById<View>(R.id.spinnerViewTemplate) as TextView

            if (position == 0) {
                textView.setTextColor(Color.parseColor("#666666"))
//                convertView?.visibility = View.GONE
            } else {
//                convertView?.visibility = View.VISIBLE
            }

            textView.text = gender[position]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }
}

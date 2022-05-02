package com.example.implementation2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.implementation2.R
import com.example.implementation2.models.ContactModel

class ContactAdapter(
    private val contacts: ArrayList<ContactModel>
): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // How to use view binding within an adapter.
        var contactName: TextView = view.findViewById(R.id.contact_list_item_name)
        var contactNumber: TextView = view.findViewById(R.id.contact_list_item_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_xml, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.contactName.text = context.getString(R.string.contact_list_item_name_placeholder).format(contacts[position].name)
        holder.contactName.text = contacts[position].name
//        holder.contactNumber.text = context.getString(R.string.contact_list_item_number_placeholder).format(contacts[position].number)
        holder.contactNumber.text = contacts[position].number
    }

    override fun getItemCount(): Int = contacts.size

}

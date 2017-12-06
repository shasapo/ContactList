package com.example.shasapo.contactslist.Adapter

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shasapo.contactslist.Entity.Contact
import com.example.shasapo.contactslist.R
import kotlinx.android.synthetic.main.item_contact.view.*
import java.util.*
import java.util.zip.Inflater


class ContactsAdapter: RecyclerView.Adapter<ContactViewHolder>() {

    var listOfContacts = emptyList<Contact>()

    override fun onBindViewHolder(holder: ContactViewHolder?, position: Int) {
        holder?.bind(listOfContacts[position])
    }

    override fun getItemCount(): Int = listOfContacts.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_contact, parent, false)
        return  ContactViewHolder(view)
    }

}

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(contact : Contact){
        itemView.tvNama.text = """${contact.firstName} ${contact.lastName}"""
        itemView.tvEmail.text = contact.email
        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        itemView.image.setBackgroundColor(color)
    }
}

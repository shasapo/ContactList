package com.example.shasapo.contactslist.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shasapo.contactslist.Entity.Contact
import com.example.shasapo.contactslist.R
import kotlinx.android.synthetic.main.item_contact.view.*


interface DaoAction {
    fun onDelete(contact: Contact)
    fun onEdit(idContact: Int)
}

class ContactsAdapter(private val daoAction: DaoAction): RecyclerView.Adapter<ContactViewHolder>() {

    var listOfContacts = emptyList<Contact>()

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = listOfContacts[position]
        holder.itemView.tvNama.text = """${contact.firstName} ${contact.lastName}"""
        holder.itemView.tvEmail.text = contact.email


        holder.itemView.btnEdit.setOnClickListener{
            daoAction.onEdit(contact.cId)
        }

        holder.itemView.btnDelete.setOnClickListener {
            daoAction.onDelete(contact)
        }
    }

    override fun getItemCount(): Int = listOfContacts.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_contact, parent, false)
        return  ContactViewHolder(view)
    }

}

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

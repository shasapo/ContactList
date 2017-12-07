package com.example.shasapo.contactslist.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.shasapo.contactslist.Entity.Contact
import com.example.shasapo.contactslist.R
import kotlinx.android.synthetic.main.activity_insert_update_contact.*

class InsertUpdateContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_update_contact)

        if(intent.extras != null ) {
            btnAdd.text = "Update"
            val id = intent.extras.getInt("id")
            val contact = Contact(firstName = "Arif", lastName = "R. Hakim", email = "arif@bbmtek.com")

            etFirstName.setText(contact.firstName)
            etLastName.setText(contact.lastName)
            etEmail.setText(contact.email)
        }


        btnAdd.setOnClickListener {
            val contact = Contact(
                    firstName = etFirstName.text.toString(),
                    lastName = etLastName.text.toString(),
                    email = etEmail.text.toString()
            )
            if (intent.extras == null) {
                //db.add
                Toast.makeText(this,"Success add contact",Toast.LENGTH_SHORT).show()
            } else {
                //db.update
                Toast.makeText(this,"Success update contact",Toast.LENGTH_SHORT).show()
            }
            finish()

        }



    }
}
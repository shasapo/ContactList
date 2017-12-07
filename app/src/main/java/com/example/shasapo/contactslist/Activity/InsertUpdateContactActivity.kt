package com.example.shasapo.contactslist.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.shasapo.contactslist.Database.MyDatabase
import com.example.shasapo.contactslist.Entity.Contact
import com.example.shasapo.contactslist.R
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_insert_update_contact.*

class InsertUpdateContactActivity : AppCompatActivity() {

    private lateinit var db : MyDatabase
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_update_contact)

        db = MyDatabase.getDatabase(this)

        if(intent.extras != null ) {
            btnAdd.text = "Update"
            val id = intent.extras.getInt("id")

            db.contactDao().getContactById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ contact ->
                        this.contact = contact
                        etFirstName.setText(contact.firstName)
                        etLastName.setText(contact.lastName)
                        etEmail.setText(contact.email)
                    }, {
                        Toast.makeText(this,"Failed to get contact",Toast.LENGTH_SHORT).show()
                        Log.e("sasa","err $it")
                    })
        }


        btnAdd.setOnClickListener {
            if (intent.extras == null) {
                //db.add
                val contact = Contact(
                        firstName = etFirstName.text.toString(),
                        lastName = etLastName.text.toString(),
                        email = etEmail.text.toString()
                )
                Completable.fromAction{ db.contactDao().insertContact(contact) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Toast.makeText(this,"Success add contact",Toast.LENGTH_SHORT).show()
                        },
                                {
                                    Toast.makeText(this,"Error add contact",Toast.LENGTH_SHORT).show()})

            } else {
                //db.update
                contact.apply {
                    firstName = etFirstName.text.toString()
                    lastName = etLastName.text.toString()
                    email = etEmail.text.toString()
                }

                Completable.fromAction{ db.contactDao().updateContact(contact) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Toast.makeText(this,"Success update contact",Toast.LENGTH_SHORT).show()}
                                ,{

                            Toast.makeText(this,"Error update contact",Toast.LENGTH_SHORT).show()
                        })


            }
            finish()
        }



    }
}
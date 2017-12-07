package com.example.shasapo.contactslist.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.shasapo.contactslist.Adapter.ContactsAdapter
import com.example.shasapo.contactslist.Adapter.DaoAction
import com.example.shasapo.contactslist.Database.MyDatabase
import com.example.shasapo.contactslist.Entity.Contact
import com.example.shasapo.contactslist.R
import com.facebook.stetho.Stetho
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DaoAction {
    override fun onDelete(contact: Contact) {

        Completable.fromAction{db.contactDao().deleteContact(contact)}
            .andThen(Single.fromCallable { db.contactDao().getAllContact() })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                (rvContactList.adapter as ContactsAdapter).listOfContacts = it.toMutableList()
                rvContactList.adapter.notifyDataSetChanged()
                Toast.makeText(this,"Success deleted contact", Toast.LENGTH_SHORT).show()
            },{
                Toast.makeText(this,"Failed deleted contact", Toast.LENGTH_SHORT).show()
                Log.e("sasa", "err delete $it")
            })
    }

    override fun onEdit(idContact: Int) {
        startActivity(Intent(this,InsertUpdateContactActivity::class.java).putExtra("id",idContact))
    }

    private lateinit var contactsAdapter: ContactsAdapter
    private var contactListt = mutableListOf<Contact>()
    private lateinit var db : MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)

        db = MyDatabase.getDatabase(this)


        contactsAdapter = ContactsAdapter( this)
        rvContactList.layoutManager = LinearLayoutManager(this)
        rvContactList.adapter = contactsAdapter


        fabAddContact.setOnClickListener {
            val intent = Intent(this, InsertUpdateContactActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        Single.fromCallable { db.contactDao().getAllContact() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    contactsAdapter.listOfContacts = it.toMutableList()
                    rvContactList.adapter.notifyDataSetChanged()
                },{
                    Log.e("sasa","err getalldata $it`")
                })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_add_dummy, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.title) {
            "Add Dummy" -> {
                addDummyContact()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addDummyContact() {

        val contactList = mutableListOf(
                Contact(firstName = "Arif", lastName = "R. Hakim", email = "arif@bbmtek.com"),
                Contact(firstName = "Dino", lastName = "Dwiyaksa", email = "dino@bbmtek.com"),
                Contact(firstName = "Ayu", lastName = "Nindya", email = "ayu@bbmtek.com")
        )
        Single.just(contactList)
            .flatMap {
                db.contactDao().insertAll(it)
                Single.just(db.contactDao().getAllContact())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                (rvContactList.adapter as ContactsAdapter).listOfContacts = it.toMutableList()
                rvContactList.adapter.notifyDataSetChanged()
            }, {
                Toast.makeText(this,"Error add dummy", Toast.LENGTH_SHORT).show()
                Log.e("sasa","err = $it")
            })
    }
}


package com.example.shasapo.contactslist.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.shasapo.contactslist.Adapter.ContactsAdapter
import com.example.shasapo.contactslist.Entity.Contact
import com.example.shasapo.contactslist.R
import com.facebook.stetho.Stetho
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var contactsAdapter: ContactsAdapter
    private var contactListt = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stetho.initializeWithDefaults(this)

        contactsAdapter = ContactsAdapter(this)
        contactsAdapter.listOfContacts = contactListt

        rvContactList.layoutManager = LinearLayoutManager(this)
        rvContactList.adapter = contactsAdapter
        rvContactList.adapter.notifyDataSetChanged()



        fabAddContact.setOnClickListener {
            val intent = Intent(this, InsertUpdateContactActivity::class.java)
            startActivity(intent)
        }

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

//        val contactList = ArrayList<Contact>()
//        for (i in 1..3) {
//            val user = Contact(
//                    firstName = getRandomString(randBetween(3,7)),
//                    lastName = getRandomString(randBetween(4,8)),
//                    email = getRandomString(randBetween(5,10)) + "@bbmtek.com"
//            )
//            contactList.add(user)
//        }

        Observable.just(contactList)
            .flatMapIterable { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                contactListt.add(it)
                rvContactList.adapter.notifyDataSetChanged()
                Log.e("sasa", "sukses add ${it.firstName}")
                //db?.contactDao()?.insertContact(it)
            }, {
                Log.e("sasa", "Error add all: $it" )
            })
    }


    fun randBetween(start: Int, end: Int): Int {
        return start + Math.round(Math.random() * (end - start)).toInt()
    }

    fun getRandomString(sizeOfRandomString: Int): String {
        val ALLOWED_CHARACTERS = "qwertyuiopasdfghjklzxcvbnm"
        val random = Random()
        val sb = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString)
            sb.append(ALLOWED_CHARACTERS.get(random.nextInt(ALLOWED_CHARACTERS.length)))
        return sb.toString()
    }
}


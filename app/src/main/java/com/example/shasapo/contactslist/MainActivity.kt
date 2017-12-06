package com.example.shasapo.contactslist

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.shasapo.contactslist.Activity.InsertUpdateContactActivity
import com.example.shasapo.contactslist.Adapter.ContactsAdapter
import com.example.shasapo.contactslist.Database.MyDatabase
import com.example.shasapo.contactslist.Entity.Contact
import com.facebook.stetho.Stetho
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stetho.initializeWithDefaults(this)


        var contactListt = mutableListOf(
                Contact(1,"Eat","sdf","High"),
                Contact(1,"Sleep","dahd","Medium"),
                Contact(1,"Repeat","sfjhksd","Low")
        )

        val contact1 = Contact(2,"shasa","sdf","High")

        contactsAdapter = ContactsAdapter()
        contactsAdapter.listOfContacts = contactListt

        rvContactList.layoutManager = LinearLayoutManager(this)
        rvContactList.adapter = contactsAdapter
        rvContactList.adapter.notifyDataSetChanged()


        val db = MyDatabase.getDatabase(this)

        Observable.just(contactListt)
                .flatMapIterable { it }
                .observeOn(Schedulers.io())
                .subscribe({
                    db.contactDao().insertContact(it)
                }, {

                })


        fabAddContact.setOnClickListener {

            val intent = Intent(this, InsertUpdateContactActivity::class.java)
            startActivity(intent)

//            db.contactDao().insertContact(contact1)
            Single.fromCallable { db.contactDao().insertContact(contact1) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( {
                        contactListt.add(contact1)

                        rvContactList.adapter.notifyDataSetChanged()

                        Log.e("shasapo", "berhasil add")
                    }, {
                        Log.e("shasapo", "gagal add cause || it = $it")

                    })
        }


//
//
//        db.contactDao().getAllContact().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    Log.e("shasa","sukses")
//                },{ Log.e("shasa", "failed")})

//
//
//
//
//
//                })

//        Single.just(db?.contactDao()?.getAllContact())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe( {
//                    contactListt.add(contact1)
//
//                    rvContactList.adapter.notifyDataSetChanged()
//
//                    Log.e("shasapo", "berhasil add")
//                }, {
//
//                    Log.e("shasapo", "gagal add cause $")
//
//                })


    }

//    class insert(contact: Contact) : AsyncTask<Contact,Int,Void>() {
//
//        var context : Context? = null
//        var db : MyDatabase ? = null
//
//        fun runDBOnOtherThread(context: Context) {
//            this.context = context
//        }
//
//        override fun doInBackground(vararg p0: Contact) {
//            db = MyDatabase.getDatabase(context!!)
//
//            db?.contactDao()?.insertContact(p0[0])
//
//        }
//    }
}


package com.example.shasapo.contactslist.Database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.shasapo.contactslist.Dao.ContactDao
import com.example.shasapo.contactslist.Entity.Contact


@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    abstract fun contactDao() : ContactDao

    companion object {
        private val DB_NAME = "contact_db"
        private var dbInstance : MyDatabase? = null

        fun getDatabase(context: Context) : MyDatabase? {
            if(dbInstance == null) {
                dbInstance = Room.databaseBuilder<MyDatabase>(context.applicationContext, MyDatabase::class.java, DB_NAME).build()
            }
            return dbInstance
        }
    }

}

package com.example.shasapo.contactslist.Database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.example.shasapo.contactslist.Dao.ContactDao
import com.example.shasapo.contactslist.Entity.Contact


@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    abstract fun contactDao() : ContactDao

    companion object {
        val DB_NAME = "contact_db"


        fun getDatabase(context: Context) : MyDatabase = Room.databaseBuilder<MyDatabase>(context.applicationContext, MyDatabase::class.java, DB_NAME).build()

        val MIGRATION_1_2 : Migration = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Contact ")
            }

        }
    }

}
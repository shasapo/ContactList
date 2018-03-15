package com.example.shasapo.contactslist.Database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.example.shasapo.contactslist.Dao.ContactDao
import com.example.shasapo.contactslist.Entity.Contact
import com.example.shasapo.contactslist.Entity.ContactRegid


@Database(entities = arrayOf(Contact::class, ContactRegid::class), version = 2)
abstract class MyDatabase: RoomDatabase() {

    abstract fun contactDao() : ContactDao

    companion object {
        private val DB_NAME = "contact_db"
        private var dbInstance : MyDatabase? = null

        fun getDatabase(context: Context) : MyDatabase {
            if(dbInstance == null) {
                dbInstance = Room.databaseBuilder<MyDatabase>(context.applicationContext, MyDatabase::class.java, DB_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .build()
            }
            return dbInstance!!
        }


        val MIGRATION_1_2 : Migration = object: Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Contact ADD COLUMN phone_number text")
            }

        }
    }

}

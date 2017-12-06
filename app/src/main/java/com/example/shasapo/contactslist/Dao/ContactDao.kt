package com.example.shasapo.contactslist.Dao

import android.arch.persistence.room.*
import com.example.shasapo.contactslist.Entity.Contact
import android.arch.persistence.room.OnConflictStrategy




@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllContact(contactList: List<Contact>)

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("SELECT * FROM Contact")
    fun getAllContact(): List<Contact>

    @Query("SELECT * FROM Contact WHERE firstName= :name")
    fun getContactByFirstName(name: String) : Contact

    @Query("SELECT Count (*) FROM Contact")
    fun countContact(): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg user: Contact): LongArray


}
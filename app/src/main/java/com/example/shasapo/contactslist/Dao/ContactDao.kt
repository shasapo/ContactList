package com.example.shasapo.contactslist.Dao

import android.arch.persistence.room.*
import com.example.shasapo.contactslist.Entity.Contact
import android.arch.persistence.room.OnConflictStrategy




@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("SELECT * FROM CONTACT")
    fun getAllContact(): List<Contact>

    @Query("SELECT * FROM CONTACT WHERE first_name like :name")
    fun getContactByFirstName(name: String) : Contact

    @Query("SELECT COUNT(*) FROM CONTACT")
    fun countContact(): Int
}
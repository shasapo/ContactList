package com.example.shasapo.contactslist.Dao

import android.arch.persistence.room.*
import com.example.shasapo.contactslist.Entity.Contact
import android.arch.persistence.room.OnConflictStrategy
import android.support.annotation.VisibleForTesting
import com.example.shasapo.contactslist.Entity.ContactRegid
import io.reactivex.Single


@Dao
abstract class ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertContact(contact: Contact): Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertContactRegid(contactRegId: ContactRegid): Long

    @Insert
    abstract fun insertAll(contacts: List<Contact>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateContact(contact: Contact)

    @Delete
    abstract fun deleteContact(contact: Contact)

    @Query("SELECT * FROM CONTACT")
    abstract fun getAllContact(): List<Contact>

    @Query("SELECT * FROM contact_reg_id")
    abstract fun getAllContactRegId(): List<ContactRegid>

    @Query("SELECT * FROM CONTACT WHERE first_name like :arg0")
    abstract fun getContactByFirstName(name: String) : Contact


    @Query("SELECT * FROM CONTACT WHERE cid like :arg0")
    abstract fun getContactById(id: Int) : Single<Contact>

    @Query("SELECT COUNT(*) FROM CONTACT")
    abstract fun countContact(): Int


    @Transaction
    @VisibleForTesting
    open fun somethin(contact: Contact): Long {

        val id = insertContact(contact)
        val contactRegId = ContactRegid(contactId = id.toInt(), regId = 171222232)
        insertContactRegid(contactRegId)

        return id
    }

}
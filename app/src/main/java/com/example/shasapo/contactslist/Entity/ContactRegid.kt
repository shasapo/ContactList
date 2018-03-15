package com.example.shasapo.contactslist.Entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "contact_reg_id",
        foreignKeys = arrayOf(ForeignKey(entity = Contact::class,
                parentColumns = arrayOf("cId"),
                childColumns = arrayOf("contactID"),
                onDelete = ForeignKey.CASCADE)))
data class ContactRegid(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "crId")
        var crId: Long? = null,

        @ColumnInfo(name = "contactID")
        var contactId : Int? = 0,

        @ColumnInfo(name = "regId")
        var regId : Long? = null
)
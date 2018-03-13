package com.example.shasapo.contactslist.Entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "ContactRegid")
data class ContactRegid(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "crId")
        var crId: Long = 0,

        @ColumnInfo(name = "contactID")
        var contactId : Int? = 0,

        @ColumnInfo(name = "regId")
        var regId : Long? = null
)
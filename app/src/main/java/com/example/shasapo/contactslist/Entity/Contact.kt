package com.example.shasapo.contactslist.Entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Contact")
data class Contact(
        @PrimaryKey(autoGenerate = true)
        var cId: Int = 0,

        @ColumnInfo(name = "first_name")
        var firstName: String? = null,

        @ColumnInfo(name = "last_name")
        var lastName: String? = null,

        @ColumnInfo(name = "email")
        var email: String? = null
)
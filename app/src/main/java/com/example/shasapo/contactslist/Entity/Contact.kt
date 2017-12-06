package com.example.shasapo.contactslist.Entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Contact")
data class Contact(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,

        @ColumnInfo(name = "firstName")
        var firstName: String? = null,

        @ColumnInfo(name = "lastName")
        var lastName: String? = null,

        @ColumnInfo(name = "email")
        var email: String? = null
)
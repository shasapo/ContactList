package com.example.shasapo.contactslist.MIgration

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory
import android.arch.persistence.room.Room
import android.arch.persistence.room.testing.MigrationTestHelper
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.shasapo.contactslist.Database.MyDatabase
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestMigration {

    @get:Rule
    var helper : MigrationTestHelper = MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),MyDatabase::class.java.canonicalName,FrameworkSQLiteOpenHelperFactory())

    @Test
    fun testMigration1to2 () {

        var db : SupportSQLiteDatabase = helper.createDatabase("test_db",1)

        val sql = "INSERT INTO CONTACT(first_name,last_name,email) VALUEs('shasa','po','shasapo@gmail.com')"
        db.execSQL(sql)

        db.close()

        db = helper.runMigrationsAndValidate("test_db",2,true, MyDatabase.MIGRATION_1_2)

        val migratedDatabase = getDatabase()

        val contact = migratedDatabase.contactDao().getAllContact()

        Assert.assertTrue(contact[0].phoneNumber == null)
    }


    fun getDatabase() : MyDatabase {

        return Room.databaseBuilder(InstrumentationRegistry.getTargetContext(), MyDatabase::class.java, "test_db")
                .addMigrations(MyDatabase.MIGRATION_1_2)
                .build()
                .apply { helper.closeWhenFinished(this) }
    }



}
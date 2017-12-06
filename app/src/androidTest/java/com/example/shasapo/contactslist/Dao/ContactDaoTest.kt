package com.example.shasapo.contactslist.Dao

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.matcher.ViewMatchers.assertThat
import android.support.test.runner.AndroidJUnit4
import com.example.shasapo.contactslist.Database.MyDatabase
import com.example.shasapo.contactslist.Entity.Contact
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ContactDaoTest {

    private lateinit var database: MyDatabase
    private lateinit var dao: ContactDao

    val contact1 = Contact(2,"shasa","sdf","High")


    @Before fun initDb() {
        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                MyDatabase::class.java)
                .build()
        dao = database.contactDao()
    }

    @After fun closeDb() {
        database.close()
    }



    @Test
    fun testInsertContact() {

        assertEquals(0, dao.countContact())

        dao.insertContact(contact1)

        assertEquals(1, dao.countContact())


        //The user can be retrieved
        val users = dao.getAllContact()
        assertThat(users.size, `is`(1))
        val dbUser = users.get(0)
        assertEquals(dbUser.firstName, contact1.firstName)
        assertEquals(dbUser.lastName, contact1.lastName)

        val user = dao.getContactByFirstName("shasa")
        assertEquals(user.firstName, contact1.firstName )
        assertEquals(user.lastName, contact1.lastName )
    }


    @Test
    fun testInsertAllContact() {

        assertEquals(0, dao.countContact())

        val contacts = listOf(
            Contact(firstName = "shasa",lastName = "po",email = "shasapo@gmail.com"),
            Contact(firstName = "dino",lastName = "po",email = "shasapo@gmail.com"),
            Contact(firstName = "ayu",lastName = "po",email = "shasapo@gmail.com")
        )

        dao.insertAllContact(contacts)
        
        //assertEquals(3, dao.countContact())
    }

    @Test
    fun testConflictingInsertsReplaceUsers() {
        val contacts = listOf(
                Contact(firstName = "shasa",lastName = "po",email = "shasapo@gmail.com"),
                Contact(firstName = "dino",lastName = "po",email = "shasapo@gmail.com"),
                Contact(firstName = "ayu",lastName = "po",email = "shasapo@gmail.com")
        )

        dao.insertAllContact(contacts)

        assertEquals(contacts, dao.getAllContact())
    }



    @Test
    fun testUpdateContact() {
        // When inserting a new user in the data source
        dao.insertContact(contact1)
        val userss = dao.getAllContact()
        assertThat(userss.size, `is`(1))


        var users = dao.getContactByFirstName(contact1.firstName!!)
        assertEquals("shasa", contact1.firstName)
        assertEquals(contact1.lastName, contact1.lastName)

        contact1.firstName = "shasapo"
        dao.updateContact(contact1)


        //The user can be retrieved
        users = dao.getContactByFirstName(contact1.firstName!!)
        assertEquals("shasapo", contact1.firstName)
        assertEquals(contact1.lastName, contact1.lastName)
    }


    @Test
    fun testDeleteContact() {
        // When inserting a new user in the data source
        dao.insertContact(contact1)

        assertEquals(1, dao.countContact())

        dao.deleteContact(contact1)

        assertEquals(0, dao.countContact())
    }



    @Test fun getUsersWhenNoUserInserted() {
        val user = dao.getContactByFirstName("shasa")


    }
//
//    @Test fun insertAndGetUser() {
//        // When inserting a new user in the data source
//        database.userDao().insertUser(USER)
//
//        // When subscribing to the emissions of the user
//        database.userDao().getUserById(USER.id)
//                .test()
//                // assertValue asserts that there was only one emission of the user
//                .assertValue { it.id == USER.id && it.userName == USER.userName }
//    }
//
//    @Test fun updateAndGetUser() {
//        // Given that we have a user in the data source
//        database.userDao().insertUser(USER)
//
//        // When we are updating the name of the user
//        val updatedUser = User(USER.id, "new username")
//        database.userDao().insertUser(updatedUser)
//
//        // When subscribing to the emissions of the user
//        database.userDao().getUserById(USER.id)
//                .test()
//                // assertValue asserts that there was only one emission of the user
//                .assertValue { it.id == USER.id && it.userName == "new username" }
//    }
//
//    @Test fun deleteAndGetUser() {
//        // Given that we have a user in the data source
//        database.userDao().insertUser(USER)
//
//        //When we are deleting all users
//        database.userDao().deleteAllUsers()
//        // When subscribing to the emissions of the user
//        database.userDao().getUserById(USER.id)
//                .test()
//                // check that there's no user emitted
//                .assertNoValues()
//    }
//
//    companion object {
//        private val USER = User("id", "username")
//    }
}

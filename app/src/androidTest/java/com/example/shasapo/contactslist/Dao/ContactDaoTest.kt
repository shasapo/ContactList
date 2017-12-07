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

    @Test
    fun testInsertAll(){
        // When inserting a new user in the data source
        val listC = populateData()
        assertEquals(0, dao.countContact())

        dao.insertAll(listC)

        assertEquals(3, dao.countContact())

    }

    fun populateData() : List<Contact> {
        return mutableListOf(
                Contact(firstName = "Arif", lastName = "R. Hakim", email = "arif@bbmtek.com"),
                Contact(firstName = "Dino", lastName = "Dwiyaksa", email = "dino@bbmtek.com"),
                Contact(firstName = "Ayu", lastName = "Nindya", email = "ayu@bbmtek.com")
        )
    }

}

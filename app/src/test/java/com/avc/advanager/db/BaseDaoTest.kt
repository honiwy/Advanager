package com.avc.advanager.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

/**
 * Base class to reduce boilerplate code for testing DAO(s) of [RoomDatabase].
 */
abstract class BaseDaoTest<Database : RoomDatabase>(
    private val database: Class<Database>
){

    protected lateinit var db: Database

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            database
        ).build()
    }

    @After
    fun teardown() = db.close()
}
package com.donguyen.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.donguyen.data.db.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Created by DoNguyen on 29/10/18.
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
abstract class BaseDaoTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    protected lateinit var appDatabase: AppDatabase

    /**
     * Set up the dependencies and resources before every test.
     * Remember to call this method when inheriting from [BaseDaoTest]
     */
    @Before
    open fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(), AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    /**
     * Clear up dependencies and resources after every test.
     * Remember to call this method when inheriting from [BaseDaoTest]
     */
    @After
    open fun tearDown() {
        appDatabase.close()
    }
}
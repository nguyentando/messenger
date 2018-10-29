package com.donguyen.domain.test

import androidx.paging.DataSource
import androidx.paging.PagedList
import org.mockito.Mockito.mock

/**
 * Created by DoNguyen on 28/10/18.
 */
@Suppress("UNCHECKED_CAST")
fun <T> createMockPagedList() = mock(PagedList::class.java) as PagedList<T>

@Suppress("UNCHECKED_CAST")
fun <T> createMockDataSourceFactory() = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, T>



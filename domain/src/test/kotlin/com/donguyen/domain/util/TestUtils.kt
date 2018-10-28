package com.donguyen.domain.util

import androidx.paging.PagedList
import org.mockito.Mockito.mock

/**
 * Created by DoNguyen on 28/10/18.
 */
@Suppress("UNCHECKED_CAST")
fun <T> createMockPagedList(): PagedList<T> {
    return mock(PagedList::class.java) as PagedList<T>
}
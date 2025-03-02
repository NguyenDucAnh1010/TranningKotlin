package com.ducanh.roomdemo.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ducanh.roomdemo.data.dao.TaskDao
import com.ducanh.roomdemo.data.model.Task
import kotlinx.coroutines.delay

class TaskPagingSource(
    private val userId: Int,
    private val dao: TaskDao
) : PagingSource<Int, Task>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Task> {
        val page = params.key ?: 0

        return try {
            val entities = dao.getTasksByUserIdPaged(userId ,params.loadSize, page * params.loadSize)

            // simulate page loading
            if (page != 0) delay(1000)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Task>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
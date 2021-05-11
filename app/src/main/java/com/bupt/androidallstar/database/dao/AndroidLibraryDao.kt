package com.bupt.androidallstar.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bupt.androidallstar.database.entity.AndroidLibraryEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author zhangyongkang
 * @date 2021/04/27
 * @email zyk970512@163.com
 */

@Dao
interface AndroidLibraryDao {
    @Query("SELECT * FROM android_library")
    fun getAll(): Flow<List<AndroidLibraryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg androidLibraryEntity: AndroidLibraryEntity)
}
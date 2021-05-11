package com.bupt.androidallstar.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zhangyongkang
 * @date 2021/04/27
 * @email zyk970512@163.com
 */

@Entity(tableName = "android_library")
data class AndroidLibraryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "star_num") val starNum: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "github_url") val githubUrl: String?,
    @ColumnInfo(name = "kind_first") val kindFirst: String?,
    @ColumnInfo(name = "kind_second") val kindSecond: String?,
)

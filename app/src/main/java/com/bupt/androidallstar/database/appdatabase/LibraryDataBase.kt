package com.bupt.androidallstar.database.appdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bupt.androidallstar.database.dao.AndroidLibraryDao
import com.bupt.androidallstar.database.entity.AndroidLibraryEntity

/**
 * @author zhangyongkang
 * @date 2021/04/27
 * @email zyk970512@163.com
 */
@Database(entities = [AndroidLibraryEntity::class], version = 1)
abstract class LibraryDataBase : RoomDatabase() {
    abstract fun androidLibraryDao(): AndroidLibraryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LibraryDataBase? = null

        fun getDatabase(context: Context): LibraryDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibraryDataBase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
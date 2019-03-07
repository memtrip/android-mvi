package com.consistence.pinyin.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.consistence.pinyin.database.pinyin.PinyinDao
import com.consistence.pinyin.database.pinyin.PinyinEntity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Database(entities = [PinyinEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pinyinDao(): PinyinDao
}

@Module
class DatabaseModule {

    @Provides @Singleton
    fun appDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "pingyin")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides @Singleton
    fun pinyinDao(appDatabase: AppDatabase): PinyinDao {
        return appDatabase.pinyinDao()
    }
}
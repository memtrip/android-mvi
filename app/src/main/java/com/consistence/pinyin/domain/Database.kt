package com.consistence.pinyin.domain

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.consistence.pinyin.domain.pinyin.db.PinyinDao
import com.consistence.pinyin.domain.pinyin.db.PinyinEntity
import com.consistence.pinyin.domain.study.db.StudyDao
import com.consistence.pinyin.domain.study.db.StudyEntity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Database(entities = [PinyinEntity::class, StudyEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pinyinDao(): PinyinDao
    abstract fun studyDao(): StudyDao
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

    @Provides @Singleton
    fun studoDao(appDatabase: AppDatabase): StudyDao {
        return appDatabase.studyDao()
    }
}
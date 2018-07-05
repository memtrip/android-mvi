package com.consistence.pinyin.api

import android.app.Application
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Database
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Insert
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import dagger.Module
import dagger.Provides
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Database(entities = [PinyinEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pinyinDao(): PinyinDao
}

@Entity(tableName = "Pinyin")
data class PinyinEntity(
    @ColumnInfo(name = "sourceUrl") val sourceUrl: String,
    @ColumnInfo(name = "phoneticScriptText") val phoneticScriptText: String,
    @ColumnInfo(name = "romanLetterText") val romanLetterText: String,
    @ColumnInfo(name = "audioSrc") val audioSrc: String?,
    @ColumnInfo(name = "englishTranslationText") val englishTranslationText: String,
    @ColumnInfo(name = "chineseCharacters") val chineseCharacters: String,
    @ColumnInfo(name = "characterImageSrc") val characterImageSrc: String,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)

@Dao
interface PinyinDao {

    @Query("SELECT * FROM Pinyin WHERE romanLetterText LIKE :terms ORDER BY romanLetterText ASC LIMIT 0, 100")
    fun phoneticSearch(terms: String): List<PinyinEntity>

    @Query("SELECT * FROM Pinyin WHERE chineseCharacters LIKE :terms ORDER BY chineseCharacters ASC LIMIT 0, 100")
    fun characterSearch(terms: String): List<PinyinEntity>

    @Query("SELECT * FROM Pinyin WHERE englishTranslationText LIKE :terms ORDER BY englishTranslationText ASC LIMIT 0, 100")
    fun englishSearch(terms: String): List<PinyinEntity>

    @Insert
    fun insertAll(pinyin: List<PinyinEntity>)

    @Query("SELECT COUNT(*) FROM Pinyin")
    fun count(): Int
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

class SavePinyin @Inject internal constructor(
    private val pinyinDao: PinyinDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun insert(pinyin: List<PinyinJson>): Single<List<PinyinEntity>> {

        val pinyinEntities = pinyin.map {
            PinyinEntity(it.sourceUrl,
                    it.phoneticScriptText,
                    it.romanLetterText,
                    it.audioSrc,
                    it.englishTranslationText,
                    it.chineseCharacters,
                    it.characterImageSrc)
        }

        return Completable
                .fromAction({ pinyinDao.insertAll(pinyinEntities) })
                .observeOn(schedulerProvider.main())
                .subscribeOn(schedulerProvider.thread())
                .toSingle({ pinyinEntities })
    }
}

class CountPinyin @Inject internal constructor(
    private val pinyinDao: PinyinDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun count(): Single<Int> {
        return Single.fromCallable({ pinyinDao.count() })
                .observeOn(schedulerProvider.main())
                .subscribeOn(schedulerProvider.thread())
    }
}

class PhoneticSearch @Inject internal constructor(
    private val pinyinDao: PinyinDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun search(terms: String): Single<List<PinyinEntity>> {
        return Single.fromCallable({ pinyinDao.phoneticSearch("$terms%") })
                .observeOn(schedulerProvider.main())
                .subscribeOn(schedulerProvider.thread())
    }
}

class CharacterSearch @Inject internal constructor(
    private val pinyinDao: PinyinDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun search(terms: String): Single<List<PinyinEntity>> {
        return Single.fromCallable({ pinyinDao.characterSearch("$terms%") })
                .observeOn(schedulerProvider.main())
                .subscribeOn(schedulerProvider.thread())
    }
}

class EnglishSearch @Inject internal constructor(
    private val pinyinDao: PinyinDao,
    private val schedulerProvider: SchedulerProvider
) {

    fun search(terms: String): Single<List<PinyinEntity>> {
        return Single.fromCallable({ pinyinDao.englishSearch("%$terms%") })
                .observeOn(schedulerProvider.main())
                .subscribeOn(schedulerProvider.thread())
    }
}
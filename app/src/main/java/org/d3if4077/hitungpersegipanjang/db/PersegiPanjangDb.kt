package org.d3if4077.hitungpersegipanjang.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PersegiPanjangEntity::class], version = 1, exportSchema = false)
abstract class PersegiPanjangDb : RoomDatabase() {

    abstract val dao: PersegiPanjangDao

    companion object {
        @Volatile
        private var INSTANCE: PersegiPanjangDb? = null

        fun getInstance(context: Context): PersegiPanjangDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PersegiPanjangDb::class.java,
                        "persegipanjang.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

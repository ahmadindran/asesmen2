package org.d3if4077.hitungpersegipanjang.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersegiPanjangDao {

    @Insert
    fun insert(persegi_panjang: PersegiPanjangEntity)

    @Update
    fun update(persegi_panjang: PersegiPanjangEntity)

    @Query("SELECT * FROM persegi_panjang ORDER BY id DESC")
    fun getLastPersegiPanjang(): LiveData<List<PersegiPanjangEntity>>

    @Query("DELETE FROM persegi_panjang")
    fun clearData()

    @Query("DELETE FROM persegi_panjang WHERE id = :id")
    fun deleteData(id: Long)
}
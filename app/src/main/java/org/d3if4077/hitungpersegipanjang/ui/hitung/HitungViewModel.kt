package org.d3if4077.hitungpersegipanjang.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4077.hitungpersegipanjang.data.HasilHitung
import org.d3if4077.hitungpersegipanjang.data.HitungHasil
import org.d3if4077.hitungpersegipanjang.db.PersegiPanjangDao
import org.d3if4077.hitungpersegipanjang.db.PersegiPanjangEntity

class HitungViewModel(private val db: PersegiPanjangDao) : ViewModel() {
    private val hasilHitung = MutableLiveData<HasilHitung?>()

    private val hasilKeduanya = MutableLiveData<HasilHitung?>()

    fun hitungPersegi(panjang: String, lebar: String, cari: String, condition: String) {
        if (condition == "insert") {
            val dataPersegi = PersegiPanjangEntity(
                panjang = panjang.toFloat(),
                lebar = lebar.toFloat(),
                cari = cari
            )
            if (cari == "luas") hasilHitung.value = HitungHasil.hitung1(dataPersegi)
            else if (cari == "keliling") hasilHitung.value = HitungHasil.hitung2(dataPersegi)
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    db.insert(dataPersegi)
                }
            }
        }
    }

    fun hitungPersegi(panjang: String, lebar: String, cari: String, condition: String, id: Long?) {
        val dataPersegi = PersegiPanjangEntity(
            id = id!!,
            panjang = panjang.toFloat(),
            lebar = lebar.toFloat(),
            cari = cari
        )
        if (cari == "luas") hasilHitung.value = HitungHasil.hitung1(dataPersegi)
        else if (cari == "keliling") hasilHitung.value = HitungHasil.hitung2(dataPersegi)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.update(dataPersegi)
            }
        }
    }

    fun hitungKeduanya(panjang: String, lebar: String, cari: String, condition: String, id: Long?) {
        val dataPersegi = PersegiPanjangEntity(
            id = id!!,
            panjang = panjang.toFloat(),
            lebar = lebar.toFloat(),
            cari = cari
        )
        hasilHitung.value = HitungHasil.hitung3(dataPersegi)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.update(dataPersegi)
            }
        }
    }

    fun hitungKeduanya(panjang: String, lebar: String, cari: String, condition: String) {
        val dataPersegi = PersegiPanjangEntity(
            panjang = panjang.toFloat(),
            lebar = lebar.toFloat(),
            cari = cari
        )
        hasilHitung.value = HitungHasil.hitung3(dataPersegi)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataPersegi)
            }
        }
    }

    fun getHasilHitung(): LiveData<HasilHitung?> = hasilHitung

    fun getKeduanya(): LiveData<HasilHitung?> = hasilKeduanya
}
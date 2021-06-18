package org.d3if4077.hitungpersegipanjang.data

import org.d3if4077.hitungpersegipanjang.db.PersegiPanjangEntity

object HitungHasil {

    fun hitung1(data: PersegiPanjangEntity): HasilHitung {
        val hasil = data.panjang * data.lebar
        return HasilHitung(hasil, 0.0f)
    }

    fun hitung2(data: PersegiPanjangEntity): HasilHitung {
        val hasil = 2 * data.panjang + 2 * data.lebar
        return HasilHitung(hasil, 0.0f)
    }

    fun hitung3(data: PersegiPanjangEntity): HasilHitung {
        val hasil1 = data.panjang * data.lebar
        val hasil2 = 2 * data.panjang + 2 * data.lebar
        return HasilHitung(hasil1, hasil2)
    }
}
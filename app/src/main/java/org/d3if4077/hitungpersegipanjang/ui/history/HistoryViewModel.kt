package org.d3if4077.hitungpersegipanjang.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4077.hitungpersegipanjang.db.PersegiPanjangDao

class HistoryViewModel(private val db: PersegiPanjangDao) : ViewModel() {
    val data = db.getLastPersegiPanjang()

    fun clearData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }

    fun deleteData(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.deleteData(id)
            }
        }
    }


}
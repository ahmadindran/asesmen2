package org.d3if4077.hitungpersegipanjang.ui.history

import android.view.View
import org.d3if4077.hitungpersegipanjang.db.PersegiPanjangEntity

interface RecycleViewClickListener {

    fun onItemClicked(view: View, hasil: PersegiPanjangEntity)

    fun onItemDeleted(view: View, hasil: PersegiPanjangEntity)
}
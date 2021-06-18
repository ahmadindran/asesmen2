package org.d3if4077.hitungpersegipanjang.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.d3if4077.hitungpersegipanjang.data.HitungHasil
import org.d3if4077.hitungpersegipanjang.databinding.ItemHistoryBinding
import org.d3if4077.hitungpersegipanjang.db.PersegiPanjangEntity
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val data = mutableListOf<PersegiPanjangEntity>()
    var listener: RecycleViewClickListener? = null

    inner class HistoryViewHolder(val itemHistoryBinding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(itemHistoryBinding.root)

    fun updateData(newData: List<PersegiPanjangEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)

        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val binding = holder.itemHistoryBinding
        val dateFormatter = SimpleDateFormat(
            "dd MMMM yyyy",
            Locale("id", "ID")
        )
//        binding.tanggalTextView.text = dateFormatter.format(Date(data[position].tanggal.toString()))
        binding.panjangTextView.text = "Panjang = " +
                data[position].panjang.toString()
        binding.lebarTextView.text = "Lebar = " + data[position].lebar.toString()
        if (data[position].cari == "luas") {
            val hasilHitung = HitungHasil.hitung1(data[position])
            binding.tanggalTextView.text = "Luas"
            binding.hasilTextView.text = "Hasil = " + hasilHitung.hasil.toString()
        } else if (data[position].cari == "keliling") {
            val hasilHitung = HitungHasil.hitung2(data[position])
            binding.tanggalTextView.text = "Keliling"
            binding.hasilTextView.text = "Hasil = " + hasilHitung.hasil.toString()
        } else {
            val hasilHitung = HitungHasil.hitung3(data[position])
            binding.tanggalTextView.text = "Luas & Keliling"
            binding.hasilTextView.text = "Luas = " + hasilHitung.hasil.toString()
            binding.hasil1TextView.text = "Keliling = " + hasilHitung.hasil2.toString()
        }
        binding.hapusButton.setOnClickListener { listener?.onItemDeleted(it, data[position]) }
        binding.itemHasil.setOnClickListener { listener?.onItemClicked(it, data[position]) }
    }
}
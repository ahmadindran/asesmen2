package org.d3if4077.hitungpersegipanjang.ui.internet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3if4077.hitungpersegipanjang.databinding.FragmentPengertianBinding

class PengertianFragment : Fragment() {
    private lateinit var binding: FragmentPengertianBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPengertianBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.VISIBLE
        readData()
        return binding.root
    }

    private fun readData() {
        database = FirebaseDatabase.getInstance().getReference("aplikasi")
        database.child("pengertian").get().addOnSuccessListener {
            val pengertian = it.child("adalah").value
            val luas = it.child("luas").value
            val keliling = it.child("keliling").value
            binding.imageView.visibility = View.VISIBLE
            binding.pengertianTextView.text = pengertian.toString()
            binding.judulTextView.text = "Rumus Persegi Panjang"
            binding.luasTextView.text = luas.toString()
            binding.kelilingTextView.text = keliling.toString()
            binding.progressBar.visibility = View.GONE
        }.addOnFailureListener {
            binding.judulTextView.text = ""
            binding.pengertianTextView.text = "Koneksi Error"
            binding.progressBar.visibility = View.GONE
        }
    }
}
package org.d3if4077.hitungpersegipanjang.ui.internet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3if4077.hitungpersegipanjang.databinding.FragmentAboutBinding

class AboutFragment: Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.VISIBLE
        readData()
        return binding.root
    }

    private fun readData() {
        database = FirebaseDatabase.getInstance().getReference("aplikasi")
        database.child("about").get().addOnSuccessListener {
            val text = it.child("text").value
            val cc = it.child("cc").value
            binding.editText.text = text.toString() + "\n" + cc.toString()
            binding.progressBar.visibility = View.GONE
        }.addOnFailureListener {
            binding.editText.text = "Koneksi Error"
            binding.progressBar.visibility = View.GONE
        }
    }
}
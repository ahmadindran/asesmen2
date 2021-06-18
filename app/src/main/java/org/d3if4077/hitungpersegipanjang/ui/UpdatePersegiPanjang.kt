package org.d3if4077.hitungpersegipanjang.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import org.d3if4077.hitungpersegipanjang.R
import org.d3if4077.hitungpersegipanjang.databinding.FragmentUpdateBinding
import org.d3if4077.hitungpersegipanjang.db.PersegiPanjangDb
import org.d3if4077.hitungpersegipanjang.db.PersegiPanjangEntity
import org.d3if4077.hitungpersegipanjang.ui.hitung.HitungViewModel
import org.d3if4077.hitungpersegipanjang.ui.hitung.HitungViewModelFactory

class UpdatePersegiPanjang : Fragment() {

    private var data: PersegiPanjangEntity? = null
    private var lebar = 0.0f
    private var panjang = 0.0f
    private val viewModel: HitungViewModel by lazy {
        val db = PersegiPanjangDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HitungViewModel::class.java)
    }
    private lateinit var binding: FragmentUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(
            layoutInflater, container, false
        )
        data = arguments?.getParcelable(EXTRA_DATA)
        panjang = data!!.panjang
        lebar = data!!.lebar
        binding.panjangEditText.setText(panjang.toString())
        binding.lebarEditText.setText(lebar.toString())
        binding.hitung.text = "Update"
        binding.hitung.setOnClickListener { goHasil(data!!.id) }
        return binding.root
    }

    private fun goHasil(id: Long) {
        val panjangText = binding.panjangEditText.text.toString()
        if (TextUtils.isEmpty(panjangText)) {
            Toast.makeText(context, R.string.panjang_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val lebarText = binding.lebarEditText.text.toString()
        if (TextUtils.isEmpty(lebarText)) {
            Toast.makeText(context, R.string.lebar_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.hitung_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val cari = hitungUser()
        val condition = "update"
        if (cari == "keduanya") {
            viewModel.hitungKeduanya(panjangText, lebarText, cari, condition, id)
        }
        viewModel.hitungPersegi(panjangText, lebarText, cari, condition, id)
        view?.findNavController()?.navigate(R.id.action_updatePersegiPanjang_to_historyFragment)
    }

    private fun hitungUser(): String {
        return when (binding.radioGroup.checkedRadioButtonId) {
            R.id.luasRadioButton -> {
                "luas"
            }
            R.id.kelilingRadioButton -> {
                "keliling"
            }
            else -> {
                "keduanya"
            }
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
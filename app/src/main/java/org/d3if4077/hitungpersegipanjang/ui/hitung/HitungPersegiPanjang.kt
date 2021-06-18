package org.d3if4077.hitungpersegipanjang.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if4077.hitungpersegipanjang.R
import org.d3if4077.hitungpersegipanjang.databinding.FragmentInputBinding
import org.d3if4077.hitungpersegipanjang.db.PersegiPanjangDb

class HitungPersegiPanjang : Fragment() {

    private val viewModel: HitungViewModel by lazy {
        val db = PersegiPanjangDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HitungViewModel::class.java)
    }
    private lateinit var binding: FragmentInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputBinding.inflate(
            layoutInflater, container, false
        )

        binding.hitung.setOnClickListener { goHasil() }
        binding.reset.setOnClickListener { reset() }
        binding.caraButton.setOnClickListener { goCara() }
        binding.shareButton.setOnClickListener { shareData() }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.hitung_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_history) {
            findNavController().navigate(
                R.id.action_hitungPersegiPanjang_to_historyFragment
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHasilHitung().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            binding.bmiTextView.text = getString(R.string.hasil, it.hasil)
            binding.buttonGroup.visibility = View.VISIBLE
        })

        viewModel.getKeduanya().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            binding.bmiTextView.text = getString(R.string.l_k, it.hasil, it.hasil2)
            binding.buttonGroup.visibility = View.VISIBLE
        })

    }

    private fun goHasil() {
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
        val condition = "insert"
        if (cari == "keduanya") {
            viewModel.hitungKeduanya(panjangText, lebarText, cari, condition)
        }
        viewModel.hitungPersegi(panjangText, lebarText, cari, condition)
    }

    private fun goCara() {
        val panjangText = binding.panjangEditText.text.toString().toFloat()
        val lebarText = binding.lebarEditText.text.toString().toFloat()
        val userCari = hitungUser()
        val action =
            HitungPersegiPanjangDirections.actionHitungPersegiPanjangToHasilHitungSisi(
                panjangText,
                lebarText,
                userCari
            )
        view?.findNavController()?.navigate(action)
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

    private fun shareData() {
        val panjangText = binding.panjangEditText.text.toString()
        val lebarText = binding.lebarEditText.text.toString()
        val hasil = binding.bmiTextView.text
        val message = "Panjang = $panjangText\nLebar = $lebarText\n$hasil"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager
            ) != null
        ) {
            startActivity(shareIntent)
        }
    }

    private fun reset() {
        binding.panjangEditText.text.clear()
        binding.lebarEditText.text.clear()
        binding.radioGroup.clearCheck()
        binding.bmiTextView.text = ""
        binding.caraButton.visibility = View.INVISIBLE
        binding.buttonGroup.visibility = View.INVISIBLE
    }

}
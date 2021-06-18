package org.d3if4077.hitungpersegipanjang.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if4077.hitungpersegipanjang.R
import org.d3if4077.hitungpersegipanjang.databinding.FragmentHasilLuasKelilingBinding

class HasilLuasKeliling : Fragment() {
    private lateinit var binding: FragmentHasilLuasKelilingBinding

    private val args: HasilLuasKelilingArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHasilLuasKelilingBinding.inflate(
            layoutInflater, container, false
        )
        val getPanjang = args.panjang
        val getLebar = args.lebar
        val getCari = args.cari
        updateUI(getPanjang, getLebar, getCari)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onShare() {
        val message = binding.textView.text.toString()
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager
            ) != null
        ) {
            startActivity(shareIntent)
        }
    }

    private fun updateUI(panjang: Float, lebar: Float, cari: String) {
        val actionBar = (requireActivity() as AppCompatActivity)
            .supportActionBar
        when (cari) {
            "luas" -> {
                actionBar?.title = getString(R.string.judul_luas)
                getLuas(panjang, lebar)
            }
            "keliling" -> {
                actionBar?.title = getString(R.string.judul_keliling)
                getKeliling(panjang, lebar)
            }
            "keduanya" -> {
                actionBar?.title = getString(R.string.judul_keduanya)
                getBoth(panjang, lebar)
            }
        }
    }

    private fun getKeliling(panjang: Float, lebar: Float) {
        val panjangTotal = 2 * panjang
        val lebarTotal = 2 * lebar
        val kelilingTotal = panjangTotal + lebarTotal
        getHasil(panjang, lebar, panjangTotal, lebarTotal, kelilingTotal)
    }

    private fun getLuas(panjang: Float, lebar: Float) {
        val luasTotal = panjang * lebar
        getHasil(panjang, lebar, luasTotal)
    }

    private fun getBoth(panjang: Float, lebar: Float) {
        val luasTotal = panjang * lebar

        val panjangTotal = 2 * panjang
        val lebarTotal = 2 * lebar
        val kelilingTotal = panjangTotal + lebarTotal

        getHasil(panjang, lebar, luasTotal, panjangTotal, lebarTotal, kelilingTotal)
    }

    private fun getHasil(
        panjang: Float,
        lebar: Float,
        panjangTotal: Float,
        lebarTotal: Float,
        kelilingTotal: Float
    ) {
        binding.textView.text =
            getString(R.string.keliling_x, panjang, lebar, panjangTotal, lebarTotal, kelilingTotal)
    }

    private fun getHasil(panjang: Float, lebar: Float, luasTotal: Float) {
        binding.textView.text = getString(R.string.luas_x, panjang, lebar, luasTotal)
    }

    private fun getHasil(
        panjang: Float,
        lebar: Float,
        luasTotal: Float,
        panjangTotal: Float,
        lebarTotal: Float,
        kelilingTotal: Float
    ) {
        binding.textView.text = getString(
            R.string.luas_keliling_x,
            panjang,
            lebar,
            luasTotal,
            panjang,
            lebar,
            panjangTotal,
            lebarTotal,
            kelilingTotal
        )
    }
}
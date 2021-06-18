package org.d3if4077.hitungpersegipanjang.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if4077.hitungpersegipanjang.R
import org.d3if4077.hitungpersegipanjang.databinding.FragmentHomepageBinding

class Homepage: Fragment() {
    private lateinit var binding: FragmentHomepageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomepageBinding.inflate(
            layoutInflater, container, false
        )
        binding.kelilingLuasButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_homepage_to_hitungPersegiPanjang)
        }
        binding.cariSisiButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_homepage_to_pengertianFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.about_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_homepage_to_aboutFragment
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
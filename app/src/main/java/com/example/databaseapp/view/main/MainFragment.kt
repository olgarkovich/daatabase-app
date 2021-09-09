package com.example.databaseapp.view.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databaseapp.view.MainActivity
import com.example.databaseapp.R
import com.example.databaseapp.databinding.FragmentMainBinding
import com.example.databaseapp.model.Animal

class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var owner: MainActivity

    private val animalAdapter = AnimalsAdapter(this)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()


    }

    private fun initView() {
        owner.title?.text = "Animals"
        owner.settingsIcon?.visibility = View.VISIBLE

        owner.settingsIcon?.setOnClickListener(this)

        animalAdapter.submitList(listOf(Animal(0, "aa", "aa", "vv"), Animal(1, "cc", "W", "aa")))
        binding.animalRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = animalAdapter
        }



    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.settings_icon -> {
                owner.title?.text = "Settings"
                owner.settingsIcon?.visibility = View.GONE
                findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
            }
        }
    }

}
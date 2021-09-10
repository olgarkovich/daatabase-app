package com.example.databaseapp.view.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databaseapp.AnimalApplication
import com.example.databaseapp.view.MainActivity
import com.example.databaseapp.R
import com.example.databaseapp.databinding.FragmentMainBinding
import com.example.databaseapp.model.Actions
import com.example.databaseapp.model.Animal
import com.example.databaseapp.model.SortMode
import com.example.databaseapp.view.AnimalListener

class MainFragment : Fragment(), View.OnClickListener, AnimalListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var owner: MainActivity

    private var name = ""
    private var age = ""
    private var breed = ""
    private var action = Actions.NOTHING
    private var currentId = 0

    private val animalsViewModel: AnimalsViewModel by viewModels {
        AnimalViewModelFactory((requireActivity().application as AnimalApplication).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainActivity

        name = arguments?.getString("name") ?: ""
        age = arguments?.getString("age") ?: ""
        breed = arguments?.getString("breed") ?: ""
        currentId = arguments?.getInt("id") ?: 0

        action = when (arguments?.getString("action")) {
            Actions.ADD.name -> Actions.ADD
            Actions.UPDATE.name -> Actions.UPDATE
            else -> Actions.NOTHING
        }
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
        owner.title?.text = resources.getString(R.string.animals)
        owner.settingsIcon?.visibility = View.VISIBLE

        owner.settingsIcon?.setOnClickListener(this)

        when (action) {
            Actions.ADD -> add()
            Actions.UPDATE -> animalsViewModel.update(Animal(currentId, name, age, breed))
            else -> {
                name = ""
                age = ""
                breed = ""
            }
        }

        val animalAdapter = AnimalsAdapter(this)
        binding.animalRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = animalAdapter
        }

        animalsViewModel.allAnimals.observe(owner = requireActivity()) { animals ->
            animals.let { animalAdapter.submitList(it) }
        }

        findNavController()
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addItemFragment)
        }


    }

    override fun onResume() {
        super.onResume()

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        Toast.makeText(
            requireContext(),
            prefs.getString("sort", SortMode.NAME.name),
            Toast.LENGTH_SHORT
        ).show()
        Toast.makeText(
            requireContext(),
            prefs.getBoolean("database_mode", true).toString(),
            Toast.LENGTH_SHORT
        ).show()

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.settings_icon -> {
                owner.title?.text = resources.getString(R.string.settings)
                owner.settingsIcon?.visibility = View.GONE
                findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
            }
        }
    }

    override fun add() {
        if (action == Actions.ADD) {
            animalsViewModel.insert(Animal(name, age, breed))
        }
    }

    override fun update(animal: Animal) {
        currentId = animal.id
        val bundle = bundleOf(
            "action" to Actions.UPDATE.name,
            "id" to currentId,
            "name" to animal.name,
            "age" to animal.age,
            "breed" to animal.breed)
        findNavController().navigate(R.id.action_mainFragment_to_addItemFragment, bundle)
    }

    override fun delete(animal: Animal) {
        animalsViewModel.delete(animal)
    }

}
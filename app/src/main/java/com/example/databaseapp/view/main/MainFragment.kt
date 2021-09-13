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
import com.example.databaseapp.data.SQLHelper
import com.example.databaseapp.databinding.FragmentMainBinding
import com.example.databaseapp.model.Actions
import com.example.databaseapp.model.Animal
import com.example.databaseapp.model.SortMode
import com.example.databaseapp.view.AnimalListener

class MainFragment : Fragment(), View.OnClickListener, AnimalListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var owner: MainActivity
    private lateinit var sqlDatabase: SQLHelper

    private var isRoomDatabase = true
    private var sortMode = SortMode.NAME.name

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

        sqlDatabase = SQLHelper(requireContext())

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

    private fun initView() {
        owner.title?.text = resources.getString(R.string.animals)
        owner.settingsIcon?.visibility = View.VISIBLE

        owner.settingsIcon?.setOnClickListener(this)

        when (action) {
            Actions.ADD -> add()
            Actions.UPDATE -> {
                if (isRoomDatabase) {
                    animalsViewModel.update(Animal(currentId, name, age, breed))
                } else {
                    sqlDatabase.update(Animal(currentId, name, age, breed))
                }
            }
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

        if (isRoomDatabase) {
            animalsViewModel.allAnimals.observe(owner = requireActivity()) { animals ->
                animals.let { animalAdapter.submitList(sortList(animals)) }
            }
        } else {
            var list = sqlDatabase.getListOfAnimals()
            list = sortList(list)
            animalAdapter.submitList(list)
        }

        findNavController()
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addItemFragment)
        }
    }

    private fun sortList(list: List<Animal>): List<Animal> {
        when(sortMode) {
            SortMode.NAME.name -> return list.sortedBy { it.name }
            SortMode.AGE.name -> return list.sortedBy { it.age }
            SortMode.BREED.name -> return list.sortedBy { it.breed }
        }
        return list.sortedBy { it.name }
    }

    override fun onResume() {
        super.onResume()

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        isRoomDatabase = prefs.getBoolean("database_mode", true)
        sortMode = prefs.getString("sort", SortMode.NAME.name) ?: SortMode.NAME.name

        Toast.makeText(requireContext(), sortMode, Toast.LENGTH_SHORT).show()

        initView()
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
                action = Actions.NOTHING
                findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
            }
        }
    }

    override fun add() {
        if (action == Actions.ADD && isRoomDatabase) {
            animalsViewModel.insert(Animal(name, age, breed))
        } else {
            sqlDatabase.insert(Animal(name, age, breed))
        }
    }

    override fun update(animal: Animal) {
        currentId = animal.id
        val bundle = bundleOf(
            "action" to Actions.UPDATE.name,
            "id" to currentId,
            "name" to animal.name,
            "age" to animal.age,
            "breed" to animal.breed
        )
        findNavController().navigate(R.id.action_mainFragment_to_addItemFragment, bundle)
    }

    override fun delete(animal: Animal) {
        if (isRoomDatabase) {
            animalsViewModel.delete(animal)
        } else {
            sqlDatabase.delete(animal)
            action = Actions.NOTHING
            onResume()
        }
    }

}
package com.example.databaseapp.view.addItem

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.databaseapp.AnimalApplication
import com.example.databaseapp.R
import com.example.databaseapp.databinding.FragmentAddItemBinding
import com.example.databaseapp.databinding.FragmentMainBinding
import com.example.databaseapp.databinding.ItemAnimalBinding
import com.example.databaseapp.view.MainActivity
import com.example.databaseapp.view.main.AnimalViewModelFactory
import com.example.databaseapp.view.main.AnimalsViewModel

class AddItemFragment : Fragment(), View.OnClickListener {

    private lateinit var owner: MainActivity

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        owner.title?.text = getString(R.string.add_animal)
        owner.settingsIcon?.visibility = View.INVISIBLE

        binding.addAnimalButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.add_animal_button -> {
                if (checkFields()) {
                    val bundle = bundleOf(
                        "add" to true,
                        "name" to binding.nameEditText.text.toString(),
                        "age" to binding.ageEditText.text.toString(),
                        "breed" to binding.breedEditText.text.toString())
                    findNavController().navigate(R.id.action_addItemFragment_to_mainFragment, bundle)
                }
            }
        }
    }

    private fun checkFields(): Boolean {
        var check = true
        if (binding.nameEditText.text.toString() == "") {
            check = false
            Toast.makeText(requireContext(), "empty name", Toast.LENGTH_SHORT).show()
        }
        if (binding.ageEditText.text.toString() == "") {
            check = false
            Toast.makeText(requireContext(), "empty age", Toast.LENGTH_SHORT).show()
        }
        if (binding.breedEditText.text.toString() == "") {
            check = false
            Toast.makeText(requireContext(), "empty breed", Toast.LENGTH_SHORT).show()
        }
        return check
    }

}
package com.example.employeeapp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.employeeapp.R
import com.example.employeeapp.databinding.FragmentProfileBinding
import com.example.persiapanujikom.data.Employee
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val employeeId = 1

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.show(employeeId).collect { employee ->
                    createEmployeeIfNotExist(employee)

                    employee?.let {
                        binding.name.text = it.name
                        binding.nip.text = it.nip
                        binding.gender.text = it.gender
                        binding.birth.text =
                            getString(R.string.profile_birth, it.placeOfBirth, it.dateOfBirth)
                        binding.address.text = it.address

//                        if (employee.photo.isEmpty()) {
//                            Glide.with(this@ProfileFragment)
//                                .load("https://ui-avatars.com/api/?name=${employee.name}")
//                                .into(binding.profilePicture)
//                        } else {
//                            Glide.with(this@ProfileFragment).load(it.photo)
//                                .into(binding.profilePicture)
//                        }
                    }
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            val bundle = bundleOf("employeeId" to employeeId)
            findNavController().navigate(R.id.action_ProfileFragment_to_EditProfileFragment, bundle)
        }
    }

    private suspend fun createEmployeeIfNotExist(employee: Employee?) {
        if (employee == null) {
            // Insert dummy employee if not found
            val dummyEmployee = Employee(
                id = 1,
                name = "Aulia El Ihza Fariz Rafiqi",
                nip = "1234567890",
                gender = "Laki-Laki",
                dateOfBirth = "07-01-2003",
                placeOfBirth = "Bekasi",
                address = "Ujung Harapan, Gg. Al-Ikhlas 14",
                photo = ""
            )
            viewModel.insert(dummyEmployee)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
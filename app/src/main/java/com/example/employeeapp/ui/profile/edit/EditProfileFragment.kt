package com.example.employeeapp.ui.profile.edit

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.employeeapp.R
import com.example.employeeapp.databinding.FragmentEditProfileBinding
import com.example.employeeapp.data.Employee
import kotlinx.coroutines.launch

/**
 * Fragment untuk menampilkan halaman edit profile
 */
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditProfileViewModel by viewModels()
    private var selectedImageUri: Uri? = null
    private var employeeData: Employee? = null

    private val pickImage = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            Glide.with(this).load(uri).into(binding.imageProfile)
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil ID karyawan dari argumen
        val employeeId = arguments?.getInt("employeeId") ?: 0

        // Melakukan inisialisasi dalam coroutine
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.show(employeeId).collect { employee ->
                    employeeData = employee

                    // Menampilkan data karyawan ke elemen UI
                    employee?.let {
                        binding.editName.setText(it.name)
                        binding.editNip.setText(it.nip)
                        binding.radioMale.isChecked = (it.gender == "Laki-Laki")
                        binding.radioFemale.isChecked = (it.gender != "Laki-Laki")
                        binding.editBirth.setText(
                            getString(
                                R.string.profile_birth,
                                it.placeOfBirth,
                                it.dateOfBirth
                            )
                        )
                        binding.editAddress.setText(it.address)
                        Glide.with(this@EditProfileFragment).load(it.photo)
                            .into(binding.imageProfile)
                    }

                    // Menampilkan avatar default jika tidak ada foto karyawan
                    if (employee?.photo?.isBlank() == true) {
                        Glide.with(this@EditProfileFragment)
                            .load("https://ui-avatars.com/api/?name=${employee.name}")
                            .into(binding.imageProfile)
                    }
                }
            }
        }

        // Menangani klik tombol pilih gambar
        binding.btnChooseImage.setOnClickListener {
            val mimeType = "image/*"
            pickImage.launch(PickVisualMediaRequest(PickVisualMedia.SingleMimeType(mimeType)))
        }

        // Validasi input pengguna
        validateInput()

        // Menangani klik tombol simpan
        binding.btnSave.setOnClickListener {
            updateEmployee()

            findNavController().navigate(R.id.action_EditProfileFragment_to_ProfileFragment)
        }
    }

    /**
     * Fungsi untuk melakukan validasi input dan memunculkan error jika tidak
     * memenuhi rules yang telah ditentukan
     */
    private fun validateInput() {
        binding.editName.doOnTextChanged { text, _, _, _ ->
            binding.editName.error = if (text.isNullOrBlank()) {
                "Name is required"
            } else {
                null
            }
        }

        binding.editNip.doOnTextChanged { text, _, _, _ ->
            binding.editNip.error = if (text.isNullOrBlank()) {
                "NIP is required"
            } else {
                null
            }
        }

        binding.editBirth.doOnTextChanged { text, _, _, _ ->
            binding.editBirth.error = if (text.isNullOrBlank()) {
                "Birth is required"
            } else {
                null
            }
        }
    }

    /**
     * Fungsi untuk memperbarui data karyawan
     *
     * Fungsi ini mengambil data yang dimasukkan oleh pengguna dari berbagai elemen UI,
     * membuat objek `Employee` baru dengan data tersebut, dan kemudian memanggil
     * metode `update` dari `viewModel` untuk memperbarui data karyawan di database.
     */
    private fun updateEmployee() {
        val updatedName = binding.editName.text.toString()
        val updatedNip = binding.editNip.text.toString()
        val updatedGender =
            if (binding.radioGroupGender.checkedRadioButtonId == R.id.radio_male) "Laki-Laki" else "Perempuan"
        val updatedAddress = binding.editAddress.text.toString()

        val updatedBirth = binding.editBirth.text.toString()
        val (updatedPlaceOfBirth, updatedDateOfBirth) = updatedBirth.split(", ", limit = 2)

        val employeeId = arguments?.getInt("employeeId") ?: 0
        val selectedPhoto = when {
            selectedImageUri != null -> selectedImageUri.toString()
            employeeData?.photo?.isNotEmpty() == true -> employeeData?.photo
            else -> "https://ui-avatars.com/api/?name=${employeeData?.name}"
        }

        val updatedEmployee = Employee(
            id = employeeId,
            name = updatedName,
            nip = updatedNip,
            gender = updatedGender,
            dateOfBirth = updatedDateOfBirth,
            placeOfBirth = updatedPlaceOfBirth,
            address = updatedAddress,
            photo = selectedPhoto.toString()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.update(updatedEmployee)
            Toast.makeText(requireContext(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
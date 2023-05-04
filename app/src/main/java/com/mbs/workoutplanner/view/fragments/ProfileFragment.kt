package com.mbs.workoutplanner.view.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.get
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mbs.workoutplanner.R
import com.mbs.workoutplanner.application.Application_Constants
import com.mbs.workoutplanner.view.viewmodels.UserViewModel
import com.mbs.workoutplanner.databinding.FragmentProfileBinding
import com.mbs.workoutplanner.view.activitys.LoginActivity
import com.mbs.workoutplanner.view.activitys.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var currentPhotoPath: String? = null
    var file: ByteArray? = null

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addProfilePic.setOnClickListener { openImagePicker() }
        binding.editProfileButton.setOnClickListener {
            val dialog = EditProfileDialogFragment()
            dialog.show(requireActivity().supportFragmentManager, Application_Constants.EDIT_PROFILE_DIALOG)
        }
        setupLogoutButton()
        registerObserver()
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getUserData()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                // A permissão foi concedida, você pode iniciar a câmera
                startCamera()
            } else {
                // A permissão foi negada, você pode exibir uma mensagem para o usuário ou solicitar novamente
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun checkCameraPermission() {
        if (
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // A permissão da câmera já foi concedida, você pode iniciar a câmera
            startCamera()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Camera permission")
                .setMessage("Camera permission required to take pictures.")
                .setPositiveButton("OK") { _, _ ->
                    requestCameraPermission.launch(Manifest.permission.CAMERA)
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }

    private fun startCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            // Error occurred while creating the File
            null
        }
        // Continue only if the File was successfully created
        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.mbs.workoutplanner.fileprovider",
                it
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            activityResultContract.launch(takePictureIntent)

        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = path
        }
    }
    private val activityResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // A imagem foi selecionada pela galeria
                val uri = result.data?.data
                if (uri != null) {
                    binding.profilePic.setImageURI(uri)
                    lifecycleScope.launch(Dispatchers.IO) {
                        val bitmap = Glide.with(this@ProfileFragment)
                            .asBitmap()
                            .load(uri)
                            .submit()
                            .get()
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream)
                        val byteArray = stream.toByteArray()
                        //viewModel.savePhotoToDB(byteArray)
                    }
                } else {
                    // A imagem foi capturada pela câmera
                    lifecycleScope.launch(Dispatchers.IO) {
                        val bitmap = Glide.with(this@ProfileFragment)
                            .asBitmap()
                            .load(currentPhotoPath)
                            .submit()
                            .get()
                        withContext(Dispatchers.Main) {
                            binding.profilePic.setImageBitmap(bitmap)
                        }
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream)
                        val byteArray = stream.toByteArray()
                        //viewModel.savePhotoToDB(byteArray)
                    }
                }
            }
        }

    private fun openImagePicker() {
        val options = arrayOf<CharSequence>("Take picture", "Choose from gallery", "Cancel")
        AlertDialog.Builder(requireContext())
            .setTitle("New profile picture")
            .setItems(options) { dialog, item ->
                when {
                    options[item] == "Take picture" -> {
                        checkCameraPermission()
                    }
                    options[item] == "Choose from gallery" -> {
                        val pickPhoto =
                            Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                        activityResultContract.launch(pickPhoto)
                    }
                    options[item] == "Cancel" -> {
                        dialog.dismiss()
                    }
                }
            }
            .show()
    }
    private fun registerObserver() {
        userViewModel.userData.observe(viewLifecycleOwner) {
            binding.username.text = it.name
            binding.modalityValue.text = it.modality
            binding.weightValue.text = it.weight.toString()
            binding.heightValue.text = it.height.toString()
            binding.bfValue.text = it.bf.toString()
        }
    }
    private fun setupLogoutButton() {
        binding.logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            requireActivity().finish()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }
}

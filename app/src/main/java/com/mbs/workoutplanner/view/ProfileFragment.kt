package com.mbs.workoutplanner.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
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
import com.mbs.workoutplanner.UserViewModel
import com.mbs.workoutplanner.databinding.FragmentProfileBinding
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
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPhotoFromDB()
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
        viewModel.userPhoto.observe(viewLifecycleOwner) { byteArrayPhoto ->
            if (byteArrayPhoto != null) {
                val bitmap = BitmapFactory.decodeByteArray(byteArrayPhoto, 0, byteArrayPhoto.size)
                binding.profilePic.setImageBitmap(bitmap)
            }
        }

        binding.addProfilePic.setOnClickListener { openImagePicker() }
    }

    override fun onResume() {
        super.onResume()
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
                Toast.makeText(requireContext(), "Permissão da câmera negada", Toast.LENGTH_SHORT)
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
                .setTitle("Permissão da câmera")
                .setMessage("Necessário permissão da câmera para tirar fotos.")
                .setPositiveButton("OK") { _, _ ->
                    requestCameraPermission.launch(Manifest.permission.CAMERA)
                }
                .setNegativeButton("Cancelar", null)
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
                val data: Intent? = result.data
                if (data?.data != null) {
                    val uri = data.data
                    if (uri != null) {
                        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            val source =
                                ImageDecoder.createSource(requireContext().contentResolver, uri)
                            ImageDecoder.decodeBitmap(source)
                        } else {
                            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                        }
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                        val byteArray = stream.toByteArray()
                        lifecycleScope.launch(Dispatchers.IO) {
                            viewModel.savePhotoToDB(byteArray)
                        }
                        binding.profilePic.setImageURI(data.data)
                    }
                } else {
                    // A imagem foi capturada pela câmera

                    lateinit var bitmap: Bitmap
                    val path = currentPhotoPath
                    try {
                        bitmap = BitmapFactory.decodeFile(path)
                    } catch (e: OutOfMemoryError) {
                        try {
                            val options = BitmapFactory.Options()
                            options.inSampleSize = 2
                            bitmap = BitmapFactory.decodeFile(path, options)
                        } catch (e: Exception) {
                            Log.e("erro", e.message.toString())
                        }
                    } finally {
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                        file = stream.toByteArray()
                    }

                    lifecycleScope.launch(Dispatchers.IO) {
                        file?.let { byteArray ->
                            viewModel.savePhotoToDB(byteArray)
                        }
                    }
                    val tempImage = BitmapFactory.decodeFile(path)
                    binding.profilePic.setImageBitmap(tempImage)
                }
            }
        }

    private fun openImagePicker() {
        val options = arrayOf<CharSequence>("Tirar foto", "Escolher da galeria", "Cancelar")
        AlertDialog.Builder(requireContext())
            .setTitle("Nova foto de perfil")
            .setItems(options) { dialog, item ->
                when {
                    options[item] == "Tirar foto" -> {
                        checkCameraPermission()
                    }
                    options[item] == "Escolher da galeria" -> {
                        val pickPhoto =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        activityResultContract.launch(pickPhoto)
                    }
                    options[item] == "Cancelar" -> {
                        dialog.dismiss()
                    }
                }
            }
            .show()
    }
}

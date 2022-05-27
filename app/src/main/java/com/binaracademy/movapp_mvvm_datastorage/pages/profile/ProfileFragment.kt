package com.binaracademy.movapp_mvvm_datastorage.pages.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.binaracademy.movapp_mvvm_datastorage.R
import com.binaracademy.movapp_mvvm_datastorage.database.create_db.UserDatabase
import com.binaracademy.movapp_mvvm_datastorage.database.model_db.User
import com.binaracademy.movapp_mvvm_datastorage.databinding.FragmentLoginBinding
import com.binaracademy.movapp_mvvm_datastorage.databinding.FragmentProfileBinding
import com.binaracademy.movapp_mvvm_datastorage.databinding.FragmentRegisterBinding
import com.binaracademy.movapp_mvvm_datastorage.pages.login.LoginFragmentDirections
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class ProfileFragment : Fragment() {

    lateinit var userManager: com.binaracademy.movapp_mvvm_datastorage.data_store.UserManager
    private var user_db: UserDatabase? = null
    private val profileViewModel: ProfileViewModel by viewModel()
    private val sharedPref = "sharedpreferences"
    private lateinit var binding: FragmentProfileBinding
    var idProfile: Int = 0
    var isLoggedIn: Boolean = false
    var username = ""
    var email =""
    lateinit var resultImage: String
    lateinit var password: String
    lateinit var rePassword: String
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            Glide.with(requireActivity())
                .load(result)
                .apply(RequestOptions.centerCropTransform())
                .into(binding.profileImage)
            resultImage = result.toString();
            print(resultImage.toString())
            Log.d("Check", resultImage)

        }


    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager =
            com.binaracademy.movapp_mvvm_datastorage.data_store.UserManager(requireContext())
        profileViewModel.getUsername().observe(viewLifecycleOwner){
            profileViewModel.getIdProfile(it)
            binding.etUsername.setText(it)
            getProfile()
        }



        binding.apply {
            binding.btnLogout.setOnClickListener {

                AlertDialog.Builder(requireActivity())
                    .setTitle("Exit")
                    .setMessage("You sure want to Exit?")
                    .setCancelable(true)
                    .setPositiveButton("Yes") { dialog, _ ->
                        isLoggedIn = false
                        GlobalScope.launch {
                            userManager.storePrefs("", isLoggedIn)
                        }
                        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
            binding.addImage.setOnClickListener {
                checkingPermissions()

            }
            binding.btnUpdate.setOnClickListener {

                var email = binding.etEmail.text.toString()
                var username = binding.etUsername.text.toString()

                savePrefs()
                AlertDialog.Builder(requireActivity())
                    .setTitle("Update")
                    .setMessage("You sure want to Update?")
                    .setCancelable(true)
                    .setPositiveButton("Update") { dialog, _ ->
                        profileViewModel.updateUser(idProfile,username,email, resultImage)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()

            }
            binding.ivBack.setOnClickListener {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())

            }
        }
    }

    private fun savePrefs() {
        var username = binding.etUsername.text.toString()
        var isLoggedIn = true

        GlobalScope.launch {
            userManager.storePrefs(username, isLoggedIn)
        }
    }



    private fun getProfile(): Int {



        profileViewModel.images.observe(viewLifecycleOwner) {
            Glide.with(requireActivity()).load(it)
                .apply(RequestOptions.centerCropTransform()).error(
                    R.drawable.ic_baseline_profile_24
                ).into(binding.profileImage)
            resultImage = it
        }
        profileViewModel.username.observe(viewLifecycleOwner) {
            username = it
            binding.etUsername.setText(it)
        }
        profileViewModel.email.observe(viewLifecycleOwner) {
            email = it
            binding.etEmail.setText(it)
        }

        profileViewModel.id.observe(viewLifecycleOwner){
            idProfile = it
        }
//        userManager.userNameFlow.asLiveData().observe(requireActivity(), {
//            Thread {
//                val resultId = user_db?.UserDao()?.getId(it)
//                activity?.runOnUiThread {
//                    if (resultId != 0) {
//                        idProfile = resultId!!
//                        Thread {
//                            val result = user_db?.UserDao()?.getUser(resultId!!)
//                            activity?.runOnUiThread {
//                                if (result != null) {
//                                    binding.etEmail.setText(result.email)
//                                    binding.etUsername.setText(result.username)
//                                    password = result.password
//                                    rePassword = result.repassword
//                                    resultImage = result.images
//                                    Glide.with(requireActivity()).load(result.images)
//                                        .apply(RequestOptions.centerCropTransform()).error(
//                                        R.drawable.ic_baseline_profile_24
//                                    ).into(binding.profileImage)
//                                } else {
//                                    Toast.makeText(
//                                        requireContext(),
//                                        "Ggal fetch profile",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                }
//                            }
//                        }.start()
//                    }
//                }
//            }.start()
//        })


        return id
    }

    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(), android.Manifest.permission.CAMERA, arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                100
            )
        ) {
            chooseImageDialog()

        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, please allow permissions from App Settings.")
            .setPositiveButton("App Settings") { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", activity?.packageName, null)
                intent.data = uri
                startActivity(intent)
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.show()
    }

    private fun chooseImageDialog() {

        AlertDialog.Builder(requireActivity())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ ->
                openGallery()
            }.setNegativeButton("Camera") { _, _ ->
                openCamera()
            }.show()
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        galleryResult.launch("image/*")

    }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        resultImage = saveImageToInternalStorage(bitmap).toString()
        Glide.with(requireActivity())
            .load(bitmap)
            .apply(RequestOptions.centerCropTransform())
            .into(binding.profileImage)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(requireContext())
        var file = wrapper.getDir("RegisterUserImage", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

}
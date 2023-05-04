package com.mbs.workoutplanner.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mbs.workoutplanner.databinding.FragmentSignUpBinding
import com.mbs.workoutplanner.models.UserDataModel
import com.mbs.workoutplanner.view.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        auth = Firebase.auth
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        setupSignUpButton()
        with(binding) {
            listOf(email, password, reenterPassword, height, weight).forEach {
                it.errorSetup()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSignUpButton() {
        binding.registerButton.setOnClickListener {
            with(binding) {
                emailInput.error = if (email.text.isNullOrBlank()) "Required field." else null
                emailInput.error =
                    if (email.text.toString().emailIsValid()) null else "Invalid email"
                passwordInput.error = if (password.text.isNullOrBlank()) "Required field." else null
                passwordInput.error = if (password.text.toString().length < 8) "Password should have at least 8 characters." else null
                reenterPasswordInput.error = if (reenterPassword.text.isNullOrBlank()) "Required field." else null
                nameInput.error = if (name.text.isNullOrBlank()) "Required field." else null
                heightInput.error = if (height.text.isNullOrBlank()) "Required field." else null
                weightInput.error = if (weight.text.isNullOrBlank()) "Required field." else null
                if (emailInput.error == null && passwordInput.error == null && reenterPasswordInput.error == null && heightInput.error == null && weightInput.error == null) {
                    if (password.text.toString() == reenterPassword.text.toString()) {
                        auth.createUserWithEmailAndPassword(
                            email.text.toString(),
                            password.text.toString()
                        )
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Snackbar.make(
                                        binding.root,
                                        "Register Success, please log in",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    userViewModel.saveUser(UserDataModel(binding.name.text.toString(), "", binding.weight.text.toString().toDouble(), binding.height.text.toString().toLong(), 0.0))
                                    requireActivity().supportFragmentManager.popBackStack()
                                } else {
                                    Snackbar.make(
                                        binding.root,
                                        "Register error ${it.exception} + ${it.result}",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                    } else {
                        passwordInput.error = "Passwords don't match."
                    }
                }
            }
        }
    }


    /** Removes the error box when the editText receives new content */
    private fun TextInputEditText.errorSetup() {
        this.doAfterTextChanged {
            val layout = parent.parent as? TextInputLayout
            layout?.error = null
        }
    }

    private fun String.emailIsValid(): Boolean {
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        return EMAIL_REGEX.toRegex().matches(this);
    }

}
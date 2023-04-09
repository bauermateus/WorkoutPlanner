package com.mbs.workoutplanner.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mbs.workoutplanner.R
import com.mbs.workoutplanner.databinding.FragmentSignInBinding
import com.mbs.workoutplanner.view.activitys.MainActivity

class SignInFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        auth = Firebase.auth
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerGoogleActivityForResult()
        setupSignUpButton()
        setupSignInButton()
        setupAnonymousLogin()
        setupGoogleSignInButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSignInButton() {
        binding.loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        activity?.finish()
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        it.cause.toString() + "   " + it.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.login_fragment_container, SignUpFragment()).addToBackStack(null)
                .commit()
        }
    }

    private fun setupAnonymousLogin() {
        binding.loginAnonymous.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    activity?.finish()
                }
        }
    }

    private fun setupGoogleSignInButton() {
        binding.googleSignInButton.setOnClickListener {
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val signInClient = GoogleSignIn.getClient(requireActivity(), options)
            googleSignInLauncher.launch(signInClient.signInIntent)
        }
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credentials)
            .addOnCompleteListener {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                activity?.finish()
            }
            .addOnFailureListener {
                Log.e("google signin error", it.message.toString())
            }
    }

    private fun registerGoogleActivityForResult() {
        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).result
                    account?.let {
                        googleAuthForFirebase(it)
                    }
                }
            }
    }
}
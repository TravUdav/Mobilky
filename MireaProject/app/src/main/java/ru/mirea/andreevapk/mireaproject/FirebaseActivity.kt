package ru.mirea.andreevapk.mireaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.mirea.andreevapk.mireaproject.databinding.ActivityFirebaseBinding

class FirebaseActivity : AppCompatActivity() {

    companion object {
        private val TAG = FirebaseActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityFirebaseBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signInButton.setOnClickListener {
            signIn(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
        binding.createAccountButton.setOnClickListener {
            createAccount(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
        binding.signOutButton.setOnClickListener {
            signOut()
        }
        binding.homePageButton.setOnClickListener {
            goToHomePage()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            binding.statusTextView.text = getString(R.string.emailpassword_status_fmt, user.email, user.isEmailVerified)
            binding.detailTextView.text = getString(R.string.firebase_status_fmt, user.uid)
            binding.signInSection.visibility = View.GONE
            binding.signedInSection.visibility = View.VISIBLE
            binding.homePageButton.isEnabled = true
        } else {
            binding.statusTextView.text = getString(R.string.signed_out)
            binding.detailTextView.text = null
            binding.signInSection.visibility = View.VISIBLE
            binding.signedInSection.visibility = View.GONE
            binding.homePageButton.isEnabled = false
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount: $email")
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d(TAG, "createUserWithEmailAndPassword: success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "createUserWithEmailAndPassword: failure", it.exception)
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn: $email")
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d(TAG, "signInWithEmail: success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithEmail: failure", it.exception)
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                if (!it.isSuccessful) {
                    binding.statusTextView.text = getString(R.string.auth_failed)
                }
            }
    }

    private fun signOut() {
        mAuth.signOut()
        updateUI(null)
    }

    private fun goToHomePage() {
        binding.homePageButton.isEnabled = false

        Toast.makeText(this, "Redirecting to MainActivity...", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}

package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RegisterBtn.setOnClickListener { startActivity(Intent(this,Register::class.java)) }
        LoginButton.setOnClickListener {
            signIn()
        }
    }
    private fun signIn()
    {
        val user = findViewById(R.id.editTextFirstName) as EditText
        val pass = findViewById(R.id.editTextLastName) as EditText

        var Username = user.getText().toString().trim()
        var Password = pass.getText().toString().trim()

        if(Username.isEmpty()&&Password.isEmpty())
            Toast.makeText(this,"Enter details",Toast.LENGTH_SHORT).show()
        else
            mAuth.signInWithEmailAndPassword(Username,Password).addOnCompleteListener {
                task ->
                if(task.isSuccessful)
                {
                    val Current:FirebaseUser? = mAuth.getCurrentUser()
                    if(Current == null)
                        Toast.makeText(this,"Please try again later",Toast.LENGTH_SHORT).show()
                    else
                        if(Current.isEmailVerified) {
                            startActivity(Intent(this,LoginActivity :: class.java))
                            finish()
                            Toast.makeText(this, "SignIn Successful", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(this, "Email not verified", Toast.LENGTH_SHORT).show()
                            val user = mAuth.getCurrentUser()
                            user?.sendEmailVerification()?.addOnCompleteListener { task ->
                                if (task.isSuccessful)
                                    Toast.makeText(
                                        this,
                                        "Email sent successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else
                                    Toast.makeText(
                                        this,
                                        "Email not sent",
                                        Toast.LENGTH_SHORT
                                    ).show()

                            }
                        }
                }
                else
                {
                    Toast.makeText(this,"Sign in failed",Toast.LENGTH_SHORT).show()
                }
            }

    }
}

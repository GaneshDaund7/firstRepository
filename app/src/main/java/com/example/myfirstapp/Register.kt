package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.jvm.java as java1

class Register : AppCompatActivity() {

   val mAuth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        back.setOnClickListener { startActivity(Intent(this,MainActivity::class.java1)) }
        RegisterButton.setOnClickListener { create() }

    }

    private fun create()
    {
        val username = findViewById(R.id.EditTextFirstName) as EditText
        val pass = findViewById(R.id.EditTextLastName) as EditText
        val Repass = findViewById(R.id.EditTextPhone) as EditText

        var user = username.getText().toString().trim()
        var Pass = pass.getText().toString().trim()
        var rePass = Repass.getText().toString().trim()

        if(!(Pass==rePass))
            Toast.makeText(this,"Passwords are not matching",Toast.LENGTH_SHORT).show()
        else
            if(user.isEmpty() && Pass.isEmpty())
                Toast.makeText(this,"Enter Username or password",Toast.LENGTH_SHORT).show()
            else
                mAuth.createUserWithEmailAndPassword(user,Pass).addOnCompleteListener(this,
                    OnCompleteListener { task ->
                        if(task.isSuccessful)
                        {
                            val user = mAuth.getCurrentUser()
                            user?.sendEmailVerification()?.addOnCompleteListener { task ->
                                if(task.isSuccessful)
                                    Toast.makeText(this,"Email sent successfully",Toast.LENGTH_SHORT).show()
                                else
                                    Toast.makeText(this,"Email not sent",Toast.LENGTH_SHORT).show()

                            }
                            Toast.makeText(this,"User Registered Successfully",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity :: class.java1))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this,"User Registration Failed",Toast.LENGTH_SHORT).show()
                        }
                    })
    }
}

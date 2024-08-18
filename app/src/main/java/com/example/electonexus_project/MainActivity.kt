package com.example.electonexus_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.electonexus_project.ui.theme.ElectoNexus_projectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val email: EditText = findViewById(R.id.LEmailAddress)
        val password : EditText = findViewById(R.id.LPassword)
        val loginButton: Button = findViewById(R.id.LLoginButton)
        val signupButton : Button = findViewById(R.id.LSignupButton)

        signupButton.setOnClickListener {
            val username = email.text.toString()
            val password = password.text.toString()


            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            } else {

                Intent(this,Account_type::class.java).also { startActivity(it) }
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

            }
        }
    }
}


package LoginAndSignUp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.electonexus_project.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {

    private lateinit var signupButton: Button
    private lateinit var loginButton: Button

    private lateinit var un: EditText
    private lateinit var pw: EditText

    private lateinit var fbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        un = findViewById(R.id.LEmailAddress)
        pw = findViewById(R.id.LPassword)
        loginButton = findViewById(R.id.LLoginButton)
        signupButton = findViewById(R.id.LSignupButton)


        var b = true

        loginButton.setOnClickListener {
            b = checkUsername()
            if(b) {
                Toast.makeText(this,"Activity Changed",Toast.LENGTH_SHORT).show()
            }
        }
        signupButton.setOnClickListener {
            Intent(this, Account_type::class.java).also { startActivity(it) }
        }

    }

    private fun checkUsername(): kotlin.Boolean {
        var b = true
        var chk = false
        val username = un.text.toString()
        val password = pw.text.toString()
        fbref =
            FirebaseDatabase.getInstance("https://electonexusmain-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Account/Admin")

        if (username.isEmpty()) {
            un.error = "Please enter Username"
            b = false
        }
        if (password.isEmpty()) {
            pw.error = "Please enter password"
            b = false
        }
        if (b) {
            var chk= false
            fbref.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(this@MainActivity, "Username Found", Toast.LENGTH_SHORT).show()
                        chk = true
                        val tpw = dataSnapshot.child("pw").value
                        if(tpw == password){
                            Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this@MainActivity, "Invalid Password", Toast.LENGTH_SHORT).show()

                        }
                    }
                    else {
                        fbref =
                            FirebaseDatabase.getInstance("https://electonexusmain-default-rtdb.asia-southeast1.firebasedatabase.app")
                                .getReference("Account/Voter")
                        fbref.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Toast.makeText(this@MainActivity, "Username Found", Toast.LENGTH_SHORT)
                                        .show()
                                    val tpw = dataSnapshot.child("pw").value
                                    if (tpw == password) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Login Successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Invalid Password",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                } else {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Username Not Found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@MainActivity, "$error", Toast.LENGTH_SHORT).show()

                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity, "$error", Toast.LENGTH_SHORT).show()

                }
            })
        }
        return b
    }
}




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

    var type : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        un = findViewById(R.id.LEmailAddress)
        pw = findViewById(R.id.LPassword)
        loginButton = findViewById(R.id.LLoginButton)
        signupButton = findViewById(R.id.LSignupButton)



        loginButton.setOnClickListener {
            checkUsername()
        }
        signupButton.setOnClickListener {
            Intent(this, Account_type::class.java).also { startActivity(it) }
        }

    }

    private fun checkUsername() {
        var b = true
        val username = un.text.toString()
        val password = pw.text.toString()
        fbref =
            FirebaseDatabase.getInstance("https://electonexusmain-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Account")

        if (username.isEmpty()) {
            un.error = "Please enter Username"
            b = false
        }
        if (password.isEmpty()) {
            pw.error = "Please enter password"
            b = false
        }
        if (b) {
            fbref.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val tpw = dataSnapshot.child("pw").value
                        if (tpw == password) {
                            Toast.makeText(
                                this@MainActivity,
                                "Login Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            type = dataSnapshot.child("atype").value.toString()
                            if(type=="A"){
                                Toast.makeText(this@MainActivity, "Login To Admin", Toast.LENGTH_SHORT).show()
                                Intent(this@MainActivity, admin_sign_up::class.java).also { startActivity(it) }
                            }
                            else if(type=="V"){
                                Toast.makeText(this@MainActivity, "Login to Voter", Toast.LENGTH_SHORT).show()
                                Intent(this@MainActivity, voter_sign_up::class.java).also { startActivity(it) }
                            }

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
}




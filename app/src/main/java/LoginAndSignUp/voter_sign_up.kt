package LoginAndSignUp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.electonexus_project.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class voter_sign_up : ComponentActivity() {

    private lateinit var nameInsert: EditText
    private lateinit var unInsert: EditText
    private lateinit var pwInsert: EditText

    private lateinit var fbref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_voter_sign_up)
        nameInsert = findViewById(R.id.LVoterName)
        unInsert = findViewById(R.id.LVoterEmailAddress)
        pwInsert = findViewById(R.id.LVoterPassword)

        fbref = FirebaseDatabase.getInstance("https://electonexusmain-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Account")
        val signup : Button = findViewById(R.id.LVoterSignup)

        var noerror : kotlin.Boolean = true

        signup.setOnClickListener {
                noerror = saveVoterData()
                if(noerror){
                Intent(this, MainActivity::class.java).also { startActivity(it) }
                Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                finish()
                }
        }
    }

    private fun saveVoterData():Boolean{
        var b: Boolean = true
        val name = nameInsert.text.toString()
        val username = unInsert.text.toString()
        val password = pwInsert.text.toString()

        if(name.isEmpty()){
            nameInsert.error = "Please enter name"
            b=false
        }
        if(username.isEmpty()){
            nameInsert.error = "Please enter username"
            b=false
        }
        if(password.isEmpty()){
            nameInsert.error = "Please enter password"
            b=false
        }
        if(b) {
            val voter = VoterModel(name,username, password,"V")
            fbref.child(username).setValue(voter).addOnCompleteListener {
                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "${err.message}", Toast.LENGTH_SHORT).show()
            }
        }
        return b
    }
}
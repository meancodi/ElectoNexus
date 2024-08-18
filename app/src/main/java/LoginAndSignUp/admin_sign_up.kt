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

class admin_sign_up : ComponentActivity() {
    private lateinit var eninsert : EditText
    private lateinit var nameinsert : EditText
    private lateinit var uninsert : EditText
    private lateinit var pwinsert : EditText

    private lateinit var fbref1 : DatabaseReference
    private lateinit var fbref2 : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_sign_up)

        eninsert = findViewById(R.id.LSignupElectionName)
        nameinsert = findViewById(R.id.LSignupName)
        uninsert = findViewById(R.id.LSignupEmailAddress)
        pwinsert = findViewById(R.id.LSignupPassword)

        fbref1 = FirebaseDatabase.getInstance("https://electonexusmain-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Account")
        val signup :  Button = findViewById(R.id.LSignupAdmin)


        signup.setOnClickListener {
                val noerror: Boolean = addAdmin()
                if (noerror) {
                    Intent(this, MainActivity::class.java).also { startActivity(it) }
                    finish()
                }
            }


    }

    private fun addAdmin(): kotlin.Boolean {
        var b  =true
        val en = eninsert.text.toString()
        val name = nameinsert.text.toString()
        val un = uninsert.text.toString()
        val pw = pwinsert.text.toString()

        if(en.isEmpty()){
            eninsert.error="Please enter Election name"
            b=false
        }
        if(name.isEmpty()){
            nameinsert.error="Please enter name"
            b=false

        }
        if(un.isEmpty()){
            uninsert.error="Please enter username"
            b=false

        }
        if(pw.isEmpty()){
            pwinsert.error="Please enter password"
            b=false

        }
        val eId = (10000000..99999999).random()

        if(b) {
           val admin = AdminModel(en,name,un,pw,"A",eId)
           fbref1.child(un).setValue(admin).addOnCompleteListener {
               Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
               fbref2.child(un)
           }.addOnFailureListener { err ->
               Toast.makeText(this, "${err.message}", Toast.LENGTH_SHORT).show()
           }
       }
        return b
    }
}
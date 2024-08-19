package com.example.electonexus_project;

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class Voterrequest : ComponentActivity() {

    private var lastTextViewId: Int? = R.id.textViewvr
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_voterrequest)
        val i:Int=0
        for(i in 0..10) {
            createView("bello+$i","killo+$i")
        }


    }
    private fun createView(name : String,uname:String){
        val containerLayout: ConstraintLayout = findViewById(R.id.containervoterrequestlist)
        val newTextView = TextView(this).apply {
            id = View.generateViewId() // Generate a unique ID
            text = name
            textSize = 18f
            setPadding(16, 16, 16, 16)
        }
        val yButton = Button(this).apply {
            id = View.generateViewId() // Generate a unique ID
            text = "Yes"
            tag = "ybtn+$uname"
            textSize = 18f
            setPadding(16, 16, 16, 16)

        }
        val nButton = Button(this).apply {
            id = View.generateViewId() // Generate a unique ID
            text = "No"
            tag = "nbtn+$uname"
            textSize = 18f
            setPadding(16, 16, 16, 16)

        }
        yButton.setOnClickListener {
            val yy : String = "yes + $uname"
            Toast.makeText(this, yy, Toast.LENGTH_SHORT).show()
        }
        nButton.setOnClickListener {
            val yy : String = "no + $uname"
            Toast.makeText(this, yy, Toast.LENGTH_SHORT).show()
        }
        containerLayout.addView(newTextView)
        containerLayout.addView(yButton)
        containerLayout.addView(nButton)

        applyConstraintsToView(containerLayout, newTextView,yButton,nButton)

        lastTextViewId = newTextView.id
    }
    private fun applyConstraintsToView(parent: ConstraintLayout, textView: TextView,ybutton : Button,nbutton: Button) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(parent)


        constraintSet.connect(textView.id, ConstraintSet.TOP, lastTextViewId!!, ConstraintSet.BOTTOM, 16)
        constraintSet.connect(ybutton.id, ConstraintSet.TOP, lastTextViewId!!, ConstraintSet.BOTTOM, 16)
        constraintSet.connect(nbutton.id, ConstraintSet.TOP, lastTextViewId!!, ConstraintSet.BOTTOM, 16)


        constraintSet.connect(textView.id, ConstraintSet.START, parent.id, ConstraintSet.START,dpToPx(20f) )
        constraintSet.connect(ybutton.id, ConstraintSet.START, textView.id, ConstraintSet.START,dpToPx(130f) )
        constraintSet.connect(nbutton.id, ConstraintSet.START, ybutton.id, ConstraintSet.END,dpToPx(10f) )



        constraintSet.applyTo(parent)
    }
    private fun dpToPx(dp: Float): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
    private fun getCredentialsFile(): String {
        try {
            val fileName = "credentials.txt"
            val fileInputStream: FileInputStream = openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            var username: String? = null
            var Acctype: String? = null

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                line?.let {
                    when {
                        it.startsWith("eID:") -> {
                            username = it.substringAfter("eID:").trim()
                            return username!!
                        }
                        it.startsWith("Acctype:") -> {
                            Acctype = it.substringAfter("Acctype:").trim()
                        }
                    }
                }
            }

            bufferedReader.close()

            if (username != null && Acctype != null) {
                Toast.makeText(this, "Username: $username", Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Password: $Acctype", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Incomplete credentials found", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(this, "No credentials found", Toast.LENGTH_SHORT).show()
        }
        return ""
    }
}
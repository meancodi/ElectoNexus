package com.example.electonexus_project

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class voter_cast_vote : ComponentActivity() {
    private var lastTextViewId: Int? = R.id.Vcv_tv
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_cast_vote)

        val Vun: String = getCredentialsFile()
        var i : Int =0

        for(i in 0..30){

            createTextView("lanja lapaki $i")
        }

    }
    private fun createTextView(name : String){
        val containerLayout: ConstraintLayout = findViewById(R.id.containercastvote)
        val newTextView = TextView(this).apply {
            id = View.generateViewId() // Generate a unique ID
            text = name
            textSize = 18f
            setPadding(16, 16, 16, 16)
        }

        containerLayout.addView(newTextView)

        applyConstraintsToView(containerLayout, newTextView)

        lastTextViewId = newTextView.id
    }
    private fun applyConstraintsToView(parent: ConstraintLayout, textView: TextView) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(parent)


        constraintSet.connect(textView.id, ConstraintSet.TOP, lastTextViewId!!, ConstraintSet.BOTTOM, 16)

        constraintSet.connect(textView.id, ConstraintSet.START, parent.id, ConstraintSet.START,dpToPx(60f) )



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
            //var Acctype: String? = null

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                line?.let {
                    when {
                        it.startsWith("Username:") -> {
                            username = it.substringAfter("eID:").trim()
                            return username!!
                        }
                        it.startsWith("Acctype:") -> {
                            // Acctype = it.substringAfter("Acctype:").trim()
                        }
                    }
                }
            }

            bufferedReader.close()



        } catch (e: Exception) {
            Toast.makeText(this, "No credentials found", Toast.LENGTH_SHORT).show()
        }
        return ""
    }
}
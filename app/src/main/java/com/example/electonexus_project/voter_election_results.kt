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

class voter_election_results : ComponentActivity() {
    private var lastTextViewId: Int? = R.id.Vr_check
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_voter_election_results)

       // val Vun: String = getCredentialsFile()
        var i: Int = 0


        for (i in 0..30) {

            createTextView("ninja $i ")
        }

    }

    private fun createTextView(name: String) {
        val containerLayout: ConstraintLayout = findViewById(R.id.containervelectionresults)
        val newTextView = TextView(this).apply {
            id = View.generateViewId() // Generate a unique ID
            text = name
            textSize = 18f
            setPadding(16, 16, 16, 16)
        }
        val newTextView1 = TextView(this).apply {
            id = View.generateViewId() // Generate a unique ID
            text = name
            textSize = 18f
            setPadding(16, 16, 16, 16)
        }

        containerLayout.addView(newTextView)
        containerLayout.addView(newTextView1)

        applyConstraintsToView(containerLayout, newTextView,newTextView1)

        lastTextViewId = newTextView.id
    }

    private fun applyConstraintsToView(parent: ConstraintLayout, textView: TextView,textView1: TextView) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(parent)

        constraintSet.connect(textView.id, ConstraintSet.TOP, lastTextViewId!!, ConstraintSet.BOTTOM, 16)

        constraintSet.connect(textView.id, ConstraintSet.START, parent.id, ConstraintSet.START,dpToPx(60f) )

        constraintSet.connect(textView1.id, ConstraintSet.TOP, lastTextViewId!!, ConstraintSet.BOTTOM, 16)

        constraintSet.connect(textView1.id, ConstraintSet.START, textView.id, ConstraintSet.START,dpToPx(60f) )



        constraintSet.applyTo(parent)
    }

    private fun dpToPx(dp: Float): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }



}
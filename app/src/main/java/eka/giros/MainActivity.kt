package eka.giros

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    private lateinit var usernameButton: Button
    private lateinit var usernameEditText: EditText


    private fun initComponent() {
        usernameButton = findViewById(R.id.usernameButton)
        usernameEditText = findViewById(R.id.usernameEditText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.github)

        initComponent()

        usernameButton.setOnClickListener {
            usernameButton.isEnabled = false
            usernameButton.text = resources.getString(R.string.loadingText)


            Handler(Looper.getMainLooper()).postDelayed({
                usernameButton.text = resources.getString(R.string.usernameButton)
                usernameButton.isEnabled = true
            }, 3000)
        }
    }
}

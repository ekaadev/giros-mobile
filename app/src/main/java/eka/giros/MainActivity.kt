package eka.giros

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import eka.giros.model.UserRequest
import eka.giros.model.UserResponse
import eka.giros.repository.RetrofitClient
import eka.giros.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private lateinit var userRepository: UserRepository

    private lateinit var usernameButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var resultRoastingTextView: TextView

    // init component
    private fun initComponent() {
        usernameButton = findViewById(R.id.usernameButton)
        usernameEditText = findViewById(R.id.usernameEditText)
        resultRoastingTextView = findViewById(R.id.resultRoastingTextView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.github)

        initComponent()



        // init user repository
        userRepository = UserRepository(RetrofitClient.instance)

        // action if click on button
        usernameButton.setOnClickListener {
            resultRoastingTextView.text = resources.getString(R.string.loadingResult)
            usernameButton.isEnabled = false
            usernameButton.text = resources.getString(R.string.loadingText)

            // receive data from client
            val usernameGithub = usernameEditText.text.toString()

            if (usernameGithub.isEmpty()) {
                resultRoastingTextView.text = resources.getString(R.string.usernameEmpty)
                usernameButton.isEnabled = true
                return@setOnClickListener
            }

            // data yang akan dipost
            val requestBody = UserRequest(
                model = "gemini",
                language = "auto"
            )

            userRepository.createRoasting(usernameGithub, requestBody).enqueue(object: Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        val userResponse = response.body()
                        if (userResponse != null) {
                            resultRoastingTextView.text = userResponse.roasting
                        }
                        Log.i("MainActivity", "Roasting: $userResponse")
                    } else {
                        resultRoastingTextView.text = response.toString()
                        Log.i("MainActivity", "Failed: ${response.errorBody()}")
                    }

                    usernameButton.isEnabled = true
                    usernameButton.text = resources.getString(R.string.usernameButton)
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    resultRoastingTextView.text = t.message.toString()
                    Log.e("MainActivity", "Error: ${t.message}")

                    usernameButton.isEnabled = true
                    usernameButton.text = resources.getString(R.string.usernameButton)
                }
            })
        }
    }
}

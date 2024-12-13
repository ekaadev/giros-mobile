package eka.giros

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import eka.giros.model.UserRequest
import eka.giros.model.UserResponse
import eka.giros.repository.UserRepository
import eka.giros.repository.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import eka.giros.Helper

class GithubRoasting : Fragment() {

    private lateinit var userRepository: UserRepository
    private lateinit var usernameButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var resultRoastingTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_github_roasting, container, false)

        // Initialize components
        usernameButton = view.findViewById(R.id.usernameButton)
        usernameEditText = view.findViewById(R.id.usernameEditText)
        resultRoastingTextView = view.findViewById(R.id.resultRoastingTextView)

        userRepository = UserRepository(RetrofitClient.instance)

        // Set up button click listener
        usernameButton.setOnClickListener {
            handleRoasting()
        }

        return view
    }

    private fun handleRoasting() {
        resultRoastingTextView.text = resources.getString(R.string.loadingResult)
        usernameButton.isEnabled = false
        usernameButton.text = resources.getString(R.string.loadingText)

        val usernameGithub = usernameEditText.text.toString()
        if (usernameGithub.isEmpty()) {
            resultRoastingTextView.text = resources.getString(R.string.usernameEmpty)
            usernameButton.isEnabled = true
            return
        }

        if (!Helper.isNetworkConnected(requireContext())) {
            resultRoastingTextView.text = resources.getString(R.string.noInternet)
            usernameButton.isEnabled = true
            usernameButton.text = resources.getString(R.string.usernameButton)
            return
        }

        val requestBody = UserRequest(
            model = "gemini",
            language = "auto"
        )

        userRepository.createRoasting(usernameGithub, requestBody).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    resultRoastingTextView.text = response.body()?.roasting ?: "No roasting found"
                    Log.i("GithubRoasting", "Roasting: ${response.body()}")
                } else {
                    val errorMessage = response.errorBody()?.string()
                    val errorJson = try {
                        if (errorMessage != null) {
                            JSONObject(errorMessage).getString("error")
                        } else {
                            "Unknown error"
                        }
                    } catch (e: Exception) {
                        "Unknown error"
                    }

                    resultRoastingTextView.text = errorJson
                    Log.i("GithubRoasting", "Failed: $errorJson")
                }
                usernameButton.isEnabled = true
                usernameButton.text = resources.getString(R.string.usernameButton)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                resultRoastingTextView.text = t.message
                Log.e("GithubRoasting", "Error: ${t.message}")
                usernameButton.isEnabled = true
                usernameButton.text = resources.getString(R.string.usernameButton)
            }
        })
    }
}

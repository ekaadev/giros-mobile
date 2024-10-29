package eka.giros.repository

import eka.giros.api.ApiService
import eka.giros.model.UserRequest
import eka.giros.model.UserResponse
import retrofit2.Call

class UserRepository(private val apiService: ApiService) {
    fun createRoasting(username: String, requestBody: UserRequest): Call<UserResponse> {
        return apiService.createRoasting(username, requestBody)
    }
}
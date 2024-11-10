package eka.giros.api

import eka.giros.model.UserRequest
import eka.giros.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("roast")
    fun createRoasting(
        @Query("username") username: String,
        @Body requestBody: UserRequest
    ): Call<UserResponse>
}
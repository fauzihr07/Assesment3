package org.d3if3012.assesment3.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3012.assesment3.model.Motors
import org.d3if3012.assesment3.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MotorApiService {
    @GET("api_fauzi.php")
    suspend fun getHewan(
        @Header("Authorization") userId: String
    ): List<Motors>

    @Multipart
    @POST("api_fauzi.php")
    suspend fun postMotor(
        @Header("Authorization") userId: String,
        @Part("namaMotor") namaMotor: RequestBody,
        @Part("cc_motor") ccMotor: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("api_fauzi.php")
    suspend fun deleteMotor(
        @Header("Authorization") userId: String,
        @Query("id") id: String
    ): OpStatus

}

object MotorApi {
    val service: MotorApiService by lazy {
        retrofit.create(MotorApiService::class.java)
    }

    fun getMotorUrl(imageId: String): String{
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class  ApiStatus { LOADING, SUCCESS, FAILED }
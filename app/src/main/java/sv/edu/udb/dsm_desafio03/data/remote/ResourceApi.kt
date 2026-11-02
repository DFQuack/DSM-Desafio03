package sv.edu.udb.dsm_desafio03.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ResourceApi {
    @GET("resources")
    suspend fun getAll(): List<ResourceDto>

    @POST("resources")
    suspend fun create(@Body resource: ResourceDto): ResourceDto

    @PUT("resources/{id}")
    suspend fun update(@Path("id") id: String, @Body resource: ResourceDto): ResourceDto

    @DELETE("resources/{id}")
    suspend fun delete(@Path("id") id: String)
}
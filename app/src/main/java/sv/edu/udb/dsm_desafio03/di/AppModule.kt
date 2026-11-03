package sv.edu.udb.dsm_desafio03.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import sv.edu.udb.dsm_desafio03.data.remote.ResourceApi
import sv.edu.udb.dsm_desafio03.data.repository.DefaultResourceRepository
import sv.edu.udb.dsm_desafio03.data.repository.ResourceRepository
import javax.inject.Singleton

@Module // To provide dependencies that Hilt cannot create automatically (interfaces in this case)
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://6ae77d0056b2ee1e54406ee4.mockapi.io/api/"

    /*
    * @Provides: Can't inject into an external library (Retrofit),
    * so when ResourceApi is called, it will run this function and use the result.
    * @Singleton: A single instance for the whole app.
    */
    @Provides @Singleton
    fun provideResourceApi(): ResourceApi {
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ResourceApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton // When ResourceRepository is called, Hilt returns DefaultResourceRepository
    abstract fun bindRepository(impl: DefaultResourceRepository): ResourceRepository
}
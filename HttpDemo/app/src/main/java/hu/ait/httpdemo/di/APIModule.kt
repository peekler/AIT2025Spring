package hu.ait.httpdemo.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.ait.httpdemo.network.MoneyAPI
import hu.ait.httpdemo.network.NasaAPI
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MoneyExchangeAPIHost
//"https://api.exchangerate-api.com/"


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NasaAPIHost
// https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=2015-6-3&api_key=DEMO_KEY




@Module
@InstallIn(SingletonComponent::class)
object APIModule {
    @Provides
    @MoneyExchangeAPIHost
    @Singleton
    fun provideMoneyExchangeAPIRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("http://data.fixer.io/")
            .addConverterFactory(
                Json{ ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()) )
            .client(client)
            .build()
    }

    @Provides
    @NasaAPIHost
    @Singleton
    fun provideNasaAPIRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/")
            .addConverterFactory(
                Json{ ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()) )
            .client(client)
            .build()
    }


    @Provides
    @Singleton
    fun provideMoneyAPI(@MoneyExchangeAPIHost retrofit: Retrofit): MoneyAPI {
        return retrofit.create(MoneyAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNasaAPI(@NasaAPIHost retrofit: Retrofit): NasaAPI {
        return retrofit.create(NasaAPI::class.java)
    }

}

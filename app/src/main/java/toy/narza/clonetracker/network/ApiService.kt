package toy.narza.clonetracker.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import toy.narza.clonetracker.network.`interface`.IClient

object ApiService {

    private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client : OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }.build()

    private val retrofit: IClient by lazy {
        Retrofit
            .Builder()
            .baseUrl(IClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(IClient::class.java)
    }

    fun getProgress() = retrofit.getData()
}
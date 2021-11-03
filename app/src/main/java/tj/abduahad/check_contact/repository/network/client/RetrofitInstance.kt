package tj.abduahad.check_contact.repository.network.client

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tj.abduahad.check_contact.repository.network.TokenRepository
import tj.abduahad.check_contact.repository.network.interceptor.ErrorInterceptor
import tj.abduahad.check_contact.repository.network.interceptor.HeaderInterceptor
import tj.abduahad.check_contact.repository.network.interceptor.LoggingInterceptor
import java.util.concurrent.TimeUnit


class RetrofitInstance(val tokenRepository: TokenRepository) {
    private var retrofit: Retrofit? = null
    private val TIMEOUT = 45

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            val baseUrl: String = getBaseUrl()
            val client = getOkHttpClient()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .callTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(ErrorInterceptor())
            .addInterceptor(HeaderInterceptor(tokenRepository))
        builder.addInterceptor(LoggingInterceptor())
        return builder.build()
    }

    private fun getBaseUrl(): String = "https://api.boomware.com/v1/"
}
package tj.abduahad.check_contact.repository.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import tj.abduahad.check_contact.repository.network.TokenRepository
import java.util.*

/**
 * HeaderInterceptor - предназначено для добавления headers в запросов
 * */
class HeaderInterceptor(val tokenRepository: TokenRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Accept-Language", Locale.getDefault().language)
        request.addHeader("User-Agent", "MBE")
        request.addHeader("ACCEPT", "application/json")
        tokenRepository.getToken()?.let {
            request.addHeader("Authorization", it)
        }
        return chain.proceed(request.build())
    }
}
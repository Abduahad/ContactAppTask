package tj.abduahad.check_contact.repository.network.interceptor

import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import tj.abduahad.check_contact.core.ErrorModel
import tj.abduahad.check_contact.core.errors.*

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            400 -> {
                throw BadRequest(parseError(response.body))
            }
            401 -> throw Unauthorized
            403 -> throw Forbidden(response.message)
            500 -> throw InternalServerError
            502 -> throw BadGateway
            503 -> throw ServiceUnavailable
        }
        return response
    }

    private fun parseError(body: ResponseBody?): ErrorModel? {
        try {
            if (body != null) {
                val json = body.string()
                if (json.isNotEmpty()) {
                    return Gson().fromJson<ErrorModel>(json, ErrorModel::class.java)
                }
            }
        } catch (ex: Exception) {
            ex.message?.let { Log.e(ErrorInterceptor::class.java.simpleName, it) }
        }
        return null
    }

}
package tj.abduahad.check_contact.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response
import tj.abduahad.check_contact.core.errors.NoInternetConnection
import tj.abduahad.check_contact.repository.base.ResourceRepository
import tj.abduahad.check_contact.repository.network.client.APIService
import tj.abduahad.check_contact.repository.network.NetworkResult
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseViewModel : ViewModel(), KoinComponent {
    //protected val localStorage:LocalStorageRepository by inject()
    protected val apiService: APIService by inject()
    protected val resRepository: ResourceRepository by inject()
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    protected val errorHandler: MutableLiveData<Any> = MutableLiveData()
    var isLoading: LiveData<Boolean> = _isLoading

    fun setLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }

    fun getErrors(): LiveData<Any?> = errorHandler

    protected suspend fun <T> getResult(call: suspend () -> T): NetworkResult<T> {
        try {
            val response: Response<T> = call() as Response<T>
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                    return NetworkResult.success(body)
                else
                    return NetworkResult.success(true as T)
            } else {
                return NetworkResult.error(response.body(), response.message())
            }
        } catch (ex: UnknownHostException) {
            return NetworkResult.error(NoInternetConnection)
        } catch (ex: SocketTimeoutException) {
            return NetworkResult.error(NoInternetConnection)
        } catch (ex: Throwable) {
            return NetworkResult.error(ex)
        } catch (io: Exception) {
            return NetworkResult.error(io)
        }
    }

    //abstract fun getScreenName():String?
}

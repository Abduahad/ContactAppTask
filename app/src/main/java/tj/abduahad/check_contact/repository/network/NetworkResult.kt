package tj.abduahad.check_contact.repository.network



data class NetworkResult<out T>(val status: NetworkStatus, val data: Any?, val message: String? =null) {

    fun isSuccess():Boolean = status.equals(NetworkStatus.SUCCESS)

    companion object {
        fun <T> success(data: T): NetworkResult<T> = NetworkResult(status = NetworkStatus.SUCCESS, data = data, message = null)

        fun <T> error(data: Any?,  message: String?=null): NetworkResult<T> = NetworkResult(status = NetworkStatus.ERROR, data = data, message = message)
    }

}
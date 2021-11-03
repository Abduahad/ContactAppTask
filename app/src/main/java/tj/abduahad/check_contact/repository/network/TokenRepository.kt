package tj.abduahad.check_contact.repository.network


interface TokenRepository {
    fun setToken(token: String)

    fun getToken(): String?
}
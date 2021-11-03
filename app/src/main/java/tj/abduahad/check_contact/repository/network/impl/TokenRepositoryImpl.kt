package tj.abduahad.check_contact.repository.network.impl

import tj.abduahad.check_contact.repository.network.TokenRepository


class TokenRepositoryImpl : TokenRepository {
    private var _token: String? = "Basic MjE2NmQ4ODM1ZDNjZDZkYjQ5MTQyZjFjMmMwZTRkNTc6ZDMzNTA3YTA="

    override fun setToken(token: String) {
        this._token = token
    }

    override fun getToken(): String? {
        return _token
    }

}
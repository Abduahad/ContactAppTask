package tj.abduahad.check_contact.repository.network.client

import retrofit2.Response

import retrofit2.http.*
import tj.abduahad.check_contact.repository.model.NumberStatusJSONModel

interface APIService {

    @FormUrlEncoded
    @POST("insight/hlr")
    suspend fun checkNumber(@Field("number") number: String): Response<NumberStatusJSONModel>

}
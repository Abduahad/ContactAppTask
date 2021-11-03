package tj.abduahad.check_contact.repository.model

import com.google.gson.annotations.SerializedName

class NumberStatusJSONModel{
    @SerializedName("number")
    var number:String? = null
    @SerializedName("status")
    var status:String? = null

    fun getNumberWithoutSymbols() = number?.replace("[^0-9]".toRegex(),"")
}
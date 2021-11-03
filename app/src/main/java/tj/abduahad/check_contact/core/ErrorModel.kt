package tj.abduahad.check_contact.core

import com.google.gson.annotations.SerializedName

/**
 * Created by ABDUAHAD FAIZULLOEV on 08,Июнь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
class ErrorModel {
    @SerializedName("errorCode")
    var code:Int = 0
    @SerializedName("errorReason")
    var message:String? = null
}
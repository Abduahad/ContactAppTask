package tj.abduahad.check_contact.core.errors

import okio.IOException

open class Error(code: Int, msg: String?=null) : IOException("$msg")
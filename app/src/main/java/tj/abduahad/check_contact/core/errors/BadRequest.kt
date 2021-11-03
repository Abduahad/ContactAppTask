package tj.abduahad.check_contact.core.errors

import tj.abduahad.check_contact.core.ErrorModel

class BadRequest(val error: ErrorModel?) : Error(400,error?.message)
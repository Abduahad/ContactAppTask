package tj.abduahad.check_contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tj.abduahad.check_contact.core.BaseViewModel
import tj.abduahad.check_contact.repository.model.ContactData
import tj.abduahad.check_contact.repository.model.NumberStatusJSONModel

/**
 * Created by ABDUAHAD FAIZULLOEV on 03,ноябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
class MainViewModel : BaseViewModel() {
    val contactArray: ArrayList<ContactData> = ArrayList()
    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

    fun checkContact(contactData: ContactData): LiveData<Boolean> {
        val result: MutableLiveData<Boolean> = MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            setLoading(true)
            if (!checkIsHasContact(contactData.msisdn)) {
                val response = getResult(call = { apiService.checkNumber(contactData.msisdn) })
                if (response.isSuccess()) {
                    val data: NumberStatusJSONModel = response.data as NumberStatusJSONModel
                    contactData.status = data.status
                    if (data.status == DELIVERED) {
                        result.postValue(true)
                    } else {
                        result.postValue(false)
                    }
                    addContact(contactData)
                } else {
                    response.data?.let {
                        errorHandler.postValue(it)
                    }
                }
            } else {
                _message.postValue(resRepository.getString(R.string.contact_already_exists))
            }
            setLoading(false)
        }
        return result
    }

    fun fetchContacts() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    private fun checkIsHasContact(msisdn: String): Boolean {
        for (item in contactArray) {
            if (item.msisdn == msisdn) {
                return true
            }
        }
        return false
    }

    private fun addContact(contactData: ContactData) {
        contactArray.add(contactData)
    }

    private fun updateContact(contactData: ContactData) {
        for (i in 0..contactArray.lastIndex) {
            if (contactData.msisdn.equals(contactArray.get(i))) {
                contactArray.set(i, contactData)
                break
            }
        }
    }

    companion object {
        const val DELIVERED = "DELIVERED"
        const val REJECTED = "REJECTED"
    }
}
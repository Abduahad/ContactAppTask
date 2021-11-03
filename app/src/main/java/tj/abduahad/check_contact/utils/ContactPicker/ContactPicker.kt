package tj.abduahad.check_contact.utils.ContactPicker

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by ABDUAHAD FAIZULLOEV on 09,октябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
class ContactPicker(private val activity: AppCompatActivity) :
    ActivityResultCallback<ActivityResult> {
    private var resultLauncher: ActivityResultLauncher<Intent>
    private var selectedContact: MutableLiveData<ContactModel> = MutableLiveData()

    init {
        resultLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this
        )
    }

    @SuppressLint("Range")
    override fun onActivityResult(result: ActivityResult?) {
        if (result?.resultCode == AppCompatActivity.RESULT_OK && result.data?.data != null) {
            val cursor: Cursor? = activity.getContentResolver().query(
                result.data?.data!!, arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME),
                null,
                null,
                null
            )

            if (cursor != null && cursor.moveToFirst()) {
                val msisdn = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                selectedContact.value = ContactModel(msisdn.replace("[^0-9]".toRegex(), ""), msisdn, displayName)
            }
        }
    }

    fun openContactPicker(): ContactPicker {
        val pickContactIntent = Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"))
        pickContactIntent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        resultLauncher.launch(pickContactIntent)
        return this
    }

    fun getSelectedContact(): LiveData<ContactModel> = selectedContact
}
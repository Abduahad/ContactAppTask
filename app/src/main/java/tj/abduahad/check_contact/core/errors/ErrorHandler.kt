package tj.abduahad.check_contact.core.errors

import android.content.Context
import android.util.Log
import android.widget.Toast
import tj.abduahad.check_contact.R
import java.net.UnknownHostException

class ErrorHandler(context: Context, errorException: Any?) {
    init {
        try {
            when (errorException) {
                is BadRequest -> {
                    errorException.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }

                is Forbidden -> {
                    errorException.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }

                is InternalServerError -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_message),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ServiceUnavailable -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_connection),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is UnknownHostException -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_connection_timeout),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is NoInternetConnection -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_connection_timeout),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (ex: Exception) {
            ex.message?.let { Log.e(this::class.java.simpleName, it) }
        }
    }
}
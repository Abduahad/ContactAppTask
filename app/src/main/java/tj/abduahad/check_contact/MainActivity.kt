package tj.abduahad.check_contact

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import tj.abduahad.check_contact.core.errors.ErrorHandler
import tj.abduahad.check_contact.repository.model.ContactData
import tj.abduahad.check_contact.utils.ContactPicker.ContactPicker


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var contactPicker: ContactPicker
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        contactPicker = ContactPicker(this)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener(this)
        progressBar = findViewById(R.id.progress)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.isLoading.observe(this, {
            if (it) {
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })

        viewModel.getErrors().observe(this, Observer {
            ErrorHandler(this, it)
        })

        viewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        contactPicker.getSelectedContact().observe(this, Observer {
            checkNumber(ContactData(it.msisdn, it.displayNumber, it.displayName))
        })
        adapter = MainAdapter(viewModel.contactArray)
        recyclerView.adapter = adapter
        viewModel.fetchContacts()

        Toast.makeText(this, "Чтобы выбрать контакт нажмите FAB - кнопку :)", Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View) {
        contactPicker.openContactPicker()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkNumber(contactData: ContactData) {
        viewModel.checkContact(contactData).observe(this, Observer {
            adapter.notifyDataSetChanged()
        })
    }
}
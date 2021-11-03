package tj.abduahad.check_contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tj.abduahad.check_contact.repository.model.ContactData

class MainAdapter(private val myDataset:ArrayList<ContactData>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textViewTitle:TextView
        val textViewSubTitle:TextView
        val imageView:ImageView
        init {
            textViewTitle=itemView.findViewById(R.id.textViewTitle) as TextView
            textViewSubTitle=itemView.findViewById(R.id.textViewSubTitle) as TextView
            imageView=itemView.findViewById(R.id.imageView) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false))
    }

    override fun getItemCount(): Int {
       return myDataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: ContactData=myDataset.get(position)
        holder.textViewTitle.text=data.displayName
        holder.textViewSubTitle.text=data.displayNumber
        when(data.status){
            "DELIVERED"->{
                holder.imageView.setImageResource(R.drawable.ic_baseline_check_circle_24)
            }
            else ->{
                holder.imageView.setImageResource(R.drawable.ic_baseline_error_outline_24)
            }
        }
    }

}
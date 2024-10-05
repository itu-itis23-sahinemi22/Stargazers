import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emrsa.stargazers.ListItem
import com.emrsa.stargazers.databinding.ItemBoldDescriptionBinding
import com.emrsa.stargazers.databinding.ItemDescriptionBinding
import com.emrsa.stargazers.databinding.ItemImageDescriptionBinding

class ImageListAdapter(
    private val items: List<ListItem>,
    private val onItemClicked: (Int) -> Unit // Tıklanan öğenin pozisyonunu geri döndüren lambda
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_DESCRIPTION = 0
        const val VIEW_TYPE_IMAGE_DESCRIPTION = 1
        const val VIEW_TYPE_BOLD_DESCRIPTION = 2
    }

    // ViewBinding kullanan ViewHolder sınıfları
    inner class DescriptionViewHolder(val binding: ItemDescriptionBinding) : RecyclerView.ViewHolder(binding.root)
    inner class BoldDescriptionViewHolder(val binding: ItemBoldDescriptionBinding) : RecyclerView.ViewHolder(binding.root)
    inner class ImageDescriptionViewHolder(val binding: ItemImageDescriptionBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Öğeye tıklanabilirlik ekliyoruz
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(position) // Tıklanan pozisyonu geri döndürüyoruz
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ListItem.BoldDescription -> VIEW_TYPE_BOLD_DESCRIPTION
            is ListItem.Description -> VIEW_TYPE_DESCRIPTION
            is ListItem.ImageDescription -> VIEW_TYPE_IMAGE_DESCRIPTION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DESCRIPTION -> {
                val binding = ItemDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DescriptionViewHolder(binding)
            }
            VIEW_TYPE_IMAGE_DESCRIPTION -> {
                val binding = ItemImageDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ImageDescriptionViewHolder(binding)
            }
            VIEW_TYPE_BOLD_DESCRIPTION -> {
                val binding = ItemBoldDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BoldDescriptionViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val currentItem = items[position]) {
            is ListItem.Description -> {
                (holder as DescriptionViewHolder).binding.descriptionText.text = currentItem.description
            }
            is ListItem.ImageDescription -> {
                (holder as ImageDescriptionViewHolder).binding.imageView.setImageResource(currentItem.imageResId)
                holder.binding.descriptionText.text = currentItem.description
            }
            is ListItem.BoldDescription -> {
                (holder as BoldDescriptionViewHolder).binding.descriptionText.text = currentItem.description
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
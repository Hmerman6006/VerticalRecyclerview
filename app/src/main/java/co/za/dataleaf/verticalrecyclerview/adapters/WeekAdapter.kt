package co.za.dataleaf.verticalrecyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import co.za.dataleaf.verticalrecyclerview.SpinnerItem
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.za.dataleaf.verticalrecyclerview.databinding.WeekItemRowBinding

class WeekAdapter(val clickListener: (SpinnerItem) -> Unit):
    ListAdapter<SpinnerItem, WeekAdapter.ItemViewHolder>(DiffCallback) {
    class ItemViewHolder(
        var binding: WeekItemRowBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: SpinnerItem) {
            binding.apply {
                tvWeek.text ="Week ${item.display}"
            }

        }
    }

    private val _items: MutableList<SpinnerItem> = arrayListOf()

    companion object DiffCallback : DiffUtil.ItemCallback<SpinnerItem>() {

        override fun areItemsTheSame(oldItem: SpinnerItem, newItem: SpinnerItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SpinnerItem, newItem: SpinnerItem): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
            WeekItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Bind the item
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener(current)
        }
        holder.bind(current)
    }

    override fun getItemCount(): Int {
        return _items.size
    }

    override fun getItemViewType(position: Int): Int {
        return _items[position].id.toInt()
    }

    override fun getItem(position: Int): SpinnerItem {
        return _items[position]
    }

    fun setData(list: MutableList<SpinnerItem>?) {
        if (itemCount > 0) _items.clear()
        if (list != null) {
            _items.addAll(list)
        }
        notifyDataSetChanged()
    }

}
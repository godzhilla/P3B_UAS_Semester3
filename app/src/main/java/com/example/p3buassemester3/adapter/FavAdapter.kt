package com.example.p3buassemester3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.p3buassemester3.databinding.ItemDestinationBinding
import com.example.p3buassemester3.databinding.ItemListBinding
import com.example.p3buassemester3.dataclass.DestinationModel
import com.example.p3buassemester3.dataclass.FavModel


class FavAdapter(
    private val listDestinationModel: MutableList<FavModel>,
    private val onClickFav: (FavModel) -> Unit
) : RecyclerView.Adapter<FavAdapter.ItemDestinationModelViewHolder>() {

    inner class ItemDestinationModelViewHolder(
        private  val binding: ItemDestinationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavModel) {
            with(binding) {
                txtNameDestination.text = data.destinationName
                txtDescDestination.text = data.destinationDesc
                btnFavorite.setOnClickListener {
                    onClickFav(data)
                }



            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavAdapter.ItemDestinationModelViewHolder {
        val binding = ItemDestinationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemDestinationModelViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavAdapter.ItemDestinationModelViewHolder,
        position: Int
    ) {
        holder.bind(listDestinationModel[position])
    }

    override fun getItemCount(): Int {
        return listDestinationModel.size
    }

    fun updateList(newList : List<FavModel>) {
        listDestinationModel.clear()
        listDestinationModel.addAll(newList)
        notifyDataSetChanged()
    }


}
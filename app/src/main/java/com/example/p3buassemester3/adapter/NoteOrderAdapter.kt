package com.example.p3buassemester3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.p3buassemester3.databinding.ItemDestinationBinding
import com.example.p3buassemester3.databinding.ItemListBinding
import com.example.p3buassemester3.dataclass.DestinationModel
import com.example.p3buassemester3.dataclass.NoteOrder

class NoteOrderAdapter(
    private val listDestinationModel: MutableList<NoteOrder>,
    private val onClickFav: OnClickFav
) : RecyclerView.Adapter<NoteOrderAdapter.ItemDestinationModelViewHolder>() {

    inner class ItemDestinationModelViewHolder(
        private  val binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NoteOrder) {
            with(binding) {
                kotaAsal.text = data.kotaAsal
                kotaAsal.isSelected = true
                kotaTujuan.text = data.kotaTujuan
                kotaAsal.isSelected = true
                txtValueKeberangkatan.text = data.keberangkatan
                txtValueHarga.text = data.totalHarga.toString()
                idPerjalanan.text = data.id
                idPerjalanan.isSelected = true



            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteOrderAdapter.ItemDestinationModelViewHolder {
        val binding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemDestinationModelViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NoteOrderAdapter.ItemDestinationModelViewHolder,
        position: Int
    ) {
        holder.bind(listDestinationModel[position])

    }

    override fun getItemCount(): Int {
        return listDestinationModel.size
    }

    fun updateList(newList : List<NoteOrder>) {
        listDestinationModel.clear()
        listDestinationModel.addAll(newList)
        notifyDataSetChanged()
    }

}
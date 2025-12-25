package com.example.k_pulse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.VH>() {

    private val items = mutableListOf<Event>()

    fun submit(list: List<Event>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvVenue = itemView.findViewById<TextView>(R.id.tvVenue)
        private val tvMeta = itemView.findViewById<TextView>(R.id.tvMeta)

        fun bind(e: Event) {
            tvTitle.text = e.title ?: "(no title)"
            tvVenue.text = e.venue ?: "(no venue)"

            val cityPart = listOfNotNull(e.city, e.region).joinToString(", ")
            val whenPart = e.starts_at ?: ""
            tvMeta.text = listOfNotNull(cityPart.ifBlank { null }, whenPart.ifBlank { null })
                .joinToString(" • ")
        }
    }
}

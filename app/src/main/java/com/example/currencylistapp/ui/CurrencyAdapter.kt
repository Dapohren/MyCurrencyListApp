package com.example.currencylistapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencylistapp.R
import com.example.currencylistapp.data.Valute
import com.example.currencylistapp.presentation.MainViewModel

class CurrencyAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Valute, CurrencyAdapter.CurrencyViewHolder>(CurrencyDiffCallback()) {
    private var itemListenerLong : OnLongItemClickListener? = null
    interface OnLongItemClickListener {
        fun onLongItemClick(position: Int)
    }
    fun setOnLongClickListener(listener: OnLongItemClickListener){
        itemListenerLong = listener
    }

    class CurrencyViewHolder(itemView: View, listenerLong: CurrencyAdapter.OnLongItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        val currencyNameTextView: TextView = itemView.findViewById(R.id.currencyNameTextView)
        val currencyValueTextView: TextView = itemView.findViewById(R.id.currencyValueTextView)
        init {
            itemView.setOnLongClickListener {
                listenerLong?.onLongItemClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    class CurrencyDiffCallback : DiffUtil.ItemCallback<Valute>() {
        override fun areItemsTheSame(oldItem: Valute, newItem: Valute): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Valute, newItem: Valute): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view, itemListenerLong)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = getItem(position)
        holder.currencyNameTextView.text = currency.value
        holder.currencyValueTextView.text = currency.nominal

        holder.itemView.setOnLongClickListener {
            viewModel.updateCurrency(currency.id)
            Log.e("Currency", currency.toString())
            val text = "Валюта ${currency.value} обновлена"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(holder.itemView.context, text, duration)
            toast.show()
            true
        }
    }
}
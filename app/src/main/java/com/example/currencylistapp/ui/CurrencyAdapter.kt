package com.example.currencylistapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencylistapp.R
import com.example.currencylistapp.data.Valute
import com.example.currencylistapp.presentation.MainViewModel

class CurrencyAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Valute, CurrencyAdapter.CurrencyViewHolder>(CurrencyDiffCallback()) {

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyNameTextView: TextView = itemView.findViewById(R.id.currencyNameTextView)
        val currencyValueTextView: TextView = itemView.findViewById(R.id.currencyValueTextView)
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
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = getItem(position)
        holder.currencyNameTextView.text = currency.name
        holder.currencyValueTextView.text = currency.value

        holder.itemView.setOnLongClickListener {
            viewModel.updateCurrency(currency.id)
            true
        }
    }
}
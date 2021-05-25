package com.github.search

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<I : com.github.search.BindableItem> : DiffUtil.ItemCallback<I>() {

    override fun areItemsTheSame(oldItem: I, newItem: I) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: I, newItem: I) = oldItem == newItem
}

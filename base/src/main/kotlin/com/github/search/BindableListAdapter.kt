package com.github.search

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BindableListAdapter<D : com.github.search.BindableItem, VH : RecyclerView.ViewHolder>(
    callback: DiffUtil.ItemCallback<D> = com.github.search.DiffUtilCallback()
) : ListAdapter<D, VH>(callback)

package cn.ryths.blog.app.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.CategoryListItemBinding
import cn.ryths.blog.app.entity.Category

class CategoryListAdapter : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    private var categories: List<Category> = ArrayList()
    private var lisenter: Listener? = null

    fun setLisenter(lisenter: Listener) {
        this.lisenter = lisenter
    }

    fun setCategories(categories: List<Category>) {
        this.categories = categories
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.category = categories[position]
        if (lisenter != null) {
            holder.binding.root.setOnClickListener {
                lisenter!!.onItemClick(it, categories[position], position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: CategoryListItemBinding = DataBindingUtil.inflate(inflater, R.layout.category_list_item, parent, false)

        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.categories.size
    }

    class CategoryViewHolder(val binding: CategoryListItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onItemClick(view: View, category: Category, position: Int)
    }
}
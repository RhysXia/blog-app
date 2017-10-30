package cn.ryths.blog.app.view.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.ArticleEditCategoryBinding
import cn.ryths.blog.app.entity.Category

class CategorySpinnerAdapter : BaseAdapter() {

    private var categories: List<Category> = ArrayList()

    fun setCategories(categories: List<Category>) {
        this.categories = categories
        this.notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var binding: ArticleEditCategoryBinding
        val inflater = LayoutInflater.from(parent.context)
        if (convertView != null) {
            binding = DataBindingUtil.getBinding(convertView)
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.article_edit_category, parent, false)
        }
        binding.category = getItem(position)
        return binding.root
    }

    override fun getItem(position: Int): Category {
        return this.categories[position]
    }

    override fun getItemId(position: Int): Long {
        return this.categories[position].id!!
    }

    override fun getCount(): Int {
        return this.categories.size
    }


    /**
     * 根据category的id确定在adapter中的位置
     */
    fun getPositionByCategoryId(categoryId: Long): Int? {
        for ((index, category) in categories.withIndex()) {
            if (category.id == categoryId) {
                return index
            }
        }
        return null
    }
}
package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.CategoryApi
import cn.ryths.blog.app.databinding.FragmentCategoryBinding
import cn.ryths.blog.app.entity.Category
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.view.activity.CategoryActivity
import cn.ryths.blog.app.view.adapter.CategoryListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CategoryFragment : Fragment() {
    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }

    private lateinit var adapter: CategoryListAdapter
    private val categoryApi = Api.newApiInstance(CategoryApi::class.java)

    private lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        adapter = CategoryListAdapter()
        adapter.setLisenter(object :CategoryListAdapter.Listener {
            override fun onItemClick(view: View, category: Category, position: Int) {
                val intent = Intent(activity,CategoryActivity::class.java)
                intent.putExtra("categoryId",category.id)
                intent.putExtra("categoryName",category.name)
                startActivity(intent)
            }

        })
        binding.fragmentCategoryRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.fragmentCategoryRecyclerView.adapter = adapter
        categoryApi.findAll(0, 100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code == Code.SUCCESS) {
                        adapter.setCategories(it.data!!)
                    }
                }, {})
        return binding.root
    }
}
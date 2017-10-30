package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.databinding.BaseObservable
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import cn.ryths.blog.app.R
import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.CategoryApi
import cn.ryths.blog.app.databinding.FragmentArticleWriteOneBinding
import cn.ryths.blog.app.entity.Category
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.view.activity.ArticleWriteActivity
import cn.ryths.blog.app.view.adapter.CategorySpinnerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleWriteOneFragment : Fragment() {

    private lateinit var binding: FragmentArticleWriteOneBinding

    private val viewModel = ViewModel()
    private var adapter = CategorySpinnerAdapter()
    private val categoryApi = Api.newApiInstance(CategoryApi::class.java)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_write_one, container, false)

        //获取activity中articleDto数据，存入viewModel
        val article = (activity as ArticleWriteActivity).article

        viewModel.title = article.title
        viewModel.content = article.content

        binding.fragmentArticleWriteOneSpinner.adapter = adapter
        categoryApi.findAll(0, 100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code == Code.SUCCESS) {
                        adapter.setCategories(it.data!!)
                        //查看adapter中是否存在和article中categoryId相符的category，如果有则选中
                        if (article.categoryId != null) {
                            val position = adapter.getPositionByCategoryId(article.categoryId!!)
                            if (position != null) {
                                binding.fragmentArticleWriteOneSpinner.setSelection(position)
                            }
                        }
                    }

                }, {})
        binding.fragmentArticleWriteOneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                viewModel.category = parent.adapter.getItem(position) as Category
            }

        }
        binding.viewModel = viewModel

        binding.fragmentArticleWriteOneToolbar.setNavigationOnClickListener {
            activity.finish()
        }
        return binding.root
    }

    inner class ViewModel : BaseObservable() {

        var title: String? = null
            set(value) {
                field = value
                canNext = checkNext()
                this.notifyChange()
            }

        var content: String? = null
            set(value) {
                field = value
                canNext = checkNext()
                this.notifyChange()
            }
        var category: Category? = null
            set(value) {
                field = value
                canNext = checkNext()
                this.notifyChange()
            }
        var canNext: Boolean = false

        fun checkNext() = !title.isNullOrBlank() && !content.isNullOrBlank() && category != null

        fun next() {
            //将数据回写到activity中
            val article = (activity as ArticleWriteActivity).article
            article.categoryId = category!!.id

            article.title = title
            article.content = content

            //跳转到下一个fragment
            val transaction = fragmentManager.beginTransaction()

            transaction.replace(R.id.activity_article_write_frameLayout, ArticleWriteTwoFragment())
            transaction.addToBackStack(null)
            transaction.commit()

        }
    }
}
package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.BR
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.FragmentArticleListBinding
import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Result
import cn.ryths.blog.app.service.ArticleService
import cn.ryths.blog.app.service.ServiceCallback
import me.tatarka.bindingcollectionadapter2.ItemBinding

class IndexFragment : Fragment() {

    private lateinit var articleService: ArticleService

    companion object {
        fun newInstance(articleService: ArticleService): IndexFragment {
            val fragment = IndexFragment()
            fragment.articleService = articleService
            return fragment
        }
    }

    private lateinit var binding: FragmentArticleListBinding


    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_list, parent, false)

        val model = ListViewModel()
        binding.listViewModel = model
        return binding.root
    }


    inner class ListViewModel {
        val items: ObservableArrayList<Article> = ObservableArrayList()
        val itemBinding: ItemBinding<Article> = ItemBinding.of { itemBinding, position, item ->
            itemBinding.set(BR.articleListItem, R.layout.list_item)
        }

        init {
            articleService.findAll(0, 10, object : ServiceCallback<Result<List<Article>>, Void?> {
                override fun success(result: Result<List<Article>>) {
                    items.addAll(result.data!!)
                }

                override fun fail(error: Void?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        }
    }
}
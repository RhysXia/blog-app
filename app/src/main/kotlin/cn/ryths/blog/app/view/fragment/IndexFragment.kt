package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.FragmentArticleListBinding
import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Result
import cn.ryths.blog.app.service.ArticleService
import cn.ryths.blog.app.service.ServiceCallback
import cn.ryths.blog.app.view.adapter.ArticleListAdapter

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
        binding.indexRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val articleListAdapter = ArticleListAdapter()
        articleService.findAll(0, 10, object : ServiceCallback<Result<List<Article>>, Void?> {
            override fun success(result: Result<List<Article>>) {
                articleListAdapter.addAll(result.data!!)
            }

            override fun fail(error: Void?) {
            }

        })

        binding.indexRecyclerView.adapter = articleListAdapter
        return binding.root
    }

}
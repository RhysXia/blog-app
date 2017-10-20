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
import cn.ryths.blog.app.databinding.FragmentArticleListBinding
import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Result
import cn.ryths.blog.app.service.ArticleService
import cn.ryths.blog.app.service.ServiceCallback
import cn.ryths.blog.app.view.activity.ArticleActivity
import cn.ryths.blog.app.view.adapter.ArticleListAdapter
import cn.ryths.blog.app.view.adapter.RollViewAdapter
import com.jude.rollviewpager.RollPagerView

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

    private val pageSize = 10
    private var currentPage = 0

    private lateinit var articleListAdapter: ArticleListAdapter

    private lateinit var rollView: RollPagerView

    private lateinit var rollViewAdapter: RollViewAdapter

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_list, parent, false)
        binding.indexRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        articleListAdapter = ArticleListAdapter()

        rollView = RollPagerView(activity)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, activity.windowManager.defaultDisplay.height / 4)
        rollView.layoutParams = layoutParams
        rollView.setPlayDelay(2000)
        rollViewAdapter = RollViewAdapter(rollView)
        rollViewAdapter.setListener(object : RollViewAdapter.ItemListener {
            override fun onItemClick(view: View, article: Article) {
                gotoInfo(article.id!!)
            }

        })
        rollView.setAdapter(rollViewAdapter)
        freshRollView()
        freshOrUpdateList(false)

        articleListAdapter.addHeader(rollView)

        binding.indexRefreshLayout.setOnRefreshListener {
            //重置当前页
            currentPage = 0
            freshRollView()
            freshOrUpdateList(false)
        }

        binding.indexRefreshLayout.setOnLoadmoreListener {
            currentPage += 1
            freshOrUpdateList(true)
        }

        articleListAdapter.setListener(object : ArticleListAdapter.ItemListener {
            override fun onItemClick(view: View, article: Article, position: Int) {
                gotoInfo(article.id!!)
            }

        })

        binding.indexRecyclerView.adapter = articleListAdapter

        return binding.root
    }

    private fun gotoInfo(id: Long) {
        val intent = Intent(activity, ArticleActivity::class.java)
        intent.putExtra("articleId", id)
        startActivity(intent)
    }

    private fun freshOrUpdateList(isAddMore: Boolean) {
        articleService.findAll(0, 10, object : ServiceCallback<Result<List<Article>>, Void?> {
            override fun success(result: Result<List<Article>>) {
                if (isAddMore) articleListAdapter.addAll(result.data!!) else articleListAdapter.setAll(result.data!!)
                binding.indexRefreshLayout.finishLoadmore()
                binding.indexRefreshLayout.finishRefresh()
                //判断当前页是否是最后一页
                binding.indexRefreshLayout.isEnableLoadmore = (currentPage + 1 <= result.pagination!!.totalPage!!)
            }

            override fun fail(error: Void?) {
            }

        })
    }

    private fun freshRollView() {
        articleService.findAllRecommendation(0, 5, object : ServiceCallback<Result<List<Article>>, Void?> {
            override fun success(result: Result<List<Article>>) {
                rollViewAdapter.setAll(result.data!!)
            }

            override fun fail(error: Void?) {
            }

        })
    }

}
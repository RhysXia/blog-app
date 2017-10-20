package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.FragmentArticleBinding
import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.service.ArticleService
import cn.ryths.blog.app.service.ServiceCallback

class ArticleFragment : Fragment() {

    private lateinit var articleService: ArticleService
    private var articleId: Long = 0

    companion object {
        fun newInstance(articleService: ArticleService, articleId: Long): ArticleFragment {
            val fragment = ArticleFragment()
            fragment.articleService = articleService
            fragment.articleId = articleId
            return fragment
        }
    }


    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, parent, false)
        binding.fragmentArticleToolbar.setNavigationOnClickListener {
            activity.finish()
        }
        binding.fragmentArticleContent.settings.javaScriptEnabled = true

        articleService.findById(articleId, object : ServiceCallback<Article?, Void?> {
            override fun success(result: Article?) {
                binding.article = result
                binding.fragmentArticleContent.addJavascriptInterface(JsClient(result!!.content), "JsClient")
                binding.fragmentArticleContent.loadUrl("file:///android_asset/markdown.html")
            }

            override fun fail(error: Void?) {

            }

        })

        return binding.root
    }

    /**
     * js调用的api
     */
    class JsClient(private val content: String?) {
        /**
         * 获取文章内容
         */
        @JavascriptInterface
        fun getContent() = content
    }
}
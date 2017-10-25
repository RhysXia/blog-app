package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableLong
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.widget.Toast
import cn.ryths.blog.app.R
import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.ArticleApi
import cn.ryths.blog.app.databinding.FragmentArticleBinding
import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.entity.User
import cn.ryths.blog.app.view.viewModel.GlobalViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleFragment : Fragment() {

    private val articleApi = Api.newApiInstance(ArticleApi::class.java)
    private var articleId: Long = 0


    private lateinit var article: Article
    private lateinit var binding: FragmentArticleBinding

    companion object {
        fun newInstance(articleId: Long): ArticleFragment {
            val fragment = ArticleFragment()
            fragment.articleId = articleId
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, parent, false)
        binding.fragmentArticleToolbar.setNavigationOnClickListener {
            activity.finish()
        }
        binding.fragmentArticleContent.settings.javaScriptEnabled = true
        binding.globalViewModel = GlobalViewModel.getInstance()
        val viewModel = ViewModel()
        binding.viewModel = viewModel
        articleApi.findById(articleId, true, true, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code == Code.SUCCESS) {
                        article = it.data!!
                        viewModel.title.set(it.data!!.title)
                        viewModel.poster.set(it.data!!.poster)
                        viewModel.author.set(it.data!!.author)
                        viewModel.praiseNum.set(it.data!!.praiseNum!!)
                        binding.fragmentArticleContent.addJavascriptInterface(JsClient(it.data!!.content), "JsClient")
                        binding.fragmentArticleContent.loadUrl("file:///android_asset/markdown.html")
                    }
                }, {})
        articleApi.checkPraise(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code == Code.SUCCESS) {
                        viewModel.praised.set(it.data!!)
                    } else {
                        viewModel.praised.set(false)
                    }
                }, {
                    viewModel.praised.set(false)
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

    inner class ViewModel {
        val title = ObservableField<String>("")
        val poster = ObservableField<String>()
        val praiseNum = ObservableLong(0)
        val author = ObservableField<User>()

        val praised = ObservableBoolean(false)

        fun praiseClick() {
            val isLogin = GlobalViewModel.getInstance().login
            if (!isLogin) {
                Toast.makeText(activity, "请登录后再进行点赞", Toast.LENGTH_SHORT).show()
                return
            }
            //取消点赞
            if (praised.get()) {
                articleApi.deletePraise(articleId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            praiseNum.set(praiseNum.get() - 1)
                            //设置点赞标志
                            praised.set(false)
                            Toast.makeText(activity, "取消点赞成功", Toast.LENGTH_SHORT).show()
                        }, {})
            } else {
                //请求服务器，点赞
                articleApi.praise(articleId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            //修改点赞数
                            praiseNum.set(praiseNum.get() + 1)
                            //设置点赞标志
                            praised.set(true)
                            Toast.makeText(activity, "点赞成功", Toast.LENGTH_SHORT).show()
                        }, {})
            }
        }

        fun commentClick() {
            val commentFragment = CommentFragment.newInstance(article)
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.activity_article_frameLayout, commentFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }
}
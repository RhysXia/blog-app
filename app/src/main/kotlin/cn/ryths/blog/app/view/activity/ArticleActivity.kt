package cn.ryths.blog.app.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.ActivityArticleBinding
import cn.ryths.blog.app.service.ArticleService
import cn.ryths.blog.app.view.fragment.ArticleFragment

class ArticleActivity : AppCompatActivity() {

    private var articleId: Long = 0


    private lateinit var articleFragment: ArticleFragment


    private lateinit var binding: ActivityArticleBinding

    private lateinit var articleService: ArticleService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article)

        articleId = intent.getLongExtra("articleId",0)

        articleService = ArticleService()

        val articleFragment = ArticleFragment.newInstance(articleService, articleId)

        val transaction = fragmentManager.beginTransaction()

        transaction.add(R.id.fragment_article, articleFragment)
        transaction.show(articleFragment)
        transaction.commit()

    }
}

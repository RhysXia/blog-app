package cn.ryths.blog.app.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.ActivityArticleBinding
import cn.ryths.blog.app.view.fragment.ArticleFragment

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article)
        val articleId: Long = intent.getLongExtra("articleId", 0)
        val transaction = fragmentManager.beginTransaction()
        val fragment = ArticleFragment.newInstance(articleId)
        transaction.add(R.id.activity_article_frameLayout, fragment)
        transaction.show(fragment)
        transaction.commit()
    }

}

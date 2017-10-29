package cn.ryths.blog.app.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.ActivityMyArticlesBinding
import cn.ryths.blog.app.view.fragment.ArticleListFragment

class MyArticlesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyArticlesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_articles)

        val transaction = fragmentManager.beginTransaction()
        val articleListFragment = ArticleListFragment.newInstance(ArticleListFragment.CODE_SELF)
        transaction.add(R.id.activity_my_articles_frameLayout, articleListFragment)
        transaction.show(articleListFragment)
        transaction.commit()
        binding.activityMyArticlesToolbar.setNavigationOnClickListener {
            this@MyArticlesActivity.finish()
        }
    }
}

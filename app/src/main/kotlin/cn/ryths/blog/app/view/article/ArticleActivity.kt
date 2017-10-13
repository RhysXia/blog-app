package cn.ryths.blog.app.view.article

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.ryths.blog.app.R
import cn.ryths.blog.app.view.article.article.ArticleFragment

class ArticleActivity : AppCompatActivity() {

    private var articleId: Long = 0


    private lateinit var articleFragment: ArticleFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        articleId = intent.getSerializableExtra("articleId") as Long

        //实例化fragment
        articleFragment = ArticleFragment()

        //设置参数
        val bundle = Bundle()
        bundle.putLong("articleId", articleId)
        articleFragment.arguments = bundle


        //显示文章
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.fragment_article, articleFragment, "fragment0")
        transaction.show(articleFragment)
        transaction.commit()
    }
}

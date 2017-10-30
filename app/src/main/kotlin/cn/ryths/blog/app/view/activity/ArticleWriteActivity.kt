package cn.ryths.blog.app.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.ActivityArticleWriteBinding
import cn.ryths.blog.app.view.fragment.ArticleWriteOneFragment

class ArticleWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleWriteBinding


    val article = ArticleDto()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_write)

        val transaction = fragmentManager.beginTransaction()
        val fragmentOne = ArticleWriteOneFragment()
        transaction.add(R.id.activity_article_write_frameLayout, fragmentOne)
        transaction.show(fragmentOne)
        transaction.commit()

    }


}


data class ArticleDto(
        var id: Long? = null,
        var title: String? = null,
        var summary: String? = null,
        var content: String? = null,
        var categoryId: Long? = null,
        var poster: String? = null
)
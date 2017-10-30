package cn.ryths.blog.app.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.ryths.blog.app.R
import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.ArticleApi
import cn.ryths.blog.app.databinding.ActivityArticleWriteBinding
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.view.fragment.ArticleWriteOneFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleWriteBinding


    val articleDto = ArticleDto()

    private val articleApi = Api.newApiInstance(ArticleApi::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_write)

        //判断是添加还是修改
        if (intent.hasExtra("articleId")) {
            val articleId = intent.getLongExtra("articleId", 0)
            articleApi.findById(articleId, true, false, true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.code == Code.SUCCESS) {

                            articleDto.id = it.data!!.id
                            articleDto.title = it.data!!.title
                            articleDto.summary = it.data!!.summary
                            articleDto.content = it.data!!.content
                            articleDto.poster = it.data!!.poster
                            articleDto.categoryId = it.data!!.category!!.id

                            val transaction = fragmentManager.beginTransaction()
                            val fragmentOne = ArticleWriteOneFragment()
                            transaction.add(R.id.activity_article_write_frameLayout, fragmentOne)
                            transaction.show(fragmentOne)
                            transaction.commit()
                        }
                    }, {})
        } else {

            val transaction = fragmentManager.beginTransaction()
            val fragmentOne = ArticleWriteOneFragment()
            transaction.add(R.id.activity_article_write_frameLayout, fragmentOne)
            transaction.show(fragmentOne)
            transaction.commit()
        }


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
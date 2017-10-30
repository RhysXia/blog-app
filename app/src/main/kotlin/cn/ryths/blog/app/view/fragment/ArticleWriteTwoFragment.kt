package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.content.Intent
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import cn.ryths.blog.app.BR
import cn.ryths.blog.app.R
import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.ArticleApi
import cn.ryths.blog.app.databinding.FragmentArticleWriteTwoBinding
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.utils.FileUtils
import cn.ryths.blog.app.view.activity.ArticleWriteActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import java.io.File

class ArticleWriteTwoFragment : Fragment() {

    private lateinit var binding: FragmentArticleWriteTwoBinding
    private val FILE_SELECT_CODE = 0

    private val viewModel = ViewModel()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_write_two, container, false)

        //获取activity中articleDto数据，存入viewModel
        val article = (activity as ArticleWriteActivity).articleDto
        //判断是修改还是添加
        viewModel.add = article.id == null
        viewModel.summary = article.summary
        viewModel.poster = article.poster


        binding.viewModel = viewModel

        binding.fragmentArticleWriteTwoToolbar.setNavigationOnClickListener {
            activity.finish()
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FILE_SELECT_CODE -> {
                if (data == null) {
                    return
                }
                val uri = data.data
                viewModel.poster = FileUtils.getPath(activity, uri)
            }
        }
    }

    inner class ViewModel : BaseObservable() {
        @Bindable
        var add: Boolean = true
            set(value) {
                field = value
                this.notifyPropertyChanged(BR.add)
            }
        /**
         * 显示从本地加载图像还是从网上加载
         */
        @Bindable
        var showLocalPoster = false
            set(value) {
                field = value
                this.notifyPropertyChanged(BR.showLocalPoster)
            }
        @Bindable
        var poster: String? = null
            set(value) {
                field = value
                this.notifyPropertyChanged(cn.ryths.blog.app.BR.poster)
                canNext = checkNext()
                //判断是否是加载本地文件
                if (value != null) {
                    showLocalPoster = !value.startsWith("http", true)
                }
            }

        @Bindable
        var summary: String? = null
            set(value) {
                field = value
                this.notifyPropertyChanged(cn.ryths.blog.app.BR.summary)
                canNext = checkNext()
            }

        @Bindable
        var canNext: Boolean = false
            set(value) {
                field = value
                this.notifyPropertyChanged(cn.ryths.blog.app.BR.canNext)
            }

        fun checkNext() = !poster.isNullOrBlank() && !summary.isNullOrBlank()

        fun choosePoster() {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, FILE_SELECT_CODE)
        }

        fun next() {
            //点击之后，就不允许再点击了，直到完成文件上传
            canNext = false

            val article = (activity as ArticleWriteActivity).articleDto


            article.poster = poster
            article.summary = summary

            val articleApi = Api.newApiInstance(ArticleApi::class.java)


            val api = if (add) {
                val posterFile = File(article.poster)
                val contentType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(poster!!.substring(poster!!.lastIndexOf(".") + 1))
                val posterBody = MultipartBody.create(MediaType.parse(contentType), posterFile)
                val part = MultipartBody.Part.createFormData("posterFile", posterFile.name, posterBody)
                articleApi.add(MultipartBody.Part.createFormData("title", article.title!!),
                        MultipartBody.Part.createFormData("summary", article.summary!!),
                        MultipartBody.Part.createFormData("content", article.content!!),
                        MultipartBody.Part.createFormData("categoryId", article.categoryId.toString()),
                        part)
            } else {
                var part: MultipartBody.Part? = null
                if (showLocalPoster) {
                    val posterFile = File(article.poster)
                    val contentType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(poster!!.substring(poster!!.lastIndexOf(".") + 1))
                    val posterBody = MultipartBody.create(MediaType.parse(contentType), posterFile)
                    part = MultipartBody.Part.createFormData("posterFile", posterFile.name, posterBody)
                }
                articleApi.update(MultipartBody.Part.createFormData("id", article.id.toString()),
                        MultipartBody.Part.createFormData("title", article.title!!),
                        MultipartBody.Part.createFormData("summary", article.summary!!),
                        MultipartBody.Part.createFormData("content", article.content!!),
                        MultipartBody.Part.createFormData("categoryId", article.categoryId.toString()),
                        part)
            }

            api.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.code == Code.SUCCESS) {
                            Toast.makeText(activity, "完成文章", Toast.LENGTH_SHORT).show()
                            this@ArticleWriteTwoFragment.activity.finish()
                        }
                    }, {
                        //上传失败，按钮重新激活
                        canNext = true
                    })

        }

        fun back() {
            //保存当前输入状态
            val article = (activity as ArticleWriteActivity).articleDto
            article.summary = viewModel.summary
            article.poster = viewModel.poster
            fragmentManager.popBackStack()
        }
    }
}
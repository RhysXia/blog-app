package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.ryths.blog.app.R
import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.CommentApi
import cn.ryths.blog.app.api.CommentDto
import cn.ryths.blog.app.databinding.FragmentCommentBinding
import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.view.adapter.CommentListAdapter
import cn.ryths.blog.app.view.viewModel.GlobalViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentFragment : Fragment() {
    private lateinit var article: Article

    private lateinit var binding: FragmentCommentBinding

    private val commentApi = Api.newApiInstance(CommentApi::class.java)
    private val commentListAdapter = CommentListAdapter()

    private val pageSize = 10
    private var currentPage = 0

    companion object {
        fun newInstance(article: Article): CommentFragment {
            val fragment = CommentFragment()
            fragment.article = article
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false)
        binding.fragmentCommentToolbar.setNavigationOnClickListener {
            fragmentManager.popBackStack()
        }
        binding.fragmentCommentRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        getComments(true)
        binding.fragmentCommentRecyclerView.adapter = commentListAdapter
        binding.fragmentCommentRefreshLayout.setOnRefreshListener {
            getComments(false)
        }
        binding.fragmentCommentRefreshLayout.setOnLoadmoreListener {
            getComments(true)
        }
        binding.gloableViewModel = GlobalViewModel.getInstance()

        val viewModel = ViewModel()
        viewModel.articleTitle.set(article.title)
        binding.viewModel = viewModel
        return binding.root
    }

    private fun getComments(isLoadMore: Boolean) {
        if(!isLoadMore){
            currentPage = 0
        }
        commentApi.findAllComments(article.id!!, currentPage, pageSize, false, true, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code == Code.SUCCESS) {
                        if (isLoadMore) {
                            commentListAdapter.addAll(it.data!!)
                        } else {
                            commentListAdapter.setAll(it.data!!)
                        }
                        currentPage += 1
                        binding.fragmentCommentRefreshLayout.isEnableLoadmore = (currentPage < it.pagination!!.totalPage!!)
                        binding.fragmentCommentRefreshLayout.finishLoadmore()
                        binding.fragmentCommentRefreshLayout.finishRefresh()
                    }
                }, {})
    }

    inner class ViewModel {
        val articleTitle: ObservableField<String> = ObservableField()
        val comment: ObservableField<String> = ObservableField()

        fun submit(){
            if(comment.get().isNullOrBlank()){
                Toast.makeText(activity,"请输入评论",Toast.LENGTH_SHORT).show()
                return
            }
            commentApi.addComment(CommentDto(content = this.comment.get(),articleId = article.id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(it.code == Code.SUCCESS){
                            Toast.makeText(activity,"评论成功",Toast.LENGTH_SHORT).show()
                            //清空评论
                            comment.set("")
                            //更新列表
                            getComments(false)
                        }
                    })
        }
    }
}
package cn.ryths.blog.app.view.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.BR
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.ListItemBinding
import cn.ryths.blog.app.entity.Article

class ArticleListAdapter : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {
    private var listener: ItemListener? = null


    /**
     * 存放文章数据
     */
    private var articles: List<Article> = ArrayList()

    /**
     * 头部
     */
    private var headers: List<ViewDataBinding> = ArrayList()

    /**
     * 尾部
     */
    private var footers: List<ViewDataBinding> = ArrayList()

    /**
     * 添加监听器
     */
    fun setListener(listener: ItemListener) {
        this.listener = listener
    }

    /**
     * 添加头
     */
    fun addHeader(view: ViewDataBinding) {
        headers += view
        //更新视图
        this.notifyItemChanged(this.headers.size - 1)
    }

    /**
     * 添加尾部
     */
    fun addFooter(view: ViewDataBinding) {
        footers += view
        //更新视图
        this.notifyItemChanged(this.headers.size + this.articles.size + this.footers.size - 1)
    }

    /**
     * 添加文章列表
     */
    fun addAll(articles: List<Article>) {
        this.articles += articles
        //更新视图
        this.notifyItemChanged(this.headers.size + this.articles.size - articles.size - 1, articles.size)
    }

    /**
     * 重新设置文章列表
     */
    fun setAll(articles: List<Article>) {
        val pos = this.headers.size + this.articles.size
        this.articles = articles
        //更新视图
        this.notifyDataSetChanged()
    }


    /**
     * 清空文章列表
     */
    fun removeAll() {
        val size = this.articles.size
        this.articles = ArrayList()
        //更新视图
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        //如果是头部或者尾部，不需要绑定数据
        if (position < this.headers.size || position >= this.headers.size + this.articles.size) {
            return
        }
        holder.bindArticle(articles[position - this.headers.size])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        //如果是头部
        if (viewType < this.headers.size) {
            return ArticleViewHolder(headers[viewType])
        }

        val pos = viewType - this.headers.size - this.articles.size
        //如果是尾部
        if (pos >= 0) {
            return ArticleViewHolder(footers[pos])
        }

        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListItemBinding>(inflater, R.layout.list_item, parent, false)

        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.articles.size + headers.size + footers.size
    }

    override fun getItemViewType(position: Int): Int {
        //使用position作为类型
        return position
    }


    class ArticleViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindArticle(article: Article) {
            binding.setVariable(BR.article, article)
        }


    }

    /**
     * 监听器
     */
    interface ItemListener {
        /**
         * 每个item的点击事件，
         * [view] 被点击的view
         * [article] 对应的文章
         * [position] 该view在列表中的位置
         */
        fun onItemClick(view: View, article: Article, position: Int)
    }

}
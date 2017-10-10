package cn.ryths.blog.app.view.index

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.ryths.blog.app.R
import cn.ryths.blog.app.entity.Article
import com.squareup.picasso.Picasso

class ArticleListAdapter : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {
    private var listener: ItemListener? = null


    /**
     * 存放文章数据
     */
    private var articles: List<Article> = ArrayList()

    /**
     * 头部
     */
    private var headers: List<View> = ArrayList()

    /**
     * 尾部
     */
    private var footers: List<View> = ArrayList()

    /**
     * 添加监听器
     */
    fun setListener(listener: ItemListener) {
        this.listener = listener
    }

    /**
     * 添加头
     */
    fun addHeader(view: View) {
        headers += view
        //更新视图
        this.notifyItemChanged(this.headers.size - 1)
    }

    /**
     * 添加尾部
     */
    fun addFooter(view: View) {
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
        val view = inflater.inflate(R.layout.list_item, parent, false)
        //如果监听器存在，则监听当前view的点击事件
        if (listener != null) {
            view.setOnClickListener {
                listener!!.onItemClick(it, articles[viewType - this.headers.size + 1])
            }
        }
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.articles.size + headers.size + footers.size
    }

    override fun getItemViewType(position: Int): Int {
        //使用position作为类型
        return position
    }


    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindArticle(article: Article) {
            //获取view
            val poster = itemView.findViewById<ImageView>(R.id.list_item_poster)
            val title = itemView.findViewById<TextView>(R.id.list_item_title)
            val summary = itemView.findViewById<TextView>(R.id.list_item_summary)
            //设置数据
            Picasso.with(itemView.context)
                    .load(article.poster)
                    .into(poster)
            title.text = article.title
            summary.text = article.summary

        }

    }

    /**
     * 监听器
     */
    interface ItemListener {
        fun onItemClick(view: View, article: Article)
    }

}
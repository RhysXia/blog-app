package cn.ryths.blog.app.view.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.ArticleRollItemBinding
import cn.ryths.blog.app.entity.Article
import com.jude.rollviewpager.RollPagerView
import com.jude.rollviewpager.adapter.LoopPagerAdapter

class RollViewAdapter(rollView: RollPagerView) : LoopPagerAdapter(rollView) {
    private lateinit var binding: ArticleRollItemBinding

    override fun getView(container: ViewGroup, position: Int): View {
        val inflater = LayoutInflater.from(container.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.article_roll_item, container, false)
        binding.article = articles[position]
        binding.root.setOnClickListener {
            if (listener != null) {
                listener!!.onItemClick(binding.root, articles[position])
            }
        }
        return binding.root
    }

    override fun getRealCount(): Int {
        return articles.size
    }

    private var articles: List<Article> = ArrayList()
    private var listener: ItemListener? = null

    /**
     * 添加监听器
     */
    fun setListener(listener: ItemListener) {
        this.listener = listener
    }

    /**
     * 添加文章
     */
    fun addAll(articles: List<Article>) {
        this.articles += articles
        this.notifyDataSetChanged()
    }

    /**
     * 重置所有文章
     */
    fun setAll(articles: List<Article>) {
        this.articles = articles
        this.notifyDataSetChanged()
    }


    /**
     * 监听器
     */
    interface ItemListener {
        fun onItemClick(view: View, article: Article)
    }
}
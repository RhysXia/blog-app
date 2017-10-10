package cn.ryths.blog.app.view.index

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.ryths.blog.app.R
import cn.ryths.blog.app.entity.Article
import com.jude.rollviewpager.RollPagerView
import com.jude.rollviewpager.adapter.LoopPagerAdapter
import com.squareup.picasso.Picasso

class RollViewAdapter(rollView: RollPagerView) : LoopPagerAdapter(rollView) {
    override fun getView(container: ViewGroup, position: Int): View {
        val inflater = LayoutInflater.from(container.context)
        //获取view
        val view = inflater.inflate(R.layout.roll_item, null)
        //获取view中组件
        val poster = view.findViewById<ImageView>(R.id.roll_poster)
        val title = view.findViewById<TextView>(R.id.roll_title)
        //设置值
        Picasso.with(container.context)
                .load(articles[position].poster)
                .into(poster)
        title.text = articles[position].title
        //如果监听器存在，则监听当前view的点击事件
        if (listener != null) {
            view.setOnClickListener {
                listener!!.onItemClick(it, articles[position])
            }
        }

        return view
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
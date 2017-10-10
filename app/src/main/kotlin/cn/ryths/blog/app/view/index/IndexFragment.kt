package cn.ryths.blog.app.view.index

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.ryths.blog.app.R
import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Result
import com.jude.rollviewpager.RollPagerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class IndexFragment : Fragment() {
    /**
     * viewModel
     */
    private lateinit var indexPresenter: IndexPresenter

    /**
     * 列表
     */
    private lateinit var recyclerView: RecyclerView
    /**
     * 刷新
     */
    private lateinit var refreshLayout: SmartRefreshLayout


    /**
     * 列表适配器
     */
    private var articleListAdapter = ArticleListAdapter()

    /**
     * 轮播图
     */
    private lateinit var rollView: RollPagerView

    private val rollViewAdapter = RollViewAdapter()

    /**
     * 回调
     */
    private val callback = Callback()

    /**
     * 每页显示数
     */
    private val pageSize = 10
    /**
     * 当前页
     */
    private var currentPage = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_article_list, container, false)
        //初始化presenter
        indexPresenter = IndexPresenter()

        //获取滚动列表
        recyclerView = view.findViewById(R.id.index_recyclerView)

        //添加布局管理器
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        //设置列表元素点击的监听

        articleListAdapter.setListener(object : ArticleListAdapter.ItemListener {
            override fun onItemClick(view: View, article: Article) {
                onItemClickEvent(view, article)
            }

        })

        //设置适配器
        recyclerView.adapter = articleListAdapter


        //获取屏幕高度
        val height = activity.windowManager.defaultDisplay.height
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 4)

        //初始化轮播图
        rollView = RollPagerView(view.context)
        rollView.setPlayDelay(2000)
        rollView.layoutParams = layoutParams

        //设置列表元素点击的监听

        rollViewAdapter.setListener(object : RollViewAdapter.ItemListener {
            override fun onItemClick(view: View, article: Article) {
                onItemClickEvent(view, article)
            }

        })

        //设置轮播图适配器
        rollView.setAdapter(rollViewAdapter)


        //轮播图添加到列表中
        articleListAdapter.addHeader(rollView)

        //设置轮播图适配器的数据
        setDataInRollView()

        //获取列表数据
        addMoreInRecyclerView()

        //获取refreshLayout
        refreshLayout = view.findViewById(R.id.index_refreshLayout)
        //加载更多事件
        refreshLayout.setOnLoadmoreListener {
            addMoreInRecyclerView()
        }

        //刷新
        refreshLayout.setOnRefreshListener {
            currentPage = 0
            addMoreInRecyclerView()
            setDataInRollView()
        }

        return view
    }

    /**
     * 列表点击的事件监听
     */
    private fun onItemClickEvent(view: View, article: Article) {
        Toast.makeText(activity, article.toString(), 2000).show()
    }


    private fun addMoreInRecyclerView() {
        indexPresenter.addMoreInRecyclerView(currentPage, pageSize, callback)
    }

    private fun setDataInRollView() {
        indexPresenter.getInRollView(0, 5, callback)
    }

    /**
     * 回调
     */
    inner class Callback {
        /**
         * 获取列表数据成功后的回调
         */
        fun addMoreInRecyclerViewSuccess(result: Result<List<Article>>) {

            refreshLayout.finishLoadmore()
            refreshLayout.finishRefresh()

            //如果currentPage==0 说明此时是第一次加载或者刷新
            if (currentPage == 0) {
                articleListAdapter.setAll(result.data!!)
            } else {
                articleListAdapter.addAll(result.data!!)
            }


            currentPage++
            //判断是否还可以刷新
            refreshLayout.isEnableLoadmore = currentPage < result.pagination!!.totalPage!!


        }

        /**
         * 获取列表数据失败后的回调
         */
        fun addMoreInRecyclerViewFail() {
            Toast.makeText(this@IndexFragment.activity, "网络有问题，请稍后再试！", 3000).show()
        }

        /**
         * 获取轮播图数据成功时的回调
         */
        fun addInRollViewSuccess(result: Result<List<Article>>) {
            //判断是否还有数据需要加载
            rollViewAdapter.setAll(result.data!!)
        }

        /**
         * 获取轮播图数据失败的回调
         */
        fun addInRollViewFail() {
            Toast.makeText(this@IndexFragment.activity, "网络有问题，请稍后再试！", 3000).show()
        }


    }
}
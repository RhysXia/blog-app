package cn.ryths.blog.app.view.tab.index

import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.ArticleApi
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.view.tab.index.IndexFragment.Callback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class IndexPresenter {


    /**
     * api访问接口
     */
    private val articleApi = Api.newApiInstance(ArticleApi::class.java)


    /**
     * 添加更多数据到listView适配器
     */
    fun addMoreInRecyclerView(currentPage: Int, pageSize: Int, callback: Callback) {
        articleApi.getList(currentPage, pageSize,true,true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    //判断请求是否成功
                    if (result.code == Code.SUCCESS) {
                        //成功回调
                        callback.addMoreInRecyclerViewSuccess(result)
                    } else {
                        //失败回调
                        callback.addMoreInRecyclerViewFail()
                    }
                }
    }

    /**
     * 添加数据到rollView的适配器
     */
    fun getInRollView(currentPage: Int, pageSize: Int, callback: Callback) {
        articleApi.getRecommendation(currentPage, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    //判断请求是否成功
                    if (result.code == Code.SUCCESS) {
                        //成功回调
                        callback.addInRollViewSuccess(result)
                    } else {
                        //失败回调
                        callback.addInRollViewFail()
                    }
                }
    }

}
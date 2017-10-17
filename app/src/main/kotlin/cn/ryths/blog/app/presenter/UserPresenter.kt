package cn.ryths.blog.app.presenter

import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.UserApi
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.entity.Result
import cn.ryths.blog.app.entity.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * 用户数据提供类
 */
class UserPresenter {

    private val userApi = Api.newApiInstance(UserApi::class.java)

    fun login(username: String, password: String, callback: PresenterCallback<String, String>) {
        userApi.login(User(username = username, password = password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<Result<String>> {
                    if (it.code == Code.SUCCESS) {
                        callback.success(it.data!!)
                    } else {
                        callback.fail(it.message!!)
                    }
                }, Consumer<Throwable> {
                    callback.fail("用户名密码有误")
                })
    }

    fun getSelf(callback: PresenterCallback<User, String>){
        userApi.getSelf()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<Result<User>> {
                    if (it.code == Code.SUCCESS) {
                        callback.success(it.data!!)
                    } else {
                        callback.fail(it.message!!)
                    }
                }, Consumer<Throwable> {
                    callback.fail("用户名密码有误")
                })
    }
}
package cn.ryths.blog.app.service

import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.UserApi
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.entity.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 用户数据提供类
 */
class UserService {

    private val userApi = Api.newApiInstance(UserApi::class.java)

    fun login(username: String, password: String, callback: ServiceCallback<String, String?>) {
        userApi.login(User(username = username, password = password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code == Code.SUCCESS) {
                        callback.success(it.data!!)
                    } else {
                        callback.fail(it.message)
                    }
                }, {
                    callback.fail(it.message)
                })
    }

    fun getSelf(callback: ServiceCallback<User, String?>) {
        userApi.getSelf()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code == Code.SUCCESS) {
                        callback.success(it.data!!)
                    } else {
                        callback.fail(it.message)
                    }
                }, {
                    callback.fail(it.message)
                })
    }

    fun checkToken(callback: ServiceCallback<Boolean, String?>) {
        userApi.checkToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code == Code.SUCCESS) {
                        callback.success(it.data!!)
                    } else {
                        callback.fail(it.message)
                    }
                }, {
                    callback.fail(it.message)
                })
    }
}
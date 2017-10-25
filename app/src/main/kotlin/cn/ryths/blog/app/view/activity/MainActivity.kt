package cn.ryths.blog.app.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.ryths.blog.app.R
import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.UserApi
import cn.ryths.blog.app.databinding.ActivityMainBinding
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.view.fragment.TabFragment
import cn.ryths.blog.app.view.viewModel.GlobalViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val userApi = Api.newApiInstance(UserApi::class.java)


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //判断token是否存在，如果存在，验证是否正确，如果不正确，则将其移除
        userApi.checkToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code == Code.SUCCESS) {
                        //token合法
                        if (it.data!!) {
                            //获取用户信息
                            GlobalViewModel.getInstance().login = true
                            userApi.getSelf()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        if (it.code == Code.SUCCESS) {
                                            GlobalViewModel.getInstance().user = it.data
                                        }
                                    }, {})
                        }else{
                            GlobalViewModel.getInstance().login = false
                        }
                    }
                }, {})

        val transition = fragmentManager.beginTransaction()
        val tabFragment = TabFragment.newInstance()

        transition.add(R.id.activity_main_frameLayout, tabFragment)
        transition.show(tabFragment)
        transition.commit()

    }


}

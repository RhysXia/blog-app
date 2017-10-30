package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.UserApi
import cn.ryths.blog.app.databinding.FragmentSettingBinding
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.utils.TokenUtils
import cn.ryths.blog.app.view.activity.ArticleWriteActivity
import cn.ryths.blog.app.view.activity.MyArticlesActivity
import cn.ryths.blog.app.view.viewModel.GlobalViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private var loginDialogFragment: LoginDialogFragment? = null

    private var userApi = Api.newApiInstance(UserApi::class.java)

    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, parent, false)
        binding.viewModel = ViewModel()
        binding.globalViewModel = GlobalViewModel.getInstance()
        return binding.root
    }

    inner class ViewModel {
        fun loginClick() {
            val globalViewModel = GlobalViewModel.getInstance()
            if (loginDialogFragment == null) {
                loginDialogFragment = LoginDialogFragment.newInstance(object : LoginDialogFragment.Listener {
                    override fun onLoginSuccess() {
                        globalViewModel.login = true
                        userApi.getSelf()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    if (it.code == Code.SUCCESS) {
                                        globalViewModel.user = it.data
                                    }
                                }, {})
                    }

                })
            }
            loginDialogFragment!!.show(fragmentManager, "loginDialogFragment")
        }

        fun logoutClick() {
            //删除token
            TokenUtils.removeToken(activity)
            //login置false
            GlobalViewModel.getInstance().login = false
            GlobalViewModel.getInstance().user = null
        }

        fun selfArticlesClick() {
            val intent = Intent(activity, MyArticlesActivity::class.java)
            startActivity(intent)
        }

        fun writeArticlesClick() {
            val intent = Intent(activity, ArticleWriteActivity::class.java)
            startActivity(intent)
        }
    }

}
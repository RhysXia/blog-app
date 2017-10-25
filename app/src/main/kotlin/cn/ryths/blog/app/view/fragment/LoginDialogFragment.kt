package cn.ryths.blog.app.view.fragment

import android.app.DialogFragment
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.UserApi
import cn.ryths.blog.app.databinding.FragmentDialogLoginBinding
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.entity.User
import cn.ryths.blog.app.utils.TokenUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 登录注册框
 */
class LoginDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogLoginBinding
    private var userApi = Api.newApiInstance(UserApi::class.java)
    private lateinit var listener: Listener


    companion object {
        fun newInstance(listener: Listener): LoginDialogFragment {
            val fragment = LoginDialogFragment()
            fragment.listener = listener
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_login, parent, false)


        binding.viewModel = ViewModel()
        return binding.root
    }


    interface Listener {
        fun onLoginSuccess()
    }

    inner class ViewModel {
        val username: ObservableField<String> = ObservableField("")
        val password: ObservableField<String> = ObservableField("")
        val error: ObservableField<String> = ObservableField("")

        fun loginClick() {
            val usernameStr = username.get()
            val passwordStr = password.get()
            if (usernameStr.isNullOrBlank() || passwordStr.isNullOrBlank()) {
                error.set("用户名密码不能为空")
            }
            userApi.login(User(username = usernameStr, password = passwordStr))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.code == Code.SUCCESS) {
                            TokenUtils.saveToken(activity, it.data!!)
                            listener.onLoginSuccess()
                            this@LoginDialogFragment.dismiss()
                        } else {
                            error.set(it.message)
                        }
                    }, {
                        error.set(it.message)
                    })
        }
    }
}
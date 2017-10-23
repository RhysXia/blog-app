package cn.ryths.blog.app.view.fragment

import android.app.DialogFragment
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.FragmentDialogLoginBinding
import cn.ryths.blog.app.service.ServiceCallback
import cn.ryths.blog.app.service.UserService
import cn.ryths.blog.app.utils.TokenUtils

/**
 * 登录注册框
 */
class LoginDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogLoginBinding
    private var userService = UserService()
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
            userService.login(username = username.get(), password = password.get(), callback = object : ServiceCallback<String, String?> {
                override fun success(token: String) {
                    TokenUtils.saveToken(activity, token)
                    this@LoginDialogFragment.dialog.hide()
                    listener.onLoginSuccess()
                }

                override fun fail(error: String?) {
                    this@ViewModel.error.set(error)
                }

            })
        }
    }
}
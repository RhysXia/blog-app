package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.FragmentSettingBinding
import cn.ryths.blog.app.entity.User
import cn.ryths.blog.app.service.ServiceCallback
import cn.ryths.blog.app.service.UserService
import cn.ryths.blog.app.view.viewModel.GlobalViewModel

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private var loginDialogFragment: LoginDialogFragment? = null

    private lateinit var userService: UserService

    companion object {
        fun newInstance(userService: UserService): SettingFragment {
            val fragment = SettingFragment()
            fragment.userService = userService
            return fragment
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
                loginDialogFragment = LoginDialogFragment.newInstance(userService, object : LoginDialogFragment.Listener {
                    override fun onLoginSuccess() {
                        globalViewModel.login = true
                        userService.getSelf(object : ServiceCallback<User, String> {
                            override fun success(result: User) {
                                globalViewModel.user = result
                            }

                            override fun fail(error: String) {
                            }

                        })
                    }

                })
            }
            loginDialogFragment!!.show(fragmentManager, "loginDialogFragment")
        }
    }
}
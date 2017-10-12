package cn.ryths.blog.app.view.tab.setting

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import de.hdodenhof.circleimageview.CircleImageView

class SettingFragment : Fragment() {
    private lateinit var loginBtn: CircleImageView
    private lateinit var loginView: View

    private var loginDialogFragment: LoginDialogFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        loginView = inflater.inflate(R.layout.fragment_dialog_login, container, false)
        initView(view)
        initEvent()
        return view
    }

    private fun initEvent() {
        //登录按键的点击事件
        loginBtn.setOnClickListener {
            if (loginDialogFragment == null) {
                loginDialogFragment = LoginDialogFragment()
            }
            loginDialogFragment!!.show(fragmentManager,"loginDialogFragment")
        }
    }


    private fun initView(view: View) {
        loginBtn = view.findViewById<CircleImageView>(R.id.tab_setting_login)
    }


}
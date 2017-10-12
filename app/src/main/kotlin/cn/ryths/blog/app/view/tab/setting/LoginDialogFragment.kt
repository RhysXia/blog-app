package cn.ryths.blog.app.view.tab.setting

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import cn.ryths.blog.app.R

/**
 * 登录注册框
 */
class LoginDialogFragment : DialogFragment() {

    private lateinit var title: TextView
    private lateinit var username: EditText
    private lateinit var password: EditText

    private lateinit var dialog:View
    /**
     * 登录或注册
     */
    private lateinit var submit: Button
    /**
     * 切换登录注册
     */
    private lateinit var change: Button

    /**
     * 判断当前状态
     */
    private var isLogin = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog = inflater.inflate(R.layout.fragment_dialog_login, container, false)

        title = view.findViewById(R.id.fragment_dialog_login_title)
        username = view.findViewById(R.id.fragment_dialog_login_username)
        password = view.findViewById(R.id.fragment_dialog_login_password)
        submit = view.findViewById(R.id.fragment_dialog_login_submit)
        change = view.findViewById(R.id.fragment_dialog_login_change)
        setLoginView()
        initEvent()

        return dialog
    }

    private fun initEvent() {
        //切换登录注册事件
        change.setOnClickListener {
            if (isLogin) {
                setRegisterView()
            } else {
                setLoginView()
            }
        }
    }

    /**
     * 将界面信息切换到登录
     */
    private fun setLoginView() {
        //隐藏对话框
        title.text = "欢迎登录Ryths博客"
        submit.text = "登录"
        change.text = "点我注册"
        isLogin = true
    }

    /**
     * 将界面信息切换到注册
     */
    private fun setRegisterView() {
        title.text = "欢迎注册Ryths博客"
        submit.text = "注册"
        change.text = "点我登录"
        isLogin = false
    }
}
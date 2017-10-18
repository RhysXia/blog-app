package cn.ryths.blog.app.view.fragment

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import cn.ryths.blog.app.R
import cn.ryths.blog.app.presenter.PresenterCallback
import cn.ryths.blog.app.presenter.UserPresenter
import cn.ryths.blog.app.utils.TokenUtils

/**
 * 登录注册框
 */
class LoginDialogFragment : DialogFragment() {

    private lateinit var title: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    private var userPresenter: UserPresenter? = null

    fun setUserPresenter(userPresenter: UserPresenter) {
        this.userPresenter = userPresenter
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    private lateinit var dialog: View
    /**
     * 登录或注册
     */
    private lateinit var submitBtn: Button
    /**
     * 切换登录注册
     */
    private lateinit var changeToRegisterBtn: Button


    private lateinit var errorLabel: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog = inflater.inflate(R.layout.fragment_dialog_login, container, false)

        title = dialog.findViewById(R.id.fragment_dialog_login_title)
        usernameEditText = dialog.findViewById(R.id.fragment_dialog_login_username)
        errorLabel = dialog.findViewById(R.id.fragment_dialog_login_errorLabel)
        passwordEditText = dialog.findViewById(R.id.fragment_dialog_login_password)
        submitBtn = dialog.findViewById(R.id.fragment_dialog_login_submit)
        changeToRegisterBtn = dialog.findViewById(R.id.fragment_dialog_login_changeToRegister)
        initEvent()
        return dialog
    }

    private fun initEvent() {
        //切换登录注册事件
        changeToRegisterBtn.setOnClickListener {

        }
        //登录事件
        submitBtn.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            userPresenter!!.login(username, password, object : PresenterCallback<String, String> {
                //登录成功时，
                override fun success(result: String) {
                    TokenUtils.saveToken(activity, result)
                    val transaction = this@LoginDialogFragment.fragmentManager.beginTransaction()
                    transaction.remove(this@LoginDialogFragment)
                    transaction.commit()
                    if (listener != null) {
                        listener!!.onLoginSuccess()
                    }
                }

                override fun fail(error: String) {
                    errorLabel.text = error
                    errorLabel.visibility = View.VISIBLE
                }
            })
        }
    }

    interface Listener {
        fun onLoginSuccess()
    }
}
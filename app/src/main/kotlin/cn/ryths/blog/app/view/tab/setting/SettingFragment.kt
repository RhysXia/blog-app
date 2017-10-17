package cn.ryths.blog.app.view.tab.setting

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import cn.ryths.blog.app.R
import cn.ryths.blog.app.entity.User
import cn.ryths.blog.app.presenter.PresenterCallback
import cn.ryths.blog.app.presenter.UserPresenter
import cn.ryths.blog.app.utils.TokenUtils
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class SettingFragment : Fragment() {
    private lateinit var loginBtn: CircleImageView
    private lateinit var loginView: View
    private lateinit var container: View
    private var loginDialogFragment: LoginDialogFragment? = null
    private val userPresenter = UserPresenter()
    override fun onCreateView(inflater: LayoutInflater, _container: ViewGroup?, savedInstanceState: Bundle?): View {
        container = inflater.inflate(R.layout.fragment_setting, _container, false)
        loginView = inflater.inflate(R.layout.fragment_dialog_login, _container, false)
        initView()
        initEvent()
        return container
    }

    private fun initEvent() {
        //登录按键的点击事件
        loginBtn.setOnClickListener {
            if (loginDialogFragment == null) {
                loginDialogFragment = LoginDialogFragment()
                loginDialogFragment!!.setUserPresenter(userPresenter)
                loginDialogFragment!!.setListener(object : LoginDialogFragment.Listener {
                    //登录成功时，
                    override fun onLoginSuccess() {
                        loginSuccess()
                    }
                })
            }
            loginDialogFragment!!.show(fragmentManager, "loginDialogFragment")
        }
        //注销事件
    }

    /**
     * 更新视图
     */
    private fun refreshView(){
        //获取token
        val token = TokenUtils.getToken(activity)
        //如果token不存在，说明没有登录
        if(token == null){
        }

    }

    private fun loginSuccess() {
        notLoginContainer.visibility = View.GONE
        loginContainer.visibility = View.VISIBLE
        userPresenter.getSelf(object : PresenterCallback<User, String> {
            override fun success(result: User) {
                Picasso.with(this@SettingFragment.view.context)
                        .load(result.avatar)
                        .into(userAvatar)
                userNickname.text = result.nickname
                userSummary.text = result.summary
            }

            override fun fail(error: String) {
            }

        })
    }


    private lateinit var notLoginContainer: LinearLayout

    private lateinit var loginContainer: LinearLayout

    private lateinit var userSummary: TextView

    private lateinit var userAvatar: CircleImageView

    private lateinit var userNickname: TextView

    private lateinit var logout: TextView

    private fun initView() {
        loginBtn = container.findViewById(R.id.tab_setting_login)
        notLoginContainer = container.findViewById(R.id.tab_setting_notLoginContainer)
        loginContainer = container.findViewById(R.id.tab_setting_loginContainer)
        userAvatar = container.findViewById<CircleImageView>(R.id.tab_setting_login_user_avatar)
        userNickname = container.findViewById<TextView>(R.id.tab_setting_login_user_nickname)
        userSummary = container.findViewById<TextView>(R.id.tab_setting_login_user_summary)
        logout = container.findViewById<TextView>(R.id.tab_setting_login_logout)
    }


}
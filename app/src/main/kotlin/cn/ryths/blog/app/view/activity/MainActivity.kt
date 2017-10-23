package cn.ryths.blog.app.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.ActivityMainBinding
import cn.ryths.blog.app.service.ArticleService
import cn.ryths.blog.app.service.UserService
import cn.ryths.blog.app.view.fragment.TabFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val articleService = ArticleService()
    private val userService = UserService()


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val transition = fragmentManager.beginTransaction()
        val tabFragment = TabFragment.newInstance()

        transition.add(R.id.main_fragment, tabFragment)
        transition.show(tabFragment)
        transition.commit()

    }


}

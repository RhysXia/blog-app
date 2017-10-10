package cn.ryths.blog.app.view.activity

import android.app.Fragment
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import cn.ryths.blog.app.R
import cn.ryths.blog.app.view.category.CategoryFragment
import cn.ryths.blog.app.view.index.IndexFragment
import cn.ryths.blog.app.view.setting.SettingFragment


class MainActivity : AppCompatActivity() {


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initEvent()
        //显示第一个视图
        tabActive(0)
    }

    private var tabs: List<View> = ArrayList()
    private var fragments: List<Fragment> = ArrayList()

    private fun initView() {
        tabs += findViewById(R.id.tab_index) as LinearLayout
        fragments += IndexFragment()
        tabs += findViewById(R.id.tab_category) as LinearLayout
        fragments += CategoryFragment()
        tabs += findViewById(R.id.tab_more) as LinearLayout
        fragments += SettingFragment()
    }

    private fun initEvent() {
        for ((index, tab) in tabs.withIndex()) {
            tab.setOnClickListener {
                tabActive(index)
            }
        }
    }

    private fun tabActive(index: Int) {
        val transaction = fragmentManager.beginTransaction()
        //隐藏所有
        fragments.forEach {
            if (it.isVisible) {
                transaction.hide(it)
            }
        }
        //取消tab颜色
        tabs.forEach {
            val imageView = it.findViewWithTag<ImageView>("tab_image")
            imageView.imageTintList = ColorStateList.valueOf(Color.BLACK)
        }
        //判断是否存在当前fragment
        val fragment = fragmentManager.findFragmentByTag("fragment${index}")
        if (fragment == null) {
            transaction.add(R.id.fragment_main, fragments[index], "fragment${index}")
        }
        //设置tab颜色
        val imageView = tabs[index].findViewWithTag<ImageView>("tab_image")
        imageView.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.primary))
        //显示当前
        transaction.show(fragments[index])
        transaction.commit()
    }
}

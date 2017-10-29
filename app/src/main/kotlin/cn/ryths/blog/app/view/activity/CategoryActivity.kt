package cn.ryths.blog.app.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.ActivityCategoryBinding
import cn.ryths.blog.app.view.fragment.ArticleListFragment

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)

        val transaction = fragmentManager.beginTransaction()

        val categoryId = intent.getLongExtra("categoryId", 0)

        val fragment = ArticleListFragment.newInstance(code = ArticleListFragment.CODE_CATEGORY, data = categoryId)
        transaction.add(R.id.activity_category_frameLayout, fragment)
        transaction.show(fragment)
        transaction.commit()
        val viewModel = ViewModel()
        viewModel.categoryName = intent.getStringExtra("categoryName")
        binding.viewModel = viewModel
        binding.activityCategoryToolbar.setNavigationOnClickListener {
            this@CategoryActivity.finish()
        }
    }

    class ViewModel {
        var categoryName: String = ""
    }
}

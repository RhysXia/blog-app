package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    companion object {
        fun newInstance(): CategoryFragment {
            return CategoryFragment()
        }
    }

    private lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)

        return binding.root
    }
}
package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, parent, false)

        return binding.root
    }


}
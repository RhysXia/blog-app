package cn.ryths.blog.app.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.databinding.ObservableInt
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.FragmentTabBinding

class TabFragment : Fragment() {
    private lateinit var binding: FragmentTabBinding
    private val fragmentMap = ArrayMap<Int, Fragment>()

    companion object {
        fun newInstance(): TabFragment {
            return TabFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab, parent, false)
        val viewModel = ViewModel()
        binding.viewModel = viewModel
        return binding.root
    }

    inner class ViewModel {

        val TAB_INDEX = 0
        val TAB_CATEGORY = 1
        val TAB_SETTING = 2

        /**
         * 当前激活的tab
         */
        var activeTab = ObservableInt(-1)

        init {
            tabClick(this.TAB_INDEX)
        }

        /**
         * tab点击事件
         *
         * @param index
         */
        fun tabClick(index: Int?) {
            //如果点击的tab就是已经激活的tab，则不做任何操作
            if (index == activeTab.get()) {
                return
            }

            val fm = activity.fragmentManager

            val ft = fm.beginTransaction()

            val oldFragment = fragmentMap[activeTab.get()]
            if (oldFragment != null) {
                ft.hide(oldFragment)
            }

            var newFragment: Fragment? = fragmentMap[index]

            if (newFragment == null) {
                when (index) {
                    TAB_INDEX -> {
                        newFragment = ArticleListFragment.newInstance()
                    }
                    TAB_CATEGORY -> {
                        newFragment = CategoryFragment.newInstance()
                    }
                    TAB_SETTING -> {
                        newFragment = SettingFragment.newInstance()
                    }
                }
                fragmentMap.put(index, newFragment)
            }
            if (newFragment != null) {
                if (!newFragment.isAdded){
                    ft.add(R.id.fragment_tab, newFragment)
                }
                ft.show(newFragment)
            }
            ft.commit()
            //切换activeTab
            activeTab.set(index!!)
        }
    }
}
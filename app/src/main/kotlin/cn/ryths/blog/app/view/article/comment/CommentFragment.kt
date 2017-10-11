package cn.ryths.blog.app.view.article.comment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ryths.blog.app.R

class CommentFragment : Fragment() {
    private var articleId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_comment, container, false)



        return view
    }
}
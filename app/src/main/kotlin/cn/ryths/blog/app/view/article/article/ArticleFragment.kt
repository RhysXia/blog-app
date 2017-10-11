package cn.ryths.blog.app.view.article.article

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import cn.ryths.blog.app.R
import de.hdodenhof.circleimageview.CircleImageView

class ArticleFragment : Fragment() {


    private lateinit var toolbar: Toolbar

    private lateinit var authorAvatar: CircleImageView

    private lateinit var authorNickname: TextView

    private lateinit var authorSummary: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_article_info, container, false)

        toolbar = view.findViewById(R.id.article_info_toolbar)
        authorAvatar = view.findViewById(R.id.article_info_author_avatar)
        authorNickname = view.findViewById(R.id.article_info_author_nickname)
        authorSummary = view.findViewById(R.id.article_info_author_summary)
        return view
    }


}
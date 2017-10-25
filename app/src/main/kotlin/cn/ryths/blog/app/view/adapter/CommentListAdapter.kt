package cn.ryths.blog.app.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.ryths.blog.app.R
import cn.ryths.blog.app.databinding.CommentListItemBinding
import cn.ryths.blog.app.entity.Comment

class CommentListAdapter : RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {
    private var comments: List<Comment> = ArrayList()

    fun addAll(comments: List<Comment>) {
        val pos = this.comments.size
        this.comments += comments
        this.notifyItemRangeInserted(pos, comments.size)
    }

    fun setAll(comments: List<Comment>) {
        this.comments = comments
        this.notifyDataSetChanged()
    }

    private lateinit var binding: CommentListItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.comment_list_item, parent, false)

        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindView(comments[position])
    }

    class CommentViewHolder(private val binding: CommentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(comment: Comment){
            binding.comment = comment
        }
    }
}
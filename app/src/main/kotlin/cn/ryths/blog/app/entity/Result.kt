package cn.ryths.blog.app.entity

import com.google.gson.annotations.SerializedName

data class Result<T>(
        var code: Code = Code.SUCCESS,
        var data: T? = null,
        var pagination: Pagination? = null,
        var message: String? = null
)

enum class Code {
    @SerializedName("0")
    SUCCESS,
    @SerializedName("1")
    FAIL
}

data class Pagination(
        var total: Long? = null,
        var totalPage: Int? = null,
        var curentPage: Int? = null,
        var pageSize: Int? = null
)
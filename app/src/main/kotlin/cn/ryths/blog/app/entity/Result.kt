package cn.ryths.blog.app.entity

data class Result<T>(
        var code: Code = Code.SUCCESS,
        var data: T? = null,
        var pagination: Pagination? = null,
        var message: String? = null
)

enum class Code {
    SUCCESS, FAIL
}

data class Pagination(
        var total: Long? = null,
        var totalPage: Int? = null,
        var curentPage: Int? = null,
        var pageSize: Int? = null
)
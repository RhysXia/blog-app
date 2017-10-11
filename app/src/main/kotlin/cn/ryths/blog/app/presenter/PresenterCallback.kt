package cn.ryths.blog.app.presenter

/**
 * presenter执行结果的回调,
 * [S]:成功时回调的参数类型
 * [E]:失败时回调的参数类型
 */
interface PresenterCallback<in S, in E> {
    /**
     * 成功的回调
     */
    fun success(result: S)

    /**
     * 执行失败的回调
     */
    fun fail(error: E)
}
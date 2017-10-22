package cn.ryths.blog.app.view.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import cn.ryths.blog.app.BR;
import cn.ryths.blog.app.entity.User;

/**
 * 全局model数据，单例模式
 */
public class GlobalViewModel extends BaseObservable {

    private static GlobalViewModel globalViewModel;
    /**
     * 是否登陆
     */
    @Bindable
    private Boolean login = false;
    /**
     * 登陆用户信息
     */
    @Bindable
    private User user = null;

    private GlobalViewModel() {
    }

    /**
     * 生成单例对象
     * @return
     */
    public static GlobalViewModel getInstance() {
        if (globalViewModel == null) {
            globalViewModel = new GlobalViewModel();
        }
        return globalViewModel;
    }

    public Boolean getLogin() {
        return login;
    }

    /**
     * 设置登陆信息
     * @param login
     */
    public void setLogin(Boolean login) {
        this.login = login;
        this.notifyPropertyChanged(BR.login);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.notifyPropertyChanged(BR.user);
    }


}

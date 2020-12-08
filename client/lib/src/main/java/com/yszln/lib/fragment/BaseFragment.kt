package com.yszln.lib.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yszln.lib.R
import com.yszln.lib.activity.RootActivity
import com.yszln.lib.ibase.IBaseVM
import com.yszln.lib.ibase.IBaseView
import com.yszln.lib.ibase.ILoadMore
import com.yszln.lib.loading.EmptyView
import com.yszln.lib.utils.LogUtil
import com.yszln.lib.viewmodel.BaseViewModel
import com.yszln.lib.viewmodel.LoadHelper
import com.yszln.lib.viewmodel.RefreshHelper
import java.lang.reflect.ParameterizedType

/**
 * @author: yszln
 * @date: 2020/8/9 21:54
 * @description: viewModel fragment
 * @history:
 */
abstract class BaseFragment<VM : BaseViewModel> : SuperFragment(),
    IBaseView, IBaseVM, ILoadMore {

    protected lateinit var mViewModel: VM

    protected var swipeRefreshLayout: SwipeRefreshLayout? = null
    protected var mEmptyView: EmptyView? = null

    // 是否第一次加载
    private var isFirstLoad = true

    protected lateinit var mActivity: RootActivity

    private var lastCreateView:View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as RootActivity
    }

    /**
     * 是否懒加载，重写修改
     */
    open fun isLazyLoad() = true

    override fun onResume() {
        super.onResume()
        LogUtil.e("生命周期：${this.javaClass.name}_onResume:$isFirstLoad")
        if (isFirstLoad && isLazyLoad()) {
            //懒加载
            isFirstLoad = false
            refreshData()

        }
    }

    /**
     * 关闭懒加载调用这里，每次显示都去请求网络
     */
    override fun onHiddenChanged(hidden: Boolean) {
        LogUtil.e("生命周期：${this.javaClass.name}_onHiddenChanged:$hidden")
        super.onHiddenChanged(hidden)
        if (!isLazyLoad() && hidden) {
            refreshData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.e("生命周期：${this.javaClass.name}_onViewCreated")
        mActivity.currentFragment = this
        mActivity.hideKeyboard()
        initViewModel()
        initRefresh()
        observer()

    }

    override fun showLoading() {
        if (activity is IBaseVM) {
            (activity as IBaseVM).showLoading()
        }
    }

    override fun dismissLoading() {
        if (activity is IBaseVM) {
            (activity as IBaseVM).dismissLoading()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.e("生命周期：${this.javaClass.name}_onDestroyView")
        //view被销毁
        isFirstLoad = false
    }

    /**
     * 设置刷新头
     */
    private fun initRefresh() {
        swipeRefreshLayout = view?.findViewById(R.id.mRefreshLayout)
        mEmptyView = view?.findViewById(R.id.mEmptyView)
        //初始化刷新
        RefreshHelper(
            swipeRefreshLayout,
            this,
            mViewModel,
            this,
            mEmptyView
        )
        LoadHelper(
            this,
            mViewModel,
            this,
            this,
            mEmptyView
        )


    }

    override fun onRefresh() {
        super.onRefresh()
        refreshData()
    }


    private fun initViewModel() {
        //取第一个泛型的class
        val vmClazz =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        //创建viewModel
        mViewModel = ViewModelProvider(this).get(vmClazz)

    }

    /**
     * 拦截返回事件
     */
    open fun onBackPressed() = true


}
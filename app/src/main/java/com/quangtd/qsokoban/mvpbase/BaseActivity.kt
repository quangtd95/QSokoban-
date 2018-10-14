package com.quangtd.qsokoban.mvpbase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import java.lang.reflect.ParameterizedType

/**
 * Created by QuangTD
 * on 1/12/2018.
 */
@SuppressLint("Registered")
open class BaseActivity<V : IBaseView, out P : BasePresenter<V>> : AppCompatActivity(), IBaseView {
    override fun getViewContext(): Context {
        return this
    }

    private var presenter: P? = null
    private var view: V? = null

    fun getPresenter(v: V): P {
        if (presenter == null) {
            view = v
            val parameterType = this.javaClass.genericSuperclass as ParameterizedType
            val e = parameterType.actualTypeArguments[1] as Class<*>
            val classDefinition = Class.forName(e.name)
            this.presenter = classDefinition.newInstance() as P
            this.presenter!!.attachView(v)
            this.presenter!!.onInit()
            return presenter!!
        }
        return presenter!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusAndNavigationBar()
    }

    /**
     * pass any view to make transition.
     * TODO
     */
    fun startActivity(cls: Class<*>, view: View) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "")
        startActivityWithSharedElement(cls, options)

    }

    fun startActivity(cls: Class<*>) {
        startActivity(Intent(this, cls))
    }

    override fun startActivity(clazz: Class<*>, bundle: Bundle) {
        startActivity(Intent(this, clazz).apply { putExtras(bundle) })

    }

    fun startActivityWithSharedElement(cls: Class<*>, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this, cls)
        startActivity(intent, optionsCompat.toBundle())
    }

    fun popFragment(): Boolean {
        if (fragmentManager.backStackEntryCount >= 0) {
            fragmentManager.popBackStackImmediate()
            return true
        }
        return false
    }

    fun hideFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.name)
        }
        transaction.hide(fragment)
        transaction.commit()
    }

    fun showFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.name)
        }
        transaction.show(fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment, id: Int, addToBackStack: Boolean) {
        val tag = fragment.javaClass.name
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.replace(id, fragment, tag)
        transaction.commit()
        supportFragmentManager.executePendingTransactions()
    }

    fun replaceFragmentWithAnimation(fragment: Fragment, id: Int, addToBackStack: Boolean) {
        val tag = fragment.javaClass.name
        val transaction = supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.view_enter_from_left, R.anim.view_exit_to_right, R.anim.view_enter_from_right, R.anim.view_exit_to_left)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.replace(id, fragment, tag)
        transaction.commit()
        supportFragmentManager.executePendingTransactions()
    }

    fun addFragment(fragment: Fragment, id: Int, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.name)
        }
        transaction.add(id, fragment)
        transaction.commit()
    }

    fun updateStatusBarColor(color: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor(color)
        }
    }

    private fun hideStatusAndNavigationBar() {
        val decorView = window.decorView
        var uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        if (Build.VERSION.SDK_INT >= 19) {
            uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
        }
        decorView.systemUiVisibility = uiOptions
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideStatusAndNavigationBar()
        }
    }

    fun uiChangeListener() {
        val decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                hideStatusAndNavigationBar()
            }
        }
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        super.onDestroy()
    }


}
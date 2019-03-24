package com.lt.recyclerview_album_kotlin.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

/**
 * @作者: LinTan
 * @日期: 2018/12/25 17:11
 * @版本: 1.2
 * @描述: //屏幕的工具类。
 * 源址: https://blog.csdn.net/lmj623565791/article/details/38965311
 * 1.0: Initial Commit
 * 1.1: 替换setNoTitleBar()为setNoActionBar()
 * 1.2: 修改snapShotWithStatusBar()和snapShotWithoutStatusBar()的返回值类型为Drawable
 */

object ScreenUtil {

    /**
     * 设置无ActionBar(extends AppCompatActivity)，setContentView()后调用
     */
    fun setNoActionBar(appCompatActivity: AppCompatActivity) {
        appCompatActivity.supportActionBar!!.hide()
    }

    /**
     * 设置无ActionBar(extends AppCompatActivity)，setContentView()后调用
     */
    fun cancelNoActionBar(appCompatActivity: AppCompatActivity) {
        appCompatActivity.supportActionBar!!.show()
    }

    /**
     * 设置无StatusBar
     */
    fun setNoStatusBar(activity: Activity) {
        activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    /**
     * 取消无StatusBar
     */
    fun cancelNoStatusBar(activity: Activity) {
        activity.window.clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }


    /**
     * 获得屏幕高度
     */
    fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    /**
     * 获得屏幕宽度
     */
    fun getScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    /**
     * 获得状态栏的高度
     */
    fun getStatusBarHeight(context: Context): Int {
        var statusHeight = -1
        try {
            @SuppressLint("PrivateApi")
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height").get(`object`).toString())
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusHeight
    }

    /**
     * 获取当前屏幕截图，包含状态栏(但状态栏无内容)
     */
    fun snapShotWithStatusBar(activity: Activity): Drawable {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val viewDrawingCache = view.drawingCache
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        val bitmap = Bitmap.createBitmap(viewDrawingCache, 0, 0, width, height)
        view.destroyDrawingCache()
        return BitmapDrawable(activity.resources, bitmap)
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    fun snapShotWithoutStatusBar(activity: Activity): Drawable {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val viewDrawingCache = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        val bitmap = Bitmap.createBitmap(viewDrawingCache, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return BitmapDrawable(activity.resources, bitmap)
    }
}

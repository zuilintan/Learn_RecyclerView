package com.lt.recyclerview_album_kotlin.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

import java.io.File

/**
 * @作者: LinTan
 * @日期: 2018/12/25 16:53
 * @版本: 1.2
 * @描述: //Glide的工具类。注意引入依赖。
 * 源址: https://blog.csdn.net/a2012s/article/details/80988439
 * 1.0: Initial Commit
 * 1.1: 泛化所有.load()中参数，便于传入除url外的参数。eg:File，int，byte[]，Drawable
 * 1.2: 删除'jp.wasabeef:glide-transformations'相关的方法，使用较少
 *
 *
 * implementation 'com.github.bumptech.glide:glide:4.8.0'
 * annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
 */

class GlideUtil {

    companion object {
        private val placeholderSoWhite = android.R.color.white
        private val errorSoWhite = android.R.color.white
        //private static final int soWhite = android.R.color.white;

        /*
     *加载图片(默认)
     */
        fun <T> loadImage(context: Context, url: T, imageView: ImageView) {
            val options = RequestOptions()
                    //.centerCrop()
                    .placeholder(placeholderSoWhite)//占位图
                    .error(errorSoWhite)//错误图
                    //.priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(context).load(url).apply(options).into(imageView)
        }

        /**
         * 指定图片大小;使用override()方法指定了一个图片的尺寸
         * Glide现在只会将图片加载成width*height像素的尺寸，而不会管你的ImageView的大小是多少了
         * 如果你想加载一张图片的原始尺寸的话，可以使用Target.SIZE_ORIGINAL关键字。eg:
         * override(Target.SIZE_ORIGINAL)
         */
        fun <T> loadImageSize(context: Context, url: T, imageView: ImageView, width: Int, height: Int) {
            val options = RequestOptions()
                    .centerCrop()
                    .placeholder(placeholderSoWhite)//占位图
                    .error(errorSoWhite)//错误图
                    .override(width, height)
                    //.priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(context).load(url).apply(options).into(imageView)
        }

        /**
         * 禁用内存缓存功能
         * diskCacheStrategy()方法基本上就是Glide硬盘缓存功能的一切，它可以接收五种参数：
         *
         *
         * DiskCacheStrategy.NONE:表示不缓存任何内容。
         * DiskCacheStrategy.DATA:表示只缓存原始图片。
         * DiskCacheStrategy.RESOURCE:表示只缓存转换过后的图片。
         * DiskCacheStrategy.ALL:表示既缓存原始图片，也缓存转换过后的图片。
         * DiskCacheStrategy.AUTOMATIC:表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
         */

        fun <T> loadImageSizekipMemoryCache(context: Context, url: T, imageView: ImageView) {
            val options = RequestOptions()
                    .placeholder(placeholderSoWhite)//占位图
                    .error(errorSoWhite)//错误图
                    .skipMemoryCache(true)//禁用掉Glide的内存缓存功能
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(context).load(url).apply(options).into(imageView)
        }

        /**
         * 加载圆形图片
         */
        fun <T> loadCircleImage(context: Context, url: T, imageView: ImageView) {
            val options = RequestOptions()
                    .centerCrop()
                    .circleCrop()//设置圆形
                    .placeholder(placeholderSoWhite)
                    .error(errorSoWhite)
                    //.priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(context).load(url).apply(options).into(imageView)
        }

        /**
         * 预先加载图片
         * 在使用图片之前，预先把图片加载到缓存
         */
        fun <T> preloadImage(context: Context, url: T) {
            Glide.with(context)
                    .load(url)
                    .preload()
        }

        fun dp2px(context: Context, dpValue: Float): Int {
            val density = context.resources.displayMetrics.density
            return (dpValue * density + 0.5f).toInt()
        }//加0.5f以四舍五入，4.4+0.5=4.9转为int还是4；而4.5 + 0.5 = 5.0转换成int后就是5
    }

    /**
     * Glide.with(this).asGif()//强制指定加载动态图片
     * 如果加载的图片不是gif，则asGif()会报错， 当然，asGif()不写也是可以正常加载的。
     * 加入了一个asBitmap()方法，这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
     * 如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
     */
    private fun <T> loadGif(context: Context, url: T, imageView: ImageView) {
        val options = RequestOptions()
                .placeholder(placeholderSoWhite)
                .error(errorSoWhite)
        Glide.with(context)
                .load(url)
                .apply(options)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(imageView)
    }

    fun downloadImage(context: Context, url: String) {
        Thread(Runnable {
            try {
                //String url = "http://www.guolin.tech/book.png";
                val target = Glide.with(context)
                        .asFile()
                        .load(url)
                        .submit()
                val imageFile = target.get()
                Log.wtf("logcat", "下载好的图片文件路径=" + imageFile.path)
                //runOnUiThread(new Runnable() {
                //    @Override
                //    public void run() {
                //        Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
                //    }
                //});
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }
}

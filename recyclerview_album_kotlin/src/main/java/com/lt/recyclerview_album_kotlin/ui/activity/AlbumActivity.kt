package com.lt.recyclerview_album_kotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.jaeger.library.StatusBarUtil
import com.lt.recyclerview_album_kotlin.R
import com.lt.recyclerview_album_kotlin.adapter.AlbumAdapter
import com.lt.recyclerview_album_kotlin.base.BaseRecyclerViewAdapter
import com.lt.recyclerview_album_kotlin.bean.AlbumBean
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.include_album_top.*

class AlbumActivity : AppCompatActivity() {
    private lateinit var mAlbumAdapter: AlbumAdapter
    private val mImages = intArrayOf(R.drawable.img_avatar_01, R.drawable.img_avatar_02, R.drawable.img_avatar_03, R.drawable.img_avatar_04, R.drawable.img_avatar_05, R.drawable.img_avatar_06, R.drawable.img_avatar_07, R.drawable.img_avatar_08, R.drawable.img_avatar_09, R.drawable.img_avatar_10, R.drawable.img_avatar_11)

    companion object {
        var mAlbumBeanList: MutableList<AlbumBean> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        for (i in 0..9) {
            initData()
        }
        initView()
        initListener()
    }

    private fun initData() {
        for (i in 0..10) {
            val albumBean = AlbumBean(getDrawable(mImages[i]), resources.getResourceEntryName(mImages[i]))
            mAlbumBeanList.add(albumBean)
        }
    }

    private fun initView() {
        setSupportActionBar(tb_top)
        StatusBarUtil.setTransparent(this)
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        mAlbumAdapter = AlbumAdapter(R.layout.album_item, mAlbumBeanList)
        rcv_album.layoutManager = layoutManager
        rcv_album.adapter = mAlbumAdapter
    }

    private fun initListener() {
        mAlbumAdapter.setItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val bundle = Bundle()
                bundle.putInt("position", position)
                val intent = Intent()
                intent.setClass(this@AlbumActivity, AlbumDetailActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.toolBar_menu_search -> return true
            R.id.toolBar_menu_explore -> return true
            R.id.toolBar_menu_setting -> return true
            R.id.toolBar_menu_about -> return true
        }
        return super.onOptionsItemSelected(item)
    }
}
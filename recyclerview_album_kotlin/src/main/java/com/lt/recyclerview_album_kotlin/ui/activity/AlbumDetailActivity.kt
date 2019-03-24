package com.lt.recyclerview_album_kotlin.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import com.lt.recyclerview_album_kotlin.R
import com.lt.recyclerview_album_kotlin.adapter.AlbumDetailAdapter
import com.lt.recyclerview_album_kotlin.bean.AlbumBean
import com.lt.recyclerview_album_kotlin.util.ScreenUtil
import kotlinx.android.synthetic.main.activity_album_detail.*

class AlbumDetailActivity : AppCompatActivity() {
    private lateinit var mAlbumDetailAdapter: AlbumDetailAdapter
    private lateinit var mAlbumBeanList: MutableList<AlbumBean>
    private var mCurrentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)
        initData()
        initView()
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initData() {
        mAlbumBeanList = AlbumActivity.mAlbumBeanList
        val intent = intent
        val bundle = intent.extras
        mCurrentPosition = bundle.getInt("position")
    }

    private fun initView() {
        ScreenUtil.setNoStatusBar(this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mAlbumDetailAdapter = AlbumDetailAdapter(R.layout.album_detail_item, mAlbumBeanList)
        rcv_album_detail.layoutManager = layoutManager
        rcv_album_detail.adapter = mAlbumDetailAdapter
        rcv_album_detail.scrollToPosition(mCurrentPosition)//根据bundle接收的position滑动到指定位置
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(rcv_album_detail)//这两句实现了ViewPage效果
    }
}

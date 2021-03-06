package com.example.meet.ui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framework.base.BasePageAdapter;
import com.example.framework.base.BaseUIActivity;
import com.example.framework.manager.MediaPlayerManager;
import com.example.framework.utils.AnimUtils;
import com.example.meet.R;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends BaseUIActivity implements View.OnClickListener {

    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.iv_music_switch)
    ImageView iv_music_switch;
    @BindView(R.id.tv_guide_skip)
    TextView tv_guide_skip;
    @BindView(R.id.iv_guide_point_1)
    ImageView iv_guide_point_1;
    @BindView(R.id.iv_guide_point_2)
    ImageView iv_guide_point_2;
    @BindView(R.id.iv_guide_point_3)
    ImageView iv_guide_point_3;



    ImageView iv_guide_star;
    ImageView iv_guide_night;
    ImageView iv_guide_smile;


    private View view1, view2, view3;

    private List<View> mPageList = new ArrayList<> ();
    private BasePageAdapter mPageAdapter;

    private MediaPlayerManager mGuideMusic;

    private ObjectAnimator mAnim;

    /**
     * 1.ViewPager : 适配器|帧动画播放
     * 2.小圆点的逻辑
     * 3.歌曲的播放
     * 4.属性动画旋转
     * 5.跳转
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_guide);
        ButterKnife.bind (this);
        initView ();

    }

    private void initView() {
        view1 = View.inflate (this, R.layout.layout_pager_guide_1, null);
        view2 = View.inflate (this, R.layout.layout_pager_guide_2, null);
        view3 = View.inflate (this, R.layout.layout_pager_guide_3, null);
        mPageList.add (view1);
        mPageList.add (view2);
        mPageList.add (view3);

        //预加载
        mViewPager.setOffscreenPageLimit (mPageList.size ());

        //PageAdapter
        mPageAdapter = new BasePageAdapter (mPageList);
        mViewPager.setAdapter (mPageAdapter);

        //帧动画
        iv_guide_star=view1.findViewById (R.id.iv_guide_star);
        iv_guide_night=view2.findViewById (R.id.iv_guide_night);
        iv_guide_smile=view3.findViewById (R.id.iv_guide_smile);

        //播放动画
        AnimationDrawable animStar=(AnimationDrawable) iv_guide_star.getBackground ();
        AnimationDrawable animNight=(AnimationDrawable) iv_guide_night.getBackground ();
        AnimationDrawable animSmile=(AnimationDrawable) iv_guide_smile.getBackground ();
        animStar.start ();
        animNight.start ();
        animSmile.start ();

        //圆点逻辑
        //ViewPager添加侦听时间
        mViewPager.setOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //添加点击事件
        iv_music_switch.setOnClickListener (this);
        tv_guide_skip.setOnClickListener (this);

        //歌曲逻辑

        startMusic();
    }


    /**
     * 动态选择小圆点
     * @param position
     */
    private void selectPoint(int position) {
        switch (position){
            case 0:
                iv_guide_point_1.setImageResource (R.drawable.img_guide_point_p);
                iv_guide_point_2.setImageResource (R.drawable.img_guide_point);
                iv_guide_point_3.setImageResource (R.drawable.img_guide_point);
                break;
            case 1:
                iv_guide_point_1.setImageResource (R.drawable.img_guide_point);
                iv_guide_point_2.setImageResource (R.drawable.img_guide_point_p);
                iv_guide_point_3.setImageResource (R.drawable.img_guide_point);
                break;
            case 2:
                iv_guide_point_1.setImageResource (R.drawable.img_guide_point);
                iv_guide_point_2.setImageResource (R.drawable.img_guide_point);
                iv_guide_point_3.setImageResource (R.drawable.img_guide_point_p);
                break;
        }
    }


    private void startMusic() {
        mGuideMusic=new MediaPlayerManager ();
        mGuideMusic.setLooping (true);
        AssetFileDescriptor file=getResources ().openRawResourceFd (R.raw.mymusic);
        mGuideMusic.startPlay (file);

        //循环播放
        mGuideMusic.setOnCompletionListener (new MediaPlayer.OnCompletionListener () {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mGuideMusic.startPlay (file);
            }
        });

        //音乐图标旋转动画
        mAnim= AnimUtils.rotation (iv_music_switch);
        mAnim.start ();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.iv_music_switch:
                if(mGuideMusic.MEDIA_STATUS==MediaPlayerManager.MEDIA_STATUS_PAUSE){
                    mAnim.start ();
                    mGuideMusic.continuePlay ();
                    iv_music_switch.setImageResource (R.drawable.img_guide_music);
                }else if(mGuideMusic.MEDIA_STATUS==MediaPlayerManager.MEDIA_STATUS_PLAY){
                    mAnim.pause ();
                    mGuideMusic.pausePlay ();
                    iv_music_switch.setImageResource (R.drawable.img_guide_music_off);
                }
                break;
            case R.id.tv_guide_skip:
                startActivity (new Intent (GuideActivity.this,LoginActivity.class));
                finish ();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        mGuideMusic.stopPlay ();
    }
}

package com.hnpolice.daniel.refreshwithappbarlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import net.yuntutv.luoxiaoke.refreshlibrary.PullToRefreshBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * create by luoxiaoke 2017/3/30 11:01
 * eamil:wtimexiaoke@gmail.com
 * github:https://github.com/103style
 * csdn:http://blog.csdn.net/lxk_1993
 * 简书：http://www.jianshu.com/u/109656c2d96f
 * <p>
 */
public class NotBottomTabActivity extends AppCompatActivity {

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.test_title)
    Toolbar title;

    private NoBottomTabActivityXmlView rootView;
    private List<String> mDatas;
    private DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = new NoBottomTabActivityXmlView(this);
        setContentView(rootView);
        ButterKnife.bind(this);
        initParams();
        initSetup();
    }

    private void initParams() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add(String.valueOf(i));
        }

        recycleView.setLayoutManager(new LinearLayoutManager(this));
        dataAdapter = new DataAdapter(this, mDatas);
        recycleView.setAdapter(dataAdapter);
    }

    private void initSetup() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                /**
                 * {@link com.hnpolice.daniel.refreshwithappbarlayout.NoBottomTabActivityXmlView}
                 * verticalOffset=0 为 appBarLayout 完全展开的时候
                 * 完全展开 即可拦截下拉事件 执行 刷新操作
                 */
                rootView.setCanRefresh(verticalOffset == 0);
                //透明度系数
                double alphaScale = Math.abs(verticalOffset) * 1.0f / appBarLayout.getTotalScrollRange();
                //设置顶部标题的底色
                title.setBackgroundColor(
                        Color.argb(
                                (int) (255 * alphaScale),
                                63,
                                81,
                                181));
            }
        });

        rootView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<FrameLayout>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<FrameLayout> refreshView) {
                addData(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<FrameLayout> refreshView) {
                rootView.onPullUpRefreshComplete();
            }
        });

        title.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 延时2s添加数据
     */
    private void addData(final boolean refresh) {
        Observable
                .timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (refresh) {
                            mDatas.add(0, "Added after refresh...");
                            dataAdapter.setDatasAndNotify(mDatas);
                            rootView.setLastUpdatedLabel(formatDateTime(System.currentTimeMillis()));
                            rootView.onPullDownRefreshComplete();
                        }
                    }
                })
                .subscribe();
    }


    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date(time));
    }

}

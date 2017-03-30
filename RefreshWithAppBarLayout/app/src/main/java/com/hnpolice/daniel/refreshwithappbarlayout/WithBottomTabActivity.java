package com.hnpolice.daniel.refreshwithappbarlayout;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
 * create by luoxiaoke 2017/3/30 11:02
 * eamil:wtimexiaoke@gmail.com
 * github:https://github.com/103style
 * csdn:http://blog.csdn.net/lxk_1993
 * 简书：http://www.jianshu.com/u/109656c2d96f
 */
public class WithBottomTabActivity extends AppCompatActivity {

    @BindView(R.id.content)
    WithBottomContentView contentView;
    @BindView(R.id.bottom)
    LinearLayout bottom;

    private RecyclerView recyclerView;
    private AppBarLayout appBarLayout;

    private List<String> mDatas;
    private DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_bottom_tab);
        ButterKnife.bind(this);
        initView();
        initParams();
        initSetUp();
    }


    private void initView() {
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recycle_view);
        appBarLayout = (AppBarLayout) contentView.findViewById(R.id.app_bar_layout);
    }

    private void initParams() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add(String.valueOf(i));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataAdapter = new DataAdapter(this, mDatas);
        recyclerView.setAdapter(dataAdapter);
    }


    private void initSetUp() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                /**
                 * {@link com.hnpolice.daniel.refreshwithappbarlayout.WithBottomContentView}
                 * verticalOffset=0 为 appBarLayout 完全展开的时候
                 * 完全展开 即可拦截下拉事件 执行 刷新操作
                 */
                contentView.setCanRefresh(verticalOffset == 0);

            }
        });

        contentView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<FrameLayout>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<FrameLayout> refreshView) {
                addData(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<FrameLayout> refreshView) {
                contentView.onPullUpRefreshComplete();
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
                            contentView.setLastUpdatedLabel(formatDateTime(System.currentTimeMillis()));
                            contentView.onPullDownRefreshComplete();
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

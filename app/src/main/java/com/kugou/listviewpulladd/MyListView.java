package com.kugou.listviewpulladd;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MyListView extends ListView implements AbsListView.OnScrollListener {
    private View bottomview;
    //尾文件
    private View headview;
    //头文件
    private int totaItemCounts;
    //用于表示是下拉还是上拉
    private int lassVisible;
    private int firstVisible;
    private LoadListener loadListener;
    private int bottomHeight;
    private int headHeight;
    private int Yload;
    boolean isLoading;
    private TextView headtxt;
    private TextView headtime;
    private ProgressBar progressBar;//加载进度

    public MyListView(Context context) {
        super(context);
        init(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //拿到头布局文件xml
        headview = LinearLayout.inflate(context, R.layout.list_view_fresh_head, null);
        headtxt = (TextView) headview.findViewById(R.id.headtxt);
        headtime = (TextView) headview.findViewById(R.id.timetxt);
        progressBar = (ProgressBar) headview.findViewById(R.id.headprogress);
        headtime.setText("上次更新时间:" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));            //拿到尾布局文件		bottomview=LinearLayout.inflate(context, R.layout.bottom, null);		//测量尾文件高度		bottomview.measure(0,0);		//拿到高度		bottomHeight=bottomview.getMeasuredHeight();		//隐藏view		bottomview.setPadding(0, -bottomHeight, 0, 0);		headview.measure(0, 0);		headHeight=headview.getMeasuredHeight();		headview.setPadding(0,-headHeight, 0, 0);		//添加listview底部		this.addFooterView(bottomview);		//添加到listview头部		this.addHeaderView(headview);		//设置拉动监听		this.setOnScrollListener(this);	}
        //拿到尾布局文件
        bottomview=LinearLayout.inflate(context, R.layout.list_view_fresh_bottom, null);
        bottomview.measure(0,0);
        bottomHeight=bottomview.getMeasuredHeight();
        bottomview.setPadding(0, -bottomHeight, 0, 0);
        headview.measure(0, 0);
        headHeight=headview.getMeasuredHeight();
        headview.setPadding(0,-headHeight, 0, 0);
        this.addFooterView(bottomview);
        this.addHeaderView(headview);
        this.setOnScrollListener(this);

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if(totaItemCounts==lassVisible&&i==SCROLL_STATE_IDLE){
            if(!isLoading){
                isLoading=true;
                bottomview.setPadding(0, 0, 0, 0);
                loadListener.onLoad();
            }
        }
        Log.i("TGA", "firstVisible----"+firstVisible);
        Log.i("TGA", "状态？"+(firstVisible==0));
        if(firstVisible==0&&i==SCROLL_STATE_IDLE){
            headview.setPadding(0, 0, 0, 0);
            headtxt.setText("正在刷新.......");
            progressBar.setVisibility(View.VISIBLE);
            loadListener.PullLoad();
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        this.firstVisible=i;
        this.lassVisible=i+i1;
        this.totaItemCounts=i2;
    }

    //接口回调
    public interface LoadListener{
        void onLoad();
        void PullLoad();
    }

    //加载完成
    public void loadComplete(){
        isLoading=false;
        bottomview.setPadding(0, -bottomHeight, 0, 0);
        headview.setPadding(0, -headHeight, 0, 0);
    }

    public void setInteface(LoadListener loadListener){
        this.loadListener=loadListener;
    }

}

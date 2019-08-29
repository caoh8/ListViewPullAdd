package com.kugou.listviewpulladd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MyListView.LoadListener {
    private MyListView listView;
    private MyAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        for (int i = 1; i < 10; i++) {
            list.add("这是第" + i + "条初始数据");
        }

        adapter = new MyAdapter(listView.getContext(), list);
        listView.setAdapter(adapter);
        listView.setInteface(this);
    }

    @Override
    public void onLoad() {
        // 设置延时三秒获取时局，用于显示加载效果
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里处理请求返回的结果（这里使用模拟数据）
                list.add(0, "这是下拉刷新添加的数据");
                // 更新数据
                adapter.notifyDataSetChanged();
                // 加载完毕
                listView.loadComplete();
            }
        }, 3000);

    }

    @Override
    public void PullLoad() {
// 设置延时三秒获取时局，用于显示加载效果
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // 这里处理请求返回的结果（这里使用模拟数据）
                list.add("这是上拉刷新添加的数据");
                adapter.notifyDataSetChanged();
                listView.loadComplete();
            }
        }, 3000);

    }
}

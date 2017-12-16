package com.renhuikeji.wanlb.wanlibao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renhuikeji.wanlb.wanlibao.bean.KeywordBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 *
 * 搜索界面推荐关键词展示适配器
 */

public class KeySearchAdapter extends BaseAdapter {
    private List<KeywordBean.KeywordsBean> keywordsBeen;
    private Context context;

    public KeySearchAdapter(List<KeywordBean.KeywordsBean> keywordsBeen, Context context) {
        this.keywordsBeen = keywordsBeen;
        this.context = context;
    }

    @Override
    public int getCount() {
        return keywordsBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return keywordsBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = new TextView(context);
        ((TextView)view).setText(keywordsBeen.get(i).getQ());
        view.setPadding(20, 20, 20, 20);
        return view;
    }
}

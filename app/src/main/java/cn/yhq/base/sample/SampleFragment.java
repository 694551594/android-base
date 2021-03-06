package cn.yhq.base.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.yhq.adapter.recycler.SimpleRecyclerListAdapter;
import cn.yhq.adapter.recycler.ViewHolder;
import cn.yhq.base.BaseFragment;

/**
 * Created by Yanghuiqiang on 2016/10/25.
 */

public class SampleFragment extends BaseFragment {
    private RecyclerView mRecyclerView;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = this.getView(view, R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item" + i);
        }
        mRecyclerView.setAdapter(SimpleRecyclerListAdapter.create(getContext(), list, android.R.layout.simple_list_item_1, new SimpleRecyclerListAdapter.IItemViewSetup<String>() {

            @Override
            public void setupView(ViewHolder viewHolder, int position, String entity) {
                viewHolder.setText(android.R.id.text1, entity);
            }
        }));
    }
}

package com.knight.arch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.knight.arch.R;
import com.knight.arch.adapter.HotReposDetailsListAdapterHolder;
import com.knight.arch.data.ReposKV;
import com.knight.arch.model.Repository;
import com.knight.arch.module.ReposDetailsModule;
import com.knight.arch.ui.base.InjectableActivity;
import com.knight.arch.utils.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReposDetailsActivity extends InjectableActivity {
    private Repository mRepository;
    private RecyclerView mRecyclerView;
    private HotReposDetailsListAdapterHolder adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<ReposKV> mReposData = new ArrayList<>();

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_repos_details;
    }

    @Override
    public List<Object> getModules() {

        return Arrays.<Object>asList(new ReposDetailsModule(this));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mRepository = intent.getParcelableExtra("repos_data");
        L.json(JSON.toJSONString(mRepository));

        if (mRepository != null) {
            initData();
        }

        initView();

    }

    private void initData() {
        mReposData.add(new ReposKV("name", mRepository.getName()));
        mReposData.add(new ReposKV("full_name", mRepository.getFull_name()));
        mReposData.add(new ReposKV("description", mRepository.getDescription()));
        mReposData.add(new ReposKV("create_at", mRepository.getCreated_at()));
        mReposData.add(new ReposKV("update_at", mRepository.getUpdated_at()));
        mReposData.add(new ReposKV("stars", String.valueOf(mRepository.getStargazers_count())));
        mReposData.add(new ReposKV("language", String.valueOf(mRepository.getLanguage())));
    }

    private void initView() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mRepository.getName());


        adapter = new HotReposDetailsListAdapterHolder(this, mReposData);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_repos_details_recycler_view);
        mRecyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

    }
}
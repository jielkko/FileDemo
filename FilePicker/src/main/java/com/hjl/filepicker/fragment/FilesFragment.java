package com.hjl.filepicker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.filepicker.FilePicker;
import com.hjl.filepicker.R;
import com.hjl.filepicker.adapter.FilesAdapter;
import com.hjl.filepicker.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * 文件
 */

public class FilesFragment extends Fragment {
    private static final String TAG = "文件列表";

    private Context mContext;

    public FilesAdapter mAdapter;

    private LinearLayout mContainer;
    private RecyclerView mRecyclerView;
    private TextView mHint;


    private String mListType = "";

    public static FilesFragment indexInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        FilesFragment pageFragment = new FilesFragment();
        pageFragment.setArguments(args);

        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files_list, container, false);

        //初始化
        mListType = getArguments().getString("type");

        mContext = getActivity();

        initFindId(view);
        initListView();

        return view;
    }

    private void initFindId(View view) {

        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mHint = (TextView) view.findViewById(R.id.hint);
    }


    private void initListView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);

        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mAdapter = new FilesAdapter(mContext, mListType);

        mRecyclerView.setAdapter(mAdapter);

        //选择
        mAdapter.setOnItemSelectClickListener(new FilesAdapter.OnItemSelectClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EventBus.getDefault().post(new MessageEvent(1, ""));


            }
        });


        if (FilePicker.getInstance().getList(mListType).size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mHint.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mHint.setVisibility(View.VISIBLE);
        }

    }


}

package com.hjl.filepicker.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.filepicker.FilePicker;
import com.hjl.filepicker.R;
import com.hjl.filepicker.adapter.FilesAdapter;
import com.hjl.filepicker.bean.FileItem;
import com.hjl.filepicker.bean.MessageEvent;
import com.hjl.filepicker.utils.DataUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * 文件
 */

public class FilesFragment extends Fragment {
    private static final String TAG = "文件列表";

    private Context mContext;

    private FilesAdapter mAdapter;
    List<FileItem> mData = new ArrayList<>();

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

        RefreshDate();

        return view;
    }

    private void initFindId(View view) {

        mContainer = (LinearLayout) view.findViewById(R.id.container);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mHint = (TextView) view.findViewById(R.id.hint);
    }


    private void initListView() {


//        mData = ListNameSort(FilePicker.getInstance().getList(mListType), 1);
//        mData = ListNameSort(FilePicker.getInstance().getList(mListType), -1);
//        mData = ListTimeSort(FilePicker.getInstance().getList(mListType), 1);
//        mData = ListTimeSort(FilePicker.getInstance().getList(mListType), -1);
//        mData = ListSizeSort(FilePicker.getInstance().getList(mListType), 1);
//        mData = ListSizeSort(FilePicker.getInstance().getList(mListType), -1);


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new FilesAdapter(mContext, mListType, mData);
        mRecyclerView.setAdapter(mAdapter);

        //选择
        mAdapter.setOnItemSelectClickListener(new FilesAdapter.OnItemSelectClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EventBus.getDefault().post(new MessageEvent(1, ""));


            }
        });


    }

    private int ListSort = 0;

    //刷新数据
    public void RefreshDate(int ListSort) {
        this.ListSort = ListSort;
        RefreshDate();
    }

    //刷新数据
    public void RefreshDate() {

    /*    switch (ListSort) {
            case 0:
                mData = ListNameSort(FilePicker.getInstance().getList(mListType), 1);
                break;
            case 1:
                mData = ListNameSort(FilePicker.getInstance().getList(mListType), -1);
                break;
            case 2:
                mData = ListTimeSort(FilePicker.getInstance().getList(mListType), 1);
                break;
            case 3:
                mData = ListTimeSort(FilePicker.getInstance().getList(mListType), -1);
                break;
            case 4:
                mData = ListSizeSort(FilePicker.getInstance().getList(mListType), 1);
                break;
            case 5:
                mData = ListSizeSort(FilePicker.getInstance().getList(mListType), -1);
                break;
            case 6:
                break;
        }*/
        mData = ListNameSort(FilePicker.getInstance().getList(mListType), 1);
        if (mData.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mHint.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mHint.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 根据名称排序（其他排序如根据id排序也类似）
     *
     * @param list
     */
    private List<FileItem> ListNameSort(List<FileItem> list, int sort) {
        //用Collections这个工具类传list进来排序

        //1 正序  大 - > 小
        //-1 正序   小 - > 大
        if (sort == 1) {
            Collections.sort(list, new Comparator<FileItem>() {
                @Override
                public int compare(FileItem o1, FileItem o2) {
                    try {
                        /**
                          *create by Davide
                          *开始比较-我这儿按照apk的名称排序，便获取apkName
                         */
                        Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
                        if (cmp.compare(o1.getName(), o2.getName()) > 0) {
                            return 1;
                        } else if (cmp.compare(o1.getName(), o2.getName()) < 0) {
                            return -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        } else {
            Collections.sort(list, new Comparator<FileItem>() {
                @Override
                public int compare(FileItem o1, FileItem o2) {
                    try {
                        /**
                          *create by Davide
                          *开始比较-我这儿按照apk的名称排序，便获取apkName
                         */
                        Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
                        if (cmp.compare(o1.getName(), o2.getName()) < 0) {
                            return 1;
                        } else if (cmp.compare(o1.getName(), o2.getName()) > 0) {
                            return -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        }

        return list;
    }

    /**
     * 根据时间排序（其他排序如根据id排序也类似）
     *
     * @param list
     */
    private List<FileItem> ListTimeSort(List<FileItem> list, int sort) {
        //用Collections这个工具类传list进来排序
        //1 正序  大 - > 小
        //-1 正序   小 - > 大
        if (sort == 1) {
            Collections.sort(list, new Comparator<FileItem>() {
                @Override
                public int compare(FileItem o1, FileItem o2) {
                    try {
                        if (o1.getAddTime() < o2.getAddTime()) {
                            return 1;//大的放前面
                        } else {
                            return -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        } else {
            Collections.sort(list, new Comparator<FileItem>() {
                @Override
                public int compare(FileItem o1, FileItem o2) {
                    try {
                        if (o1.getAddTime() > o2.getAddTime()) {
                            return 1;//小的放前面
                        } else {
                            return -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        }

        return list;
    }


    /**
     * 根据文件大小排序（其他排序如根据id排序也类似）
     *
     * @param list sort
     */
    private List<FileItem> ListSizeSort(List<FileItem> list, int sort) {
        //用Collections这个工具类传list进来排序
        //1 正序  大 - > 小
        //-1 正序   小 - > 大
        if (sort == 1) {
            Collections.sort(list, new Comparator<FileItem>() {
                @Override
                public int compare(FileItem o1, FileItem o2) {
                    try {
                        if (o1.getSize() < o2.getSize()) {
                            return 1;//大的放前面
                        } else {
                            return -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        } else {
            Collections.sort(list, new Comparator<FileItem>() {
                @Override
                public int compare(FileItem o1, FileItem o2) {
                    try {
                        if (o1.getSize() > o2.getSize()) {
                            return 1;//小的放前面
                        } else {
                            return -1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
        }

        return list;
    }


}

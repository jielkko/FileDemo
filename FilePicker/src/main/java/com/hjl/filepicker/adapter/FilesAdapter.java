package com.hjl.filepicker.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjl.filepicker.FilePicker;
import com.hjl.filepicker.R;
import com.hjl.filepicker.bean.FileItem;
import com.hjl.filepicker.utils.DataUtil;
import com.hjl.filepicker.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;


public class FilesAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TIME = 0;
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    private List<FileItem> mData = new ArrayList<>();
    private String mListType = "";


    public FilesAdapter(Context context,String mListType) {
        this.mContext = context;
        this.mListType = mListType;
        this.mData = FilePicker.getInstance().getList(mListType);
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_TIME;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_files, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_TIME) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_files, parent,
                    false);
            return new ItemViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        FileItem item = mData.get(position);
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mName.setText(item.name);
            itemViewHolder.mIcon.setImageResource(FileUtil.getFileIcon(item.name));
            itemViewHolder.mTime.setText(DataUtil.timeStampToDateString(item.addTime));

           /* itemViewHolder.mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        int position = itemViewHolder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(itemViewHolder.mContainer, position);
                    }
                }
            });*/
            Boolean isSelected = false;
            for(FileItem mSelected :  FilePicker.getInstance().mSelectedFiles){
                if(mSelected.path.equals(item.path)){
                    isSelected = true;
                }

            }
            if (isSelected) {
                //已选中
                itemViewHolder.mIsChooseIcon.setImageDrawable(itemViewHolder.mIcCheckBoxChecked);

            } else {

                //未选中
                itemViewHolder.mIsChooseIcon.setImageDrawable(itemViewHolder.mIcCheckBoxNormal);
            }

            itemViewHolder.mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = itemViewHolder.getLayoutPosition();

                    Boolean isSelected = false;
                    for(FileItem mSelected :  FilePicker.getInstance().mSelectedFiles){
                        if(mSelected.path.equals(mData.get(layoutPosition).path)){
                            isSelected = true;
                        }

                    }
                    //Toast.makeText(mContext, "点击" + layoutPosition, Toast.LENGTH_SHORT).show();
                    if (isSelected) {
                        //已选中
                        FilePicker.getInstance().mSelectedFiles.remove(mData.get(layoutPosition));
                        mData.get(layoutPosition).isSelected = 0;
                        FilePicker.getInstance().setSelected(mListType,layoutPosition,0);
                    } else {
                        //未选中
                        if (FilePicker.getInstance().mSelectedFiles.size() < FilePicker.getInstance().selectLimit) {
                            FilePicker.getInstance().mSelectedFiles.add(mData.get(layoutPosition));
                            mData.get(layoutPosition).isSelected = 1;
                            FilePicker.getInstance().setSelected(mListType,layoutPosition,1);
                        }
                    }
                    notifyItemChanged(layoutPosition);


                    if (mOnItemSelectClickListener != null) {
                        mOnItemSelectClickListener.onItemClick(v, layoutPosition);
                    }
                }
            });
        }

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mContainer;
        private LinearLayout mIsChooseBtn;
        private ImageView mIsChooseIcon;
        private ImageView mIcon;
        private TextView mName;
        private TextView mTime;




        //图标素材
        private Drawable mIcCheckBoxChecked;
        private Drawable mIcCheckBoxNormal;
        public ItemViewHolder(View view) {
            super(view);
            mContainer = (LinearLayout) view.findViewById(R.id.container);
            mIsChooseBtn = (LinearLayout) view.findViewById(R.id.isChoose_btn);
            mIsChooseIcon = (ImageView) view.findViewById(R.id.isChoose_icon);
            mIcon = (ImageView) view.findViewById(R.id.icon);
            mName = (TextView) view.findViewById(R.id.name);
            mTime = (TextView) view.findViewById(R.id.time);


            Resources resources = view.getResources();
            mIcCheckBoxChecked = resources.getDrawable(R.drawable.file_check_blue1);
            mIcCheckBoxNormal = resources.getDrawable(R.drawable.file_check_blue0);
        }
    }


    //点击
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //长按
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    //点击选择框
    private OnItemSelectClickListener mOnItemSelectClickListener;

    public interface OnItemSelectClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemSelectClickListener(OnItemSelectClickListener mOnItemSelectClickListener) {
        this.mOnItemSelectClickListener = mOnItemSelectClickListener;
    }
}
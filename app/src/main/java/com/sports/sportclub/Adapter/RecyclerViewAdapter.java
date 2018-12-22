package com.sports.sportclub.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import edu.bjtu.example.sportsdashboard.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private List<Map<String, Object>> videoList;
    private int rowLayout;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(List<Map<String, Object>> videoList, int rowLayout, Context context) {
        this.videoList = videoList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        switch (position) {
            case 0:
                holder.video_name.setText("Video1");
                holder.video_intro.setText("Swimming");
                holder.click_ratio.setText("23");
                holder.video_photo.setImageResource(R.drawable.trainer6);
                break;
//            case 1:
//                holder.video_photo.setImageResource(R.drawable.trainer5);
//                holder.video_name.setText("video2");
//                holder.video_intro.setText("Basketball");
//                holder.click_ratio.setText("4");
//                break;
//            case 2:
//                holder.video_photo.setImageResource(R.drawable.trainer4);
//                holder.video_name.setText("video3");
//                holder.video_intro.setText("Football");
//                holder.click_ratio.setText("7");
//                break;
//            case 3:
//                holder.video_photo.setImageResource(R.drawable.trainer3);
//                holder.video_name.setText("video4");
//                holder.video_intro.setText("Table Tennis");
//                holder.click_ratio.setText("23");
//                break;
//            case 4:
//                holder.video_photo.setImageResource(R.drawable.trainer2);
//                holder.video_name.setText("video5");
//                holder.video_intro.setText("Skating");
//                holder.click_ratio.setText("9");
//                break;
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return videoList == null ? 0 : videoList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    TextView video_name;
    TextView video_intro;
    TextView click_ratio;
    ImageView video_photo;

    ViewHolder(View itemView) {
        super(itemView);
        video_name = (TextView) itemView.findViewById(R.id.video_name);
        video_intro = (TextView) itemView.findViewById(R.id.video_intro);
        click_ratio = (TextView) itemView.findViewById(R.id.click_ratio);
        video_photo = (ImageView) itemView.findViewById(R.id.video_photo);
    }
}

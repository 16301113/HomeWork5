package edu.bjtu.example.sportsdashboard.UI.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sports.sportclub.Adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.bjtu.example.sportsdashboard.R;
import edu.bjtu.example.sportsdashboard.VideoPlayingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoPlayFragment extends Fragment {

    View view;

    private RecyclerView videoList2;

    private RecyclerViewAdapter myAdapter;

    public VideoPlayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //  使用RecyclerView作为VideoList
        view = inflater.inflate(R.layout.fragment_video_play2, container, false);

        //  使用RecyclerView作为VideoList
        videoList2 = (RecyclerView) view.findViewById(R.id.recycler_view);
        videoList2.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        videoList2.setItemAnimator(new DefaultItemAnimator());

        myAdapter = new RecyclerViewAdapter(DataList(), R.layout.video_item, this.getActivity());
        videoList2.setAdapter(myAdapter);

        return view;


    }

    @Override
    public void onStart() {
        super.onStart();

        myAdapter.setmOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(VideoPlayFragment.this.getActivity(), VideoPlayingActivity.class);
                startActivity(intent);
                int click_times = 0;
                click_times++;
            }

            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(VideoPlayFragment.this.getActivity(), "您长按点击了" + position + "行", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<Map<String, Object>> DataList() {
        List<Map<String, Object>> videoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        for (int i = 0; i < 5; i++) {
            videoList.add(map);
        }

        return videoList;
    }
}

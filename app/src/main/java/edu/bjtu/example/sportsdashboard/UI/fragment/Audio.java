package edu.bjtu.example.sportsdashboard.UI.fragment;

import java.io.Serializable;

public class Audio implements Serializable {

    private String data;
    private String name;
    private String intro;
    private int click_ratio;

    public Audio(String name, String intro, int click_ratio, String data) {
        this.click_ratio = click_ratio;
        this.name = name;
        this.intro = intro;
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIntro() {
        return intro;
    }

    public void setClick_ratio(int times) {
        this.click_ratio = times;
    }

    public int getClick_ratio() {
        return click_ratio;
    }


}

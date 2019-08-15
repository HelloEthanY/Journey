package com.journey.org.ui.custom.ijkplayer.media;

public interface IProgressChangeListener {

    void onStartTrackingTouch();

    void onProgressChanged(String s, long newposition);

    void onStopTrackingTouch();

}

package com.brainsales.gameport.utils;

/**
 * Created by user on 2017-07-04.
 */

public class NewsSetting {

    private String Thumbnail_Images;

    public NewsSetting() {}

    public NewsSetting(String Thumbnail_Images) {

        this.Thumbnail_Images = Thumbnail_Images;
    }

    public String getImage() {
        return Thumbnail_Images;
    }

    public void setImage(String image) {
        this.Thumbnail_Images = Thumbnail_Images;
    }

}

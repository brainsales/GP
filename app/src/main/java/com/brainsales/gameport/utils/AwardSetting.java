package com.brainsales.gameport.utils;

/**
 * Created by Ryu on 2017-06-28.
 */

public class AwardSetting {

    private String Description;
    private String Thumbnail_Images;
    private String Card_Image;

    public AwardSetting() {}

    public AwardSetting(String description, String Thumbnail_Images, String Card_Image) {
        Description = description;
        this.Thumbnail_Images = Thumbnail_Images;
        this.Card_Image = Card_Image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getThumbnail_Images() {
        return Thumbnail_Images;
    }

    public void setThumbnail_Images(String Thumbnail_Images) {
        this.Thumbnail_Images = Thumbnail_Images;
    }

    public String getCard_Image() {
        return Card_Image;
    }

    public void setCard_Image(String Card_Image) {
        this.Card_Image = Card_Image;
    }

}

package com.brainsales.gameport.utils;

/**
 * Created by Ryu on 2017-06-28.
 */

public class AwardSetting {

    private String Description;
    private String image;

    public AwardSetting() {}

    public AwardSetting(String description, String image) {
        Description = description;
        this.image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

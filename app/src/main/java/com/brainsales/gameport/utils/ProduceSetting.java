package com.brainsales.gameport.utils;

/**
 * Created by Ryu on 2017-06-06.
 */

public class ProduceSetting {

    private String GameName;
    private String GameType;
    private String image;

    public ProduceSetting(){}

    public ProduceSetting(String gameName, String gameType, String image) {
        GameName = gameName;
        GameType = gameType;
        this.image = image;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public String getGameType() {
        return GameType;
    }

    public void setGameType(String gameType) {
        GameType = gameType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

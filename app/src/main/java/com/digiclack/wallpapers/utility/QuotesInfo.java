package com.digiclack.wallpapers.utility;

public class QuotesInfo {
    private int CATEGORY_ID;
    private int QUOTES_ID;
    private String QUOTES_LIKED;
    private String QUOTES_TEXT;
    private String QUOTES_UTP;

    public int getQUOTES_ID() {
        return this.QUOTES_ID;
    }

    public void setQUOTES_ID(int QUOTES_ID) {
        this.QUOTES_ID = QUOTES_ID;
    }

    public int getCATEGORY_ID() {
        return this.CATEGORY_ID;
    }

    public void setCATEGORY_ID(int CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }

    public String getQUOTES_TEXT() {
        return this.QUOTES_TEXT;
    }

    public void setQUOTES_TEXT(String QUOTES_TEXT) {
        this.QUOTES_TEXT = QUOTES_TEXT;
    }

    public String getQUOTES_LIKED() {
        return this.QUOTES_LIKED;
    }

    public void setQUOTES_LIKED(String QUOTES_LIKED) {
        this.QUOTES_LIKED = QUOTES_LIKED;
    }

    public String getQUOTES_UTP() {
        return this.QUOTES_UTP;
    }

    public void setQUOTES_UTP(String QUOTES_UTP) {
        this.QUOTES_UTP = QUOTES_UTP;
    }
}

package com.digiclack.wallpapers.utility;

public class CategoryInfo {
    private int CATEGORY_DISPLAY_SEQ;
    private String CATEGORY_DRAWABLE;
    private int CATEGORY_ID;
    private String CATEGORY_NAME;
    private String CATEGORY_STATUS;

    public int getCATEGORY_ID() {
        return this.CATEGORY_ID;
    }

    public void setCATEGORY_ID(int CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }

    public String getCATEGORY_NAME() {
        return this.CATEGORY_NAME;
    }

    public void setCATEGORY_NAME(String CATEGORY_NAME) {
        this.CATEGORY_NAME = CATEGORY_NAME;
    }

    public String getCATEGORY_STATUS() {
        return this.CATEGORY_STATUS;
    }

    public void setCATEGORY_STATUS(String CATEGORY_STATUS) {
        this.CATEGORY_STATUS = CATEGORY_STATUS;
    }

    public String getCATEGORY_DRAWABLE() {
        return this.CATEGORY_DRAWABLE;
    }

    public void setCATEGORY_DRAWABLE(String CATEGORY_DRAWABLE) {
        this.CATEGORY_DRAWABLE = CATEGORY_DRAWABLE;
    }

    public int getCATEGORY_DISPLAY_SEQ() {
        return this.CATEGORY_DISPLAY_SEQ;
    }

    public void setCATEGORY_DISPLAY_SEQ(int CATEGRY_DISPLAY_SEQ) {
        this.CATEGORY_DISPLAY_SEQ = CATEGRY_DISPLAY_SEQ;
    }
}

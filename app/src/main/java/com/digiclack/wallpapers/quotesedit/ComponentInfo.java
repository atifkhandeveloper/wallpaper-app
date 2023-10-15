package com.digiclack.wallpapers.quotesedit;

import yuku.ambilwarna.BuildConfig;

public class ComponentInfo {
    private int COMP_ID;
    private int HEIGHT;
    private int HUE;
    private int LEFT;
    private int OPACITY;
    private int ORDER;
    private float POS_X;
    private float POS_Y;
    private String RES_ID;
    private float ROTATION;
    private int TEMPLATE_ID;
    private int TOP;
    private int TOP_ID;
    private String TYPE = BuildConfig.FLAVOR;
    private int WIDTH;
    private float Y_ROTATION;

    public int getTOP() {
        return this.TOP;
    }

    public void setTOP(int TOP) {
        this.TOP = TOP;
    }

    public int getLEFT() {
        return this.LEFT;
    }

    public void setLEFT(int LEFT) {
        this.LEFT = LEFT;
    }
    public ComponentInfo() {

    }
    public ComponentInfo(int TEMPLATE_ID, float POS_X, float POS_Y, int WIDTH, int HEIGHT, float ROTATION, float Y_ROTATION, String RES_ID, String TYPE, int ORDER, int TOP_ID, int TOP, int LEFT) {
        this.TEMPLATE_ID = TEMPLATE_ID;
        this.POS_X = POS_X;
        this.POS_Y = POS_Y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.ROTATION = ROTATION;
        this.Y_ROTATION = Y_ROTATION;
        this.RES_ID = RES_ID;
        this.TYPE = TYPE;
        this.ORDER = ORDER;
        this.TOP_ID = TOP_ID;
        this.TOP = TOP;
        this.LEFT = LEFT;
    }

    public int getCOMP_ID() {
        return this.COMP_ID;
    }

    public void setCOMP_ID(int COMP_ID) {
        this.COMP_ID = COMP_ID;
    }

    public int getTEMPLATE_ID() {
        return this.TEMPLATE_ID;
    }

    public void setTEMPLATE_ID(int TEMPLATE_ID) {
        this.TEMPLATE_ID = TEMPLATE_ID;
    }

    public float getPOS_X() {
        return this.POS_X;
    }

    public void setPOS_X(float POS_X) {
        this.POS_X = POS_X;
    }

    public float getPOS_Y() {
        return this.POS_Y;
    }

    public void setPOS_Y(float POS_Y) {
        this.POS_Y = POS_Y;
    }

    public String getRES_ID() {
        return this.RES_ID;
    }

    public void setRES_ID(String RES_ID) {
        this.RES_ID = RES_ID;
    }

    public String getTYPE() {
        return this.TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public int getORDER() {
        return this.ORDER;
    }

    public void setORDER(int ORDER) {
        this.ORDER = ORDER;
    }

    public int getTOP_ID() {
        return this.TOP_ID;
    }

    public void setTOP_ID(int TOP_ID) {
        this.TOP_ID = TOP_ID;
    }

    public float getROTATION() {
        return this.ROTATION;
    }

    public void setROTATION(float ROTATION) {
        this.ROTATION = ROTATION;
    }

    public int getWIDTH() {
        return this.WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getHEIGHT() {
        return this.HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public float getY_ROTATION() {
        return this.Y_ROTATION;
    }

    public void setY_ROTATION(float y_ROTATION) {
        this.Y_ROTATION = y_ROTATION;
    }

    public int getHUE() {
        return this.HUE;
    }

    public void setHUE(int HUE) {
        this.HUE = HUE;
    }

    public int getOPACITY() {
        return this.OPACITY;
    }

    public void setOPACITY(int OPACITY) {
        this.OPACITY = OPACITY;
    }
}

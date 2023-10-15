package com.digiclack.wallpapers.crop;

import android.graphics.Rect;
import android.view.View;

public enum Edge {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM;
    
    public static final int MIN_CROP_LENGTH_PX = 40;
    private float mCoordinate;

    static  class AnonymousClass1 {
        static  int[] $SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge = null;

        static {
            $SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge = new int[Edge.values().length];
            try {
                $SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[Edge.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[Edge.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[Edge.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[Edge.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void setCoordinate(float coordinate) {
        this.mCoordinate = coordinate;
    }

    public void offset(float distance) {
        this.mCoordinate += distance;
    }

    public float getCoordinate() {
        return this.mCoordinate;
    }

    public void adjustCoordinate(float x, float y, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        switch (AnonymousClass1.$SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[ordinal()]) {
            case 1 :
                this.mCoordinate = adjustLeft(x, imageRect, imageSnapRadius, aspectRatio);
                return;
            case 2 :
                this.mCoordinate = adjustTop(y, imageRect, imageSnapRadius, aspectRatio);
                return;
            case 3 :
                this.mCoordinate = adjustRight(x, imageRect, imageSnapRadius, aspectRatio);
                return;
            case 4 :
                this.mCoordinate = adjustBottom(y, imageRect, imageSnapRadius, aspectRatio);
                return;
            default:
                return;
        }
    }

    public void adjustCoordinate(float aspectRatio) {
        float left = LEFT.getCoordinate();
        float top = TOP.getCoordinate();
        float right = RIGHT.getCoordinate();
        float bottom = BOTTOM.getCoordinate();
        switch (AnonymousClass1.$SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[ordinal()]) {
            case 1 :
                this.mCoordinate = AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio);
                return;
            case 2 :
                this.mCoordinate = AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio);
                return;
            case 3:
                this.mCoordinate = AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio);
                return;
            case 4 :
                this.mCoordinate = AspectRatioUtil.calculateBottom(left, top, right, aspectRatio);
                return;
            default:
                return;
        }
    }

    public boolean isNewRectangleOutOfBounds(Edge edge, Rect imageRect, float aspectRatio) {
        float offset = edge.snapOffset(imageRect);
        float top;
        float bottom;
        float right;
        float left;
        switch (AnonymousClass1.$SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[ordinal()]) {
            case 1 :
                if (edge.equals(TOP)) {
                    top = (float) imageRect.top;
                    bottom = BOTTOM.getCoordinate() - offset;
                    right = RIGHT.getCoordinate();
                    return isOutOfBounds(top, AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio), bottom, right, imageRect);
                } else if (edge.equals(BOTTOM)) {
                    bottom = (float) imageRect.bottom;
                    top = TOP.getCoordinate() - offset;
                    right = RIGHT.getCoordinate();
                    return isOutOfBounds(top, AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio), bottom, right, imageRect);
                }
                break;
            case 2 :
                if (edge.equals(LEFT)) {
                    left = (float) imageRect.left;
                    right = RIGHT.getCoordinate() - offset;
                    bottom = BOTTOM.getCoordinate();
                    return isOutOfBounds(AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio), left, bottom, right, imageRect);
                } else if (edge.equals(RIGHT)) {
                    right = (float) imageRect.right;
                    left = LEFT.getCoordinate() - offset;
                    bottom = BOTTOM.getCoordinate();
                    return isOutOfBounds(AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio), left, bottom, right, imageRect);
                }
                break;
            case 3 :
                if (edge.equals(TOP)) {
                    top = (float) imageRect.top;
                    bottom = BOTTOM.getCoordinate() - offset;
                    left = LEFT.getCoordinate();
                    return isOutOfBounds(top, left, bottom, AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio), imageRect);
                } else if (edge.equals(BOTTOM)) {
                    bottom = (float) imageRect.bottom;
                    top = TOP.getCoordinate() - offset;
                    left = LEFT.getCoordinate();
                    return isOutOfBounds(top, left, bottom, AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio), imageRect);
                }
                break;
            case 4 :
                if (edge.equals(LEFT)) {
                    left = (float) imageRect.left;
                    right = RIGHT.getCoordinate() - offset;
                    top = TOP.getCoordinate();
                    return isOutOfBounds(top, left, AspectRatioUtil.calculateBottom(left, top, right, aspectRatio), right, imageRect);
                } else if (edge.equals(RIGHT)) {
                    right = (float) imageRect.right;
                    left = LEFT.getCoordinate() - offset;
                    top = TOP.getCoordinate();
                    return isOutOfBounds(top, left, AspectRatioUtil.calculateBottom(left, top, right, aspectRatio), right, imageRect);
                }
                break;
        }
        return true;
    }

    private boolean isOutOfBounds(float top, float left, float bottom, float right, Rect imageRect) {
        return top < ((float) imageRect.top) || left < ((float) imageRect.left) || bottom > ((float) imageRect.bottom) || right > ((float) imageRect.right);
    }

    public float snapToRect(Rect imageRect) {
        float oldCoordinate = this.mCoordinate;
        switch (AnonymousClass1.$SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[ordinal()]) {
            case 1:
                this.mCoordinate = (float) imageRect.left;
                break;
            case 2 :
                this.mCoordinate = (float) imageRect.top;
                break;
            case 3 :
                this.mCoordinate = (float) imageRect.right;
                break;
            case 4 :
                this.mCoordinate = (float) imageRect.bottom;
                break;
        }
        return this.mCoordinate - oldCoordinate;
    }

    public float snapOffset(Rect imageRect) {
        float oldCoordinate = this.mCoordinate;
        float newCoordinate = oldCoordinate;
        switch (AnonymousClass1.$SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[ordinal()]) {
            case 1 :
                newCoordinate = (float) imageRect.left;
                break;
            case 2 :
                newCoordinate = (float) imageRect.top;
                break;
            case 3 :
                newCoordinate = (float) imageRect.right;
                break;
            case 4 :
                newCoordinate = (float) imageRect.bottom;
                break;
        }
        return newCoordinate - oldCoordinate;
    }

    public void snapToView(View view) {
        switch (AnonymousClass1.$SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[ordinal()]) {
            case 1 :
                this.mCoordinate = 0.0f;
                return;
            case 2 :
                this.mCoordinate = 0.0f;
                return;
            case 3 :
                this.mCoordinate = (float) view.getWidth();
                return;
            case 4 :
                this.mCoordinate = (float) view.getHeight();
                return;
            default:
                return;
        }
    }

    public static float getWidth() {
        return RIGHT.getCoordinate() - LEFT.getCoordinate();
    }

    public static float getHeight() {
        return BOTTOM.getCoordinate() - TOP.getCoordinate();
    }

    public boolean isOutsideMargin(Rect rect, float margin) {
        boolean result = false;
        switch (AnonymousClass1.$SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[ordinal()]) {
            case 1 :
                if (this.mCoordinate - ((float) rect.left) < margin) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case 2 :
                if (this.mCoordinate - ((float) rect.top) < margin) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case 3 :
                if (((float) rect.right) - this.mCoordinate < margin) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case 4 :
                result = ((float) rect.bottom) - this.mCoordinate < margin;
                break;
        }
        return result;
    }

    public boolean isOutsideFrame(Rect rect) {
        boolean result = false;
        switch (AnonymousClass1.$SwitchMap$com$SimplyEntertaining$coolquotes$crop$Edge[ordinal()]) {
            case 1 :
                if (((double) (this.mCoordinate - ((float) rect.left))) < 0.0d) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case 2 :
                if (((double) (this.mCoordinate - ((float) rect.top))) < 0.0d) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case 3 :
                if (((double) (((float) rect.right) - this.mCoordinate)) < 0.0d) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case 4 :
                result = ((double) (((float) rect.bottom) - this.mCoordinate)) < 0.0d;
                break;
        }
        return result;
    }

    private static float adjustLeft(float x, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultX = x;
        if (x - ((float) imageRect.left) < imageSnapRadius) {
            return (float) imageRect.left;
        }
        float resultXHoriz = Float.POSITIVE_INFINITY;
        float resultXVert = Float.POSITIVE_INFINITY;
        if (x >= RIGHT.getCoordinate() - 40.0f) {
            resultXHoriz = RIGHT.getCoordinate() - 40.0f;
        }
        if ((RIGHT.getCoordinate() - x) / aspectRatio <= 40.0f) {
            resultXVert = RIGHT.getCoordinate() - (40.0f * aspectRatio);
        }
        return Math.min(resultX, Math.min(resultXHoriz, resultXVert));
    }

    private static float adjustRight(float x, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultX = x;
        if (((float) imageRect.right) - x < imageSnapRadius) {
            return (float) imageRect.right;
        }
        float resultXHoriz = Float.NEGATIVE_INFINITY;
        float resultXVert = Float.NEGATIVE_INFINITY;
        if (x <= LEFT.getCoordinate() + 40.0f) {
            resultXHoriz = LEFT.getCoordinate() + 40.0f;
        }
        if ((x - LEFT.getCoordinate()) / aspectRatio <= 40.0f) {
            resultXVert = LEFT.getCoordinate() + (40.0f * aspectRatio);
        }
        return Math.max(resultX, Math.max(resultXHoriz, resultXVert));
    }

    private static float adjustTop(float y, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultY = y;
        if (y - ((float) imageRect.top) < imageSnapRadius) {
            return (float) imageRect.top;
        }
        float resultYVert = Float.POSITIVE_INFINITY;
        float resultYHoriz = Float.POSITIVE_INFINITY;
        if (y >= BOTTOM.getCoordinate() - 40.0f) {
            resultYHoriz = BOTTOM.getCoordinate() - 40.0f;
        }
        if ((BOTTOM.getCoordinate() - y) * aspectRatio <= 40.0f) {
            resultYVert = BOTTOM.getCoordinate() - (40.0f / aspectRatio);
        }
        return Math.min(resultY, Math.min(resultYHoriz, resultYVert));
    }

    private static float adjustBottom(float y, Rect imageRect, float imageSnapRadius, float aspectRatio) {
        float resultY = y;
        if (((float) imageRect.bottom) - y < imageSnapRadius) {
            return (float) imageRect.bottom;
        }
        float resultYVert = Float.NEGATIVE_INFINITY;
        float resultYHoriz = Float.NEGATIVE_INFINITY;
        if (y <= TOP.getCoordinate() + 40.0f) {
            resultYVert = TOP.getCoordinate() + 40.0f;
        }
        if ((y - TOP.getCoordinate()) * aspectRatio <= 40.0f) {
            resultYHoriz = TOP.getCoordinate() + (40.0f / aspectRatio);
        }
        return Math.max(resultY, Math.max(resultYHoriz, resultYVert));
    }
}

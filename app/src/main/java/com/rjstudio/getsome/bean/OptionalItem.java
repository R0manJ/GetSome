package com.rjstudio.getsome.bean;

/**
 * Created by r0man on 2017/8/19.
 */

public class OptionalItem {
    private int imageId;
    private int textId;
    private boolean isSelected = false;


    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }
}

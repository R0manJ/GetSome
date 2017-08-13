package com.rjstudio.getsome.bean;

/**
 * Created by r0man on 2017/8/11.
 */

public class ConsumeItem {
    int ConsumeType;
    int TypeIcon;
    String ConsumeName;
    float Amount;
    long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getConsumeType() {
        return ConsumeType;
    }

    public void setConsumeType(int consumeType) {
        ConsumeType = consumeType;
    }

    public int getTypeIcon() {
        return TypeIcon;
    }

    public void setTypeIcon(int typeIcon) {
        TypeIcon = typeIcon;
    }

    public String getConsumeName() {
        return ConsumeName;
    }

    public void setConsumeName(String consumeName) {
        ConsumeName = consumeName;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }
}

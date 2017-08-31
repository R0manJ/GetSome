package com.rjstudio.getsome.bean;

import java.io.Serializable;

/**
 * Created by r0man on 2017/8/11.
 */

public class ConsumeItem implements Serializable{

    int ConsumeType;
    int TypeIcon;
    String ConsumeName;
    double Amount;
    long date;
    String remark;
    int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }
}

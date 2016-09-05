package com.maxifly.fb2_illustrator.GUI;

/**
 * Created by Maxim.Pantuhin on 02.09.2016.
 */
public class IllChangeOrder {
    String moveIllNum;    // Номер иллюстрации, сменившей порядок
    String beforeIllNum;  // Номер иллюстрации, перед которой надо поставить свою

    public IllChangeOrder(String moveIllNum, String beforeIllNum) {
        this.moveIllNum = moveIllNum;
        this.beforeIllNum = beforeIllNum;
    }

    public String getMoveIllNum() {
        return moveIllNum;
    }

    public String getBeforeIllNum() {
        return beforeIllNum;
    }
}

package com.maxifly.fb2_illustrator.GUI;

/**
 * Created by Maxim.Pantuhin on 02.09.2016.
 */
public class IllChangeOrder {
    Integer moveIllNum;    // Номер иллюстрации, сменившей порядок
    Integer beforeIllNum;  // Номер иллюстрации, перед которой надо поставить свою

    public IllChangeOrder(Integer moveIllNum, Integer beforeIllNum) {
        this.moveIllNum = moveIllNum;
        this.beforeIllNum = beforeIllNum;
    }

    public Integer getMoveIllNum() {
        return moveIllNum;
    }
    public Integer getBeforeIllNum() {
        return beforeIllNum;
    }
}

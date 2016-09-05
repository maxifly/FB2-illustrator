package com.maxifly.fb2_illustrator.GUI.DomainModel;

import javafx.scene.Node;

/**
 * Created by Maxim.Pantuhin on 05.09.2016.
 */
public class ILL_IllIco_Nodes {
    private Node ill;
    private Node illIco;

    public ILL_IllIco_Nodes(Node ill, Node illIco) {
        this.ill = ill;
        this.illIco = illIco;
    }

    public Node getIll() {
        return ill;
    }

    public Node getIllIco() {
        return illIco;
    }
}

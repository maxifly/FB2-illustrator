package com.maxifly.fb2_illustrator.GUI;

import com.maxifly.fb2_illustrator.GUI.Controllers.Ctrl_Abstract;
import com.maxifly.fb2_illustrator.GUI.DomainModel.DM_Abstract;
import javafx.scene.Node;

/**
 * Created by Maxim.Pantuhin on 30.08.2016.
 */
public class GUI_Obj {
    public Node node;
    public Ctrl_Abstract controll;
    public DM_Abstract dm_model;

    public GUI_Obj(Node node, Ctrl_Abstract controll, DM_Abstract dm_model) {
        this.node = node;
        this.controll = controll;
        this.dm_model = dm_model;
    }
}

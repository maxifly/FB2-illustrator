package com.maxifly.fb2_illustrator.GUI.DomainModel;

import com.maxifly.fb2_illustrator.GUI.Factory_GUI;

import javax.xml.bind.JAXBException;

/**
 * Created by Maximus on 23.10.2016.
 */
public class DM_Book_from_CurrProj extends DM_Book_from_Proj {
    public DM_Book_from_CurrProj(Factory_GUI factory_gui) throws JAXBException {
        super(factory_gui);
        super.dm_projectObjectPropertyProperty().bindBidirectional(factory_gui.getDm_statusBar().dmProject_Property());
    }
}

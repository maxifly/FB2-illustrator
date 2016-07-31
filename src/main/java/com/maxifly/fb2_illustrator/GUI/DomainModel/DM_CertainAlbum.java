package com.maxifly.fb2_illustrator.GUI.DomainModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Maximus on 31.07.2016.
 */
public class DM_CertainAlbum {
    private StringProperty book_name;

    private StringProperty album_addr;
    private StringProperty book_src_file;
    private StringProperty book_dst_file;

    private BooleanProperty book_check;

    public DM_CertainAlbum() {
        book_name = new SimpleStringProperty();
        album_addr = new SimpleStringProperty();
        book_src_file = new SimpleStringProperty();
        book_dst_file = new SimpleStringProperty();
        book_check = new SimpleBooleanProperty();
    }

    public StringProperty book_name_Propery() {
        return book_name;
    }
    public StringProperty album_addr_Propery() {
        return album_addr;
    }
    public StringProperty book_src_file_Propery() {
        return book_src_file;
    }
    public StringProperty book_dst_file_Propery() {
        return book_dst_file;
    }

    public BooleanProperty book_check_Propery() {
        return book_check;
    }
}

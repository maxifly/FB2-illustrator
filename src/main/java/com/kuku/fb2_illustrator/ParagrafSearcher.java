package com.kuku.fb2_illustrator;

import com.kuku.fb2_illustrator.fb2_xml.model.FictionBook;
import com.kuku.fb2_illustrator.model.Illustrations;
import com.kuku.fb2_illustrator.model.Paragrafs;

/**
 * Created by Maximus on 17.05.2016.
 */
public interface ParagrafSearcher<TBook> {
    void  search(TBook book, Illustrations illustrations, Paragrafs paragrafs);
    TBook process(TBook book, Illustrations illustrations, Paragrafs paragrafs);
}

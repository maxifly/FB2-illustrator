package com.maxifly.fb2_illustrator;

import com.maxifly.fb2_illustrator.model.Illustrations;
import com.maxifly.fb2_illustrator.model.Paragrafs;

/**
 * Created by Maximus on 17.05.2016.
 */
public interface ParagrafSearcher<TBook> {
    void  search(TBook book, Illustrations illustrations, Paragrafs paragrafs);
    TBook process(TBook book, Illustrations illustrations, Paragrafs paragrafs);
}

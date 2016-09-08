package com.maxifly.vapi;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.maxifly.fb2_illustrator.Constants;
import com.maxifly.fb2_illustrator.model.SearchTemplate_POJO;
import com.maxifly.fb2_illustrator.model.TemplateType;
import com.maxifly.vapi.model.DATA.DATA_photo;
import com.maxifly.vapi.model.DATA.ILL_data;
import com.maxifly.vapi.model.DATA.ILL_search;
import com.maxifly.vapi.model.InternetIllustration;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maximus on 12.07.2016.
 * <p>
 * Фильтрует, подходящие по критериям иллюстрации и накапливает их в себе.
 * Потом пачкой отдает
 */
public class IllFilter {
    private static final IMessageConveyor mc = new MessageConveyor(Constants.getLocaleApp());
    private static final LocLoggerFactory llFactory_uk = new LocLoggerFactory(mc);
    private static final LocLogger log = llFactory_uk.getLocLogger(IllFilter.class.getName());

    private String book;
    private boolean isBookFilter;

    private List<InternetIllustration> illustrations = new ArrayList<>();
    private Gson gson = new Gson();


    private int currentIllNum = 0;
    private int currentIllSubNum = 0;


    public IllFilter(String book, boolean isBookFilter) {
        this.book = book;
        this.isBookFilter = isBookFilter;
    }

    public IllFilter() {
        this(null, false);
    }


    /**
     * Проверяет, что фотография является иллюстрацией
     * что она является подходящей иллюстрацией и добавляет ее к общему списку иллюстраций
     * @param photo
     */

    public void add(DATA_photo photo) {
        ILL_data ill_data = null;

        try {
            ill_data = gson.fromJson(photo.text, ILL_data.class);
        } catch (JsonSyntaxException e) {
            log.warn("Can not parse photo description {}", e);
            return;
        }

        if (ill_data.fb_ill == null) {
            log.warn("Photo is not illustration");
            return;
        }

        if (this.isBookFilter && !this.book.equals(ill_data.bk)) {
            log.debug("Illustration not compotable by book name {}, {}",
                    this.book,
                    ill_data.bk);
            return;
        }

        // Проверки пройдены.
        // Это подходящая иллюстрация


        if (ill_data.num != null && this.currentIllNum != ill_data.num) {
            this.currentIllNum = ill_data.num;
            this.currentIllSubNum = 0;
        } else {
            this.currentIllSubNum++;
        }

        String ill_id = "ill" + this.currentIllNum + "_" + this.currentIllSubNum;
        log.debug("New illustration id: ()", ill_id);
//TODO Надо поменять формирование идентификатора иллюстрации, полученной из интернета
        // Создадим иллюстрацию
        InternetIllustration illustration =
                new InternetIllustration(ill_id, ill_data.dsc, photo.url, this.currentIllNum, this.currentIllSubNum);

        // Теперь добавим в нее условия поиска
        for (ILL_search ill_search : ill_data.srch) {
            SearchTemplate_POJO searchTemplate_pojo;

            if (ill_search.s != null) {
                // Условия поиска простая строка
                searchTemplate_pojo = new SearchTemplate_POJO(TemplateType.substr, ill_search.s, null);
            } else if (ill_search.re != null) {
                // Условия поиска регулярное выражение
                searchTemplate_pojo = new SearchTemplate_POJO(TemplateType.regexp, ill_search.re, null);
            } else {
                searchTemplate_pojo = null;
            }

            if (searchTemplate_pojo != null) {
                illustration.addSearchTempale(searchTemplate_pojo);
            }
        }

        this.illustrations.add(illustration);


    }

    /**
     * Получить список накопленных иллюстраций
     * @return список иллюстраций
     */
    public List<InternetIllustration> getIllustrations() {
        return this.illustrations;
    }


}

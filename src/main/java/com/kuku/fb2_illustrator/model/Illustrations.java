package com.kuku.fb2_illustrator.model;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.kuku.fb2_illustrator.BookParse;
import com.kuku.fb2_illustrator.Constants;
import com.kuku.fb2_illustrator.fb2_xml.model.PType;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.util.*;

/**
 * Created by Maximus on 19.04.2016.
 */
public class Illustrations {

    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(BookParse.class.getName());

    private ArrayList<Illustration> allIllustrations = new ArrayList<>();
    private Map<PType, Set<Illustration>> illustratedParagrafs = new HashMap<>();
    private Map<Illustration, PType> chineIllustrations = new HashMap<>();

    private Set<Integer> chinedIllustrationIndexes = new TreeSet<>();
    private int lastChainIllustration = -1;


    public void addIllustration(Illustration illustration) {
        allIllustrations.add(illustration);
    }

    /**
     * Связывает параграф и иллюстрацию
     *
     * @param paragraf     - параграф
     * @param illustration - иллюстрация
     */
    public void illustratedParagraf(PType paragraf, Illustration illustration) {
        int illIndex = this.allIllustrations.indexOf(illustration);

        Set<Illustration> illSet = illustratedParagrafs.get(paragraf);
        if (illSet == null) {
            illSet = new LinkedHashSet<Illustration>();
            illustratedParagrafs.put(paragraf, illSet );
        }

        if (illSet.add(illustration)) {
            log.debug("Chine illustration " + illustration + " with paragraf.");
        } else {
            log.warn("Illustration " + illustration + " already chined with paragraf.");
        }

        chineIllustrations.put(illustration, paragraf);
        this.chinedIllustrationIndexes.add(illIndex);
        if (illIndex > lastChainIllustration) this.lastChainIllustration = illIndex;

    }

    /**
     * Определяет иллюстрирован объект или нет
     * @param paragraf - объект текста
     * @return иллюстрирован или нет
     */
    public boolean isIllustrated(Object paragraf) {
        return illustratedParagrafs.containsKey(paragraf);
    }

    /**
     * Возвращает итератор на иллюстрации к параграфу
     * @param paragraf параграф
     * @return итератор на иллюстрации
     */
    public Iterator<Illustration> getIllustrations(Object paragraf) {
        Set<Illustration> illSet = illustratedParagrafs.get(paragraf);
        if (illSet == null) {
            illSet = new HashSet<>();
        }
        return illSet.iterator();

    }

    /**
     * Возвращает итератор на список несвязанных иллюстраций,
     * следующих за последней связанной иллюстрацией
     *
     * @return список несвязанных иллюстраций
     */
    public Iterator<Illustration> getNotChained() {
        if (this.allIllustrations.size() > lastChainIllustration + 1) {
            return this.allIllustrations.listIterator(this.lastChainIllustration+1);
        } else {
            return (new ArrayList<Illustration>()).iterator();
        }
    }

    /**
     * расставляет несвязанные иллюстрации просто по номерам параграфов
     *
     * @param paragrafs - параграфы
     */
    public void chineByOrder(Paragrafs paragrafs) {
        Iterator<Integer> chined = chinedIllustrationIndexes.iterator();
        Integer startIndex = 0, endIndex = 0;
        Map<Integer, Integer> ill_par_chain = new HashMap<>();

        while (chined.hasNext()) {
            // Переходим к следующему окну
            startIndex = endIndex;
            endIndex = chined.next();
            addNotChined(startIndex, endIndex, paragrafs, ill_par_chain);
        }


        startIndex = endIndex;
        endIndex = allIllustrations.size() - 1;
        addNotChined(startIndex, endIndex, paragrafs, ill_par_chain);
    }


    public void addNotChined(Integer startIndex,
                             Integer endIndex,
                             Paragrafs paragrafs,
                             Map<Integer, Integer> ill_par_chain) {

        Integer startParagraf, endParagraf;

        if (endIndex > startIndex + 1) {
            // Есть промежуток
            // Определим стартовый и конечный номера параграфов

            // TODO Если ни одна из иллюстраций из границ окна ни с кем не связана (последнее окно или единственное окно), то параграфы должны быть первым и последним среди всех существующих параграфов
            startParagraf =
                    paragrafs.getParagrafNumber(
                            chineIllustrations.get(allIllustrations.get(startIndex)));
            endParagraf =
                    paragrafs.getParagrafNumber(
                            chineIllustrations.get(allIllustrations.get(endIndex)));

            log.debug("Start paragraf " + startParagraf + " endParagraf " + endParagraf);

            int notChinedIll = endIndex - startIndex; // Сколько не связано иллюстраций
            int step = ((endParagraf - startParagraf) / notChinedIll) + 1;

            log.debug("notChinedIll " + notChinedIll + " step " + step);

            // теперь начинаем связывать

            int chinedParagraf = startParagraf;
            for (int i = (startIndex + 1); i < endIndex; i++) {
                chinedParagraf += step;
                if (chinedParagraf > endParagraf) chinedParagraf = endParagraf;
                ill_par_chain.put(i, chinedParagraf);
                log.info("Not chined illustration number " + i + " chine with paragraf");
            }

        }

    }

}

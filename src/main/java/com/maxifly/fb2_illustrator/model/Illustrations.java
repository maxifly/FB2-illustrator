package com.maxifly.fb2_illustrator.model;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.maxifly.fb2_illustrator.Constants;
//import com.maxifly.fb2_illustrator.fb2_xml.model.PType;
import com.maxifly.fb2_illustrator.MyException;
import org.slf4j.cal10n.LocLogger;
import org.slf4j.cal10n.LocLoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by Maximus on 19.04.2016.
 */
public class Illustrations {

    private static IMessageConveyor mc = new MessageConveyor(
            Constants.getLocaleApp());
    private static LocLogger log = (new LocLoggerFactory(mc))
            .getLocLogger(Illustrations.class.getName());

    private ArrayList<Illustration> allIllustrations = new ArrayList<>();
    private Map<Paragraf, Set<Illustration>> illustratedParagrafs = new HashMap<>();
    private Map<Illustration, Paragraf> chineIllustrations = new HashMap<>();
    private Set<Illustration> notChinedIllustrations = new HashSet<>();

    private Set<Integer> chinedIllustrationIndexes = new TreeSet<>(); // Номера иллюстраций, уже связанных с кем нибудь
    private int lastChainIllustration = -1;


    public List<Illustration> getAllIllustrations() {
        return this.allIllustrations;
    }

    public void addIllustration(Illustration illustration) {
        allIllustrations.add(illustration);
        notChinedIllustrations.add(illustration);
    }

    /**
     * Связывает параграф и иллюстрацию
     *
     * @param paragraf     - параграф
     * @param illustration - иллюстрация
     */
    public void illustratedParagraf(Paragraf paragraf, Illustration illustration) {
        int illIndex = this.allIllustrations.indexOf(illustration);

        Set<Illustration> illSet = illustratedParagrafs.get(paragraf);
        if (illSet == null) {
            illSet = new LinkedHashSet<Illustration>();
            illustratedParagrafs.put(paragraf, illSet);
        }

        if (illSet.add(illustration)) {
            log.info("Chine illustration {} with paragraf (index: {}).", illustration, paragraf.getIndexInParagrafs());
        } else {
            log.warn("Illustration {} already chined with paragraf (index: {}).", illustration, paragraf);
        }

        chineIllustrations.put(illustration, paragraf);
        this.chinedIllustrationIndexes.add(illIndex);
        this.notChinedIllustrations.remove(illustration);
        if (illIndex > lastChainIllustration) this.lastChainIllustration = illIndex;

    }

    /**
     * Определяет иллюстрирован объект или нет
     *
     * @param paragraf - объект текста
     * @return иллюстрирован или нет
     */
    public boolean isIllustrated(Paragraf paragraf) {
        return illustratedParagrafs.containsKey(paragraf);
    }

    /**
     * Возвращает итератор на иллюстрации к параграфу
     *
     * @param paragraf параграф
     * @return итератор на иллюстрации
     */
    public Iterator<Illustration> getIllustrations(Paragraf paragraf) {
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
            return this.allIllustrations.listIterator(this.lastChainIllustration + 1);
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
        Integer startIndex, endIndex = 0;
        ArrayList<ParIll> ill_par_chain = new ArrayList<>();

        while (chined.hasNext()) {
            // Переходим к следующему окну
            startIndex = endIndex;
            endIndex = chined.next();
            addNotChined(startIndex, endIndex, paragrafs, ill_par_chain);
        }


        startIndex = endIndex;
        endIndex = allIllustrations.size() - 1;
        addNotChined(startIndex, endIndex, paragrafs, ill_par_chain);

        // Все иллюстрации распределили. Теперь свяжем.
        Iterator<ParIll> parIllIterator = ill_par_chain.iterator();
        while (parIllIterator.hasNext()) {
            ParIll parIll = parIllIterator.next();
            this.illustratedParagraf(paragrafs.getParagraf(parIll.paragraf_index), allIllustrations.get(parIll.ill_index));

        }

    }


    public void scaleIllustrations(int Weight, int Hight) throws IOException, MyException {
        Path tempDir = Files.createTempDirectory("fbill_");
        for (Illustration ill : allIllustrations) {
            ill.resizeFile(Weight,Hight,tempDir);
        }
    }

    public void addNotChined(Integer startIndex,
                             Integer endIndex,
                             Paragrafs paragrafs,
                             ArrayList<ParIll> ill_par_chain) {

        Integer startParagraf, endParagraf;
        Integer startUnchinedIll, endUnchinedIll;


        log.debug("startIndex:{} endIndex:{}", startIndex, endIndex);

        boolean isUnchinedEndIll = false;

        if ((endIndex + 1) == allIllustrations.size()) {
            if (
                    paragrafs.getParagrafNumber(
                            chineIllustrations.get(allIllustrations.get(endIndex))) == null) {
                isUnchinedEndIll = true;
            }
        }

        if (endIndex > startIndex + 1 || (startIndex == 0 && endIndex == 1) || isUnchinedEndIll) {
            // Есть промежуток из непривязанных иллюстраций или непривязана первая иллюстрация
            // Определим стартовый и конечный номера параграфов

            startParagraf =
                    paragrafs.getParagrafNumber(
                            chineIllustrations.get(allIllustrations.get(startIndex)));
            if (startParagraf == null) {
                startParagraf = 0;
                startUnchinedIll = startIndex;
            } else {
                startUnchinedIll = startIndex + 1;
            }


            endParagraf =
                    paragrafs.getParagrafNumber(
                            chineIllustrations.get(allIllustrations.get(endIndex)));

            if (endParagraf == null) {
                endParagraf = paragrafs.getSize();
                endUnchinedIll = endIndex;
            } else {
                endUnchinedIll = endIndex - 1;
            }

            log.debug("Start paragraf {} endParagraf {}, startIll {} endIll {}", startParagraf, endParagraf, startUnchinedIll, endUnchinedIll);


            int notChinedIll = endUnchinedIll - startUnchinedIll + 1; // Сколько не связано иллюстраций
            int step = ((endParagraf - startParagraf) / (notChinedIll + 1));
            if (step == 0) {
                step = 1;
            }

            log.debug("notChinedIll " + notChinedIll + " step " + step);

            // теперь начинаем связывать

            int chinedParagraf = startParagraf;
            for (int i = startUnchinedIll; i <= endUnchinedIll; i++) {
                chinedParagraf += step;
                if (chinedParagraf > endParagraf) chinedParagraf = endParagraf;
                ill_par_chain.add(new ParIll(chinedParagraf, i));
                log.info("Not chined illustration number " + i + " chine with paragraf number " + chinedParagraf);
            }

        }

    }


    private class ParIll {
        Integer paragraf_index;
        Integer ill_index;

        public ParIll(Integer paragraf_index, Integer ill_index) {
            this.paragraf_index = paragraf_index;
            this.ill_index = ill_index;
        }
    }
}

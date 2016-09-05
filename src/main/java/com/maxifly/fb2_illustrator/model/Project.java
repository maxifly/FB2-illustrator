package com.maxifly.fb2_illustrator.model;

import com.maxifly.fb2_illustrator.MyException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Maxim.Pantuhin on 25.08.2016.
 */
public class Project {
    private String id = "123456789";
    private Set<SearchTemplate_POJO> bookNameTemplates = new HashSet<>();
    private List<Illustration> illustrations = new ArrayList(); //TODO тут надо как-то сохранять порядок вставки



    public void addBookNameTempale(SearchTemplate_POJO bookNameTemplate) {
        this.bookNameTemplates.add(bookNameTemplate);
    }

    public void addIll(Illustration illustration) {
        illustration.setId(Integer.toString(illustrations.size()));
        this.illustrations.add(illustration);
        illustration.setProject(this);
    }

    public List<Illustration> getIllustrations() {
        return illustrations;
    }


    /**
     * Меняет порядок иллюстраций
     * @param moveIllId - какую иллюстрацию передвинуть
     * @param beforeIllId - перед какой поставить (если null - то сделать последней)
     */
    public void moveIll(String moveIllId, String beforeIllId ) throws MyException {
        ArrayList<Illustration> changeList = new ArrayList();

        // Сначала найдем иллюстрацию, которую надо передвинуть
        Illustration moveIll = null;
        int beforeIll_Idx = -1;
        int moveIll_idx = -1;

        int idx = 0;

        for (Illustration ill:illustrations) {
            if (ill.getId().equals(moveIllId)) {
                moveIll = ill;
                moveIll_idx = idx;
            }
            if (ill.getId().equals(beforeIllId)) {
                beforeIll_Idx = idx;
            }
            idx++;
        }

        // Проанализируем ошибки

        if (moveIll_idx == -1) {
            throw new MyException("Не найдена иллюстрация с идентификатором " + moveIllId);
        }
        if (beforeIllId != null && beforeIll_Idx == -1) {
            throw new MyException("Не найдена иллюстрация с идентификатором " + beforeIllId);
        }

        // Сформируем новый список
        if (beforeIll_Idx == -1) {
            idx = moveIll_idx;
        } else if (beforeIll_Idx < moveIll_idx ) {
            idx = beforeIll_Idx;
        } else {
            idx = moveIll_idx;
        }

        // Скопируем в новый массив элементы, которые идут без изменений
        if (idx != 0) {
            changeList.addAll(illustrations.subList(0,idx-1));
        }

        // Теперь переберем остаток, так как у остатка надо менять id
        Integer curr_idx_InNewList = changeList.size();
        int curr_idx = idx;

        for(Illustration ill : illustrations.subList(idx, illustrations.size())) {
            if (curr_idx != moveIll_idx) {

                if (curr_idx == beforeIll_Idx ) {
                    moveIll.setId(curr_idx_InNewList.toString());
                    changeList.add(moveIll);
                    curr_idx_InNewList++;
                }

                ill.setId(curr_idx_InNewList.toString());
                changeList.add(ill);
                curr_idx_InNewList++;
            }
            curr_idx++;
        }

        // Если надо было поставить передвигаемый элемент в конец списка. То добавим его
        if (beforeIll_Idx == -1) {
            moveIll.setId(curr_idx_InNewList.toString());
            changeList.add(moveIll);
            curr_idx_InNewList++;
        }

        // Теперь вставим новый список вместо старого
        illustrations.clear();
        illustrations.addAll(changeList);
    }


}

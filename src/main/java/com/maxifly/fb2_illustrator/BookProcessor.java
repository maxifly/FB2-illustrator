package com.maxifly.fb2_illustrator;

import com.maxifly.fb2_illustrator.model.Illustrations;

import java.nio.file.Path;

/**
 * Created by Maximus on 17.05.2016.
 */
public interface BookProcessor {

    /**
     * Загружает файл
     * @param inputFile - ссылка на исходный файл
     */
    public void loadBook(Path inputFile)throws Exception;

    /**
     * Получить название книги
     * @return Название книги
     */
    public String getTitle();

    /**
     * Обрабатывает файл, вставляя в него иллюстрации
     *
     * @param illustrations - список иллюстраций
     * @param projectInfo - информация о проекте, добавляемя в конец книги
     * @param outputFile - ссылка на итоговый файл
     * @throws Exception
     */
    public void processBook(
            Illustrations illustrations,
            String projectInfo,
            Path outputFile
                            ) throws Exception;

}

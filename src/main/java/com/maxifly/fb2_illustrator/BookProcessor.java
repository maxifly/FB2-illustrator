package com.maxifly.fb2_illustrator;

import com.maxifly.fb2_illustrator.model.Illustrations;

import java.nio.file.Path;

/**
 * Created by Maximus on 17.05.2016.
 */
public interface BookProcessor {
    /**
     * Обрабатывает файл, вставляя в него иллюстрации
     *
     * @param illustrations - список иллюстраций
     * @param projectInfo - информация о проекте, добавляемя в конец книги
     * @param inputFile - ссылка на исходный файл
     * @param outputFile - ссылка на итоговый файл
     * @throws Exception
     */
    void processBook(
            Illustrations illustrations,
            String projectInfo,
            Path inputFile,
            Path outputFile
                            ) throws Exception;

}

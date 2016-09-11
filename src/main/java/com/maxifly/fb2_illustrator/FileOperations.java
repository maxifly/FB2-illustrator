package com.maxifly.fb2_illustrator;

import java.io.*;

/**
 * Created by Maximus on 11.09.2016.
 */
public class FileOperations {

    public static void writeAll(File file, String outString) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(outString);
        }
    }

    public static String readAll(File file) throws IOException {
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
            //В цикле построчно считываем файл
            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
        }

        //Возвращаем полученный текст с файла
        return sb.toString();


    }




}

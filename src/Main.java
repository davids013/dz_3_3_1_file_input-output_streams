import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.io.File.separator;

public class Main {
    private static final String SEP = separator;

    public static void main(String[] args) {
        System.out.println("Домашнее задание к занятию 1.3\n" +
                "Потоки ввода-вывода. Работа с файлами. Сериализация\n" +
                "Задача 1: Установка\n");

        String gamesPath = "T:" + SEP + "Program Files" + SEP + "Games";
        installTo(gamesPath);
    }

    private static String createFile(File newFile) {
        String filename = newFile.getName();
        String status = "";
        String dateTime = "";
        if (filename.contains(".")) {
            try {
                if (newFile.createNewFile()) {
                    dateTime = LocalDateTime.now() + "\t";
                    status = "Файл " + newFile.getName() + " создан";
                } else {
                    dateTime = LocalDateTime.now() + "\t";
                    status = "Файл " + newFile.getName() + " не удалось создать";
                }
                System.out.println(status);
                status = dateTime + status;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                dateTime = LocalDateTime.now() + "\t";
                status += "Ошибка при создании файла " + newFile.getName();
                System.out.println(status);
                status = dateTime + status;
            }
        } else {
            if (newFile.mkdir()) {
                dateTime = LocalDateTime.now() + "\t";
                status += "Директория " + newFile.getName() + " создана";
                System.out.println(status);
                status = dateTime + status;
            }
        }
        return status;
    }

    private static void writeTo(String data, File file) {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(data);
            System.out.println("Информация о ходе установки записана в файл " + file.getName());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println("Не удалось записать файл " + file.getName());
        }
    }

    private static void installTo(String path) {
        File gamesFolder = new File(path);
        StringBuilder sb = new StringBuilder();
        if (!gamesFolder.exists()) {
            if (!gamesFolder.mkdir()) {
                System.err.println("Ошибка. Доступ к директории установки не получен\n" +
                        "Завершение работы инсталлятора...");
            }
        }
        String[][] gameStructure = {
                {"src", "res", "savegames", "temp"},
                {"main", "drawables", "", "temp.txt"},
                {"test", "vectors"},
                {"main" + SEP + "Main.java", "icons"},
                {"main" + SEP + "Utils.java"}
        };
        for (int i = 0; i < gameStructure.length; i++) {
            if (i == 0) {
                for (String folder : gameStructure[i])
                    sb
                            .append(createFile(new File(path + SEP + folder)))
                            .append("\r\n");
            } else {
                for (int j = 0; j < gameStructure[i].length; j++) {
                    if (gameStructure[i][j].isBlank()) continue;
                    sb
                            .append(createFile(new File(path + SEP + gameStructure[0][j]
                            + SEP + gameStructure[i][j])))
                            .append("\r\n");
                }
            }
        }
        writeTo(sb.toString(), new File(path + SEP
                + gameStructure[0][3] + SEP + gameStructure[1][3]));
    }
}

package lesson1;

import kotlin.NotImplementedError;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName){
        throw new NotImplementedError();
        }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws Exception {

        List<Integer> listNegativeInt = new ArrayList<>();
        List<Integer> listPositiveInt = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(new File(inputName)));
        String temp = br.readLine();
        int value = 0;

        while (temp != null) {
            value = (int) (Double.parseDouble(temp) * 10);
            if (value < 0) {
                listNegativeInt.add(-value);
            } else listPositiveInt.add(value);
            temp = br.readLine();
        }
        int[] arrayAm = new int[listNegativeInt.size()];
        int[] arrayDuong = new int[listPositiveInt.size()];

        for (int i = 0; i < listNegativeInt.size(); i++) {
            arrayAm[i] = listNegativeInt.get(i);
        }
        for (int j = 0; j < listPositiveInt.size(); j++) {
            arrayDuong[j] = listPositiveInt.get(j);
        }
        arrayAm = Sorts.countingSort(arrayAm, 2730);
        arrayDuong = Sorts.countingSort(arrayDuong, 5000);

        try(FileWriter fw = new FileWriter(outputName,false)) {

            for (int i = arrayAm.length - 1; i >= 0; i--) {
                fw.write("-" + String.valueOf((double) arrayAm[i] / 10) + "\n");
            }
            for (int j = 0; j < arrayDuong.length; j++) {
                fw.write(String.valueOf((double) arrayDuong[j] / 10) + "\n");
            }
        }
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File(inputName)));
        List<Integer> list = new ArrayList<>();
        String str = br.readLine();
        while (str != null) {
            list.add(Integer.parseInt(str));
            str = br.readLine();
        }
        Collections.sort(list);
        int maxQt = 0;
        int countQt = 0;
        int val = list.get(0);
        int ele = list.get(0);
        for (int i: list) {
            if (i != ele) {
                countQt = 0;
                ele = i;
            }
            countQt++;
            if (countQt > maxQt) {
                maxQt = countQt;
                val =i;
            }
        }
        FileWriter fw = new FileWriter(new File(outputName));
        br = new BufferedReader(new FileReader(new File(inputName)));
        str = br.readLine();
        while (str != null) {
            if (Integer.parseInt(str) != val) fw.write(str + "\n");
            str = br.readLine();
        }
        for (int j = 1; j < maxQt + 1; j++) {
            fw.write(String.valueOf(val) + "\n");
        }
        fw.close();

        // трудоёмкост : O(n*log(n))

        // ресурсоёмкост : O(n)
    }


    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        int x = 0;
        int y = first.length;
        int z = -1;
        while (z < second.length && x < first.length) {
            z++;
            if (y >= second.length) {
                second[z] = first[x];
                x++;
                continue;
            }
            if (first[x].compareTo(second[y]) > 0){
                second[z] = second[y];
                y++;
            } else {
                second[z] = first[x];
                x++;
            }
        }
        // трудоёмкост : O(n)

        // ресурсоёмкост : O(1)
    }

}

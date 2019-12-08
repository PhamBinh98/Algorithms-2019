package lesson6;

import kotlin.NotImplementedError;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        int[][] matrix = new int[first.length() + 1][second.length() + 1];
        for (int i = 1; i <= first.length(); i++)
            for (int j = 1; j <= second.length(); j++)
                if (first.charAt(i - 1) == second.charAt(j - 1))
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                else matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i][j - 1]);
        StringBuilder res = new StringBuilder();
        int i = first.length();
        int j = second.length();
        while (i > 0 && j > 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                res.append(first.charAt(i - 1));
                i--;
                j--;
            } else if (matrix[i][j - 1] > matrix[i - 1][j]) {
                j--;
            } else i--;
        }
        return res.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.isEmpty()) return Collections.emptyList();
        int size = list.size();
        int[] prev = new int[size];
        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            a[i] = 1;
            prev[i] = -1;
            for (int j = 0; j < size; j++) {
                if (list.get(j) < list.get(i) && a[j] + 1 > a[i]) {
                    a[i] = a[j] + 1;
                    prev[i] = j;
                }
            }
        }
        int pos = 0;
        int length = a[0];
        for (int i = 0; i < size; i++) {
            if (a[i] > length) {
                pos = i;
                length = a[i];
            }
        }
        List<Integer> result = new ArrayList<>();
        while (pos != -1) {
            result.add(0, list.get(pos));
            pos = prev[pos];
        }
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        List<List<Integer>> res = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(inputName))) {
            String str = bf.readLine();
            while (str != null) {
                List<Integer> temp = new ArrayList<>();
                for (String ch : str.split(" ")) {
                    temp.add(Integer.parseInt(ch));
                }
                res.add(temp);
                str = bf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = res.get(0).size();
        int height = res.size();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                res.get(i).set(j, res.get(i).get(j) + min(res, i, j));
            }
        }
        return res.get(height - 1).get(width - 1);
    }
    private static int min(List<List<Integer>> field, int i, int j) {
        if (i == 0 && j == 0) {
            return 0;
        }
        else if (i == 0) {
            return field.get(i).get(j - 1);
        }
        else if (j == 0) {
            return field.get(i - 1).get(j);
        }
        else {
            return Math.min(Math.min(field.get(i - 1).get(j - 1),
                    field.get(i).get(j - 1)),
                    field.get(i - 1).get(j));
        }
    }
}

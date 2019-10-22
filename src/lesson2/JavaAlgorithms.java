package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        List<Integer> list = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(inputName));

            while (scanner.hasNextInt()) {
                int number = scanner.nextInt();

                if (number > 0) {
                    list.add(number);

                } else throw new IllegalArgumentException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int[] aNew = new int[list.size() - 1];

        for (int i = 0; i < aNew.length; i++) {
            aNew[i] = list.get(i + 1) - list.get(i);
        }

        int[] res = buy(aNew, 0, aNew.length - 1);

        return new Pair(res[0] + 1, res[1] + 2);
    }
    static private int[] buy(int a[], int low, int high) {

        int[] res = new int[3];
        /*
         res[0] - левый индекс

         res[1] - правый индекс
         */
        if (low == high) {
            res[0] = low;
            res[1] = high;
            res[2] = a[low];
            return new int[]{low, high, a[low]};

        } else {
            int mid = (low + high) / 2;
            int[] maxLeft = buy(a, low, mid);
            int[] maxRight = buy(a, mid + 1, high);
            int[] maxMid = arrayCrossing(a, low, mid, high);
            if (maxLeft[2] >= maxRight[2] && maxLeft[2] >= maxMid[2]) {
                return maxLeft;

            } else if (maxRight[2] >= maxLeft[2] && maxRight[2] >= maxMid[2]) {
                return maxRight;

            } else {
                return maxMid;
            }
        }
    }

    static private int[] arrayCrossing(int[] a, int low, int mid, int hight) {

        int maxSumLeft = -2147483648;
        int sum = 0;
        int res[] = new int[3];
        res[0] = 0;
        res[1] = 0;

        /*
        int[0] - индекс слева
        int[1] - индекс справа
        int[2] - сумма
        */
        for (int i = mid; i >= low; i--) {

            sum += a[i];
            if (sum > maxSumLeft) {
                maxSumLeft = sum;
                res[0] = i;
                res[1] = 0;
            }
        }
        sum = 0;
        int maxSumRight = -2147483648;

        for (int i = mid + 1; i <= hight; i++) {

            sum += a[i];
            if (sum > maxSumRight) {
                maxSumRight = sum;
                res[1] = i;
            }
        }
        if (maxSumLeft == -2147483648) maxSumLeft = 0;

        if (maxSumRight == -2147483648) maxSumRight = 0;
        res[2] = maxSumLeft + maxSumRight;

        return res;
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * <p>
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {

        int x = 0;
        if (menNumber == 0) {
            throw new NotImplementedError();
        }
        for (int i = 2; i <= menNumber; i++) {
            x = (choiceInterval + x) % i;
        }
        return x + 1;


    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String firs, String second) {

        if (firs.equals("") || second.equals("")) return "";
        String str1[] = firs.split("");
        String str2[] = second.split("");

        int line = str1.length + 1;
        int column = str2.length + 1;
        int[][] a = new int[line][column];

        for (int i = 1; i < line; i++) {
            a[i][0] = (int) firs.charAt(i - 1);
        }
        for (int i = 1; i < column; i++) {
            a[0][i] = (int) second.charAt(i - 1);
        }

        int max = 0;
        int maxIndLine = 0;

        for (int i = 1; i < line; i++) {

            for (int j = 1; j < column; j++) {

                if (a[i][0] == a[0][j]) {

                    if (i > 1 && j > 1) a[i][j] = a[i - 1][j - 1] + 1;

                    else a[i][j] = 1;

                    if (a[i][j] > max) {
                        max = a[i][j];
                        maxIndLine = i;
                    }
                }
            }
        }
        int ind = max - 1;
        String res = "";
        char ch = ' ';

        while (ind >= 0) {
            ch = (char) a[maxIndLine - ind][0];
            res += String.valueOf(ch);
            ind--;
        }

        return res;
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1) return 0;
        boolean[] arr = new boolean[limit + 1];
        for (int x = 2; x <= limit; x++) {
            arr[x] = true;
        }
        arr[0] = false;
        arr[1] = false;
        for (int i = 2; i <= Math.sqrt(limit); i++) {
            if (arr[i]) {
                for (int j = i * i; j <= limit; j += i) arr[j] = false;
            }
        }
        int res = 0;
        for (boolean e : arr) {
            if (e) res++;
        }
        return res;

    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}

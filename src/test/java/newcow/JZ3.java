package newcow;

/**
 * @author: hhuang
 * @date: 2022/10/22
 */
public class JZ3 {

    public int duplicate (int[] numbers) {
        int[] a = new int[10001];

        for (int number : numbers) {
            if (number < 0 || number > numbers.length) {
                return -1;
            }

            a[number] ++;
        }

        for (int i = 0; i < a.length; i++) {
            if (a[i] >= 2) {
                return i;
            }
        }

        return -1;
    }
}

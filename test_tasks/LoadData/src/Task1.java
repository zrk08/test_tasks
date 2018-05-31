import java.time.LocalDate;

public class Task1 {
    private static String offices;
    private static int  iter;
    private static String outputFile;

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                offices = args[0];
                iter = Integer.parseInt(args[1]);
                outputFile = args[2];
            } catch (NumberFormatException e) {
                System.err.println("Проверьте, все типы введеных аргументов коммандной строки");
                System.exit(1);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Проверьте, все ли аргументы Вы вводите. Формат аргументов: <файл офисов> <количество итераций> <выходной файл>");
                System.exit(1);
            }

        }
        CreateDataFiles cdf = new CreateDataFiles(outputFile, offices ,iter);
        cdf.init();
    }
}





import java.io.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class UtilsDataFiles {
    String name;
    String office;

    public UtilsDataFiles() {
        this.name = "test1";
        this.office = "offices";
    }

    public UtilsDataFiles(String name, String office) {
        this.name = name;
        this.office = office;
    }

    public void OpenFile(String text) {
        File file = null;
        BufferedWriter output = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter(name, true);
            bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }
    }

    public Object ReadOffices() {
        HashSet<String> set = new HashSet();
        int i = 0;
        try {
            Scanner sc = new Scanner(new File(office));
            while (sc.hasNext()) {
                String line = sc.nextLine();
                set.add(line);
            }
            int size = set.size();
            int item = new Random().nextInt(size);
            for (Object obj : set) {
                if (i == item)
                    return obj;
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return null;
    }

//  Получаем случайную дату в заланом диапазоне.
    public LocalDate RandomDate(LocalDate minDate, LocalDate maxDate) {
        Random random = new Random();
        int minDay = (int) minDate.toEpochDay();
        int maxDay = (int) maxDate.toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        System.out.println(LocalDate.ofEpochDay(randomDay));
        return LocalDate.ofEpochDay(randomDay);
    }

    public int RandomOperations(int minData, int maxData) {
            maxData -= minData;
            System.out.println((int)((Math.random() * ++maxData) + minData));
            return (int) (Math.random() * ++maxData) + minData;
    }

    public Time RandomDateTime() {
            Random random = new Random();
            final int millisInDay = 24*60*60*1000;
            Time time = new Time((long)random.nextInt(millisInDay));
            System.out.println(time);
            return time;


    }
}
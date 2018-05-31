import java.sql.Time;
import java.time.LocalDate;

public class CreateDataFiles {
    private String fileName;
    private int iter;
    private String dat;
    private int oper;
    private String tim;
    private String office;
    private String officeFile;

    public CreateDataFiles(String fileName, String officeFile, int iter) {
        this.fileName = fileName;
        this.officeFile = officeFile;
        this.iter = iter;
    }

    public void init() {
        for (int i = 0; i < iter; i++) {
            UtilsDataFiles fc = new UtilsDataFiles(fileName, officeFile);
            dat = fc.RandomDate(LocalDate.of(2017, 1, 1), LocalDate.of(2018, 1, 1)).toString();
            oper = fc.RandomOperations(10000, 100000);
            tim = fc.RandomDateTime().toString();
            office = (String) fc.ReadOffices();
            fc.OpenFile(dat + ' ' + tim + ',' + office + ',' + oper);
        }
    }


}

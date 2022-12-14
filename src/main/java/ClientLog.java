import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<int[]> clientInputs = new ArrayList<>();

    public void log(int productNum, int amount) {
        int[] clientInput = new int[2];
        clientInput[0] = productNum;
        clientInput[1] = amount;
        clientInputs.add(clientInput);
    }

    public void exportAsCSV(File txtFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            writer.writeNext(new String[]{"productNum", "amount"});
            for (int[] clientInput : clientInputs) {
                String[] s = new String[2];
                s[0] = Integer.toString(clientInput[0]);
                s[1] = Integer.toString(clientInput[1]);
                writer.writeNext(s);
            }
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
}

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String[]> clientInputs = new ArrayList<>();

    public void log(int productNum, int amount) {
        String[] clientInput = {Integer.toString(productNum), Integer.toString(amount)};
        clientInputs.add(clientInput);
    }

    public void exportAsCSV(File txtFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            writer.writeNext(new String[]{"productNum", "amount"});
            for (String[] clientInput : clientInputs) {
                writer.writeNext(clientInput);
            }
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
}

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Scanner scanner = new Scanner(System.in);
        XMLReader reader = new XMLReader();
        reader.readXMLDocument();
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }
        File jsonFile = new File(reader.getFileNameForSaving());
        File csvFile = new File(reader.getFileNameForLog());
        File txtFile = new File("basket.txt");
        ClientLog clientLog = new ClientLog();
        Basket basket = null;
        // загрузка данных из корзины (необходима/нет, из какого формата и какого файла)
        if (reader.getIsNeededToLoadDataFromBasket().equals("true")) {
            if (jsonFile.exists()) {
                System.out.println();
                System.out.println("Корзина уже существует...производим загрузку данных из json - файла");
                System.out.println();
                basket = Basket.loadFromJSONFile(jsonFile);
                basket.printCart();
            }
            if (txtFile.exists()) {
                System.out.println();
                System.out.println("Корзина уже существует...производим загрузку данных из txt - файла");
                System.out.println();
                basket = Basket.loadFromTxtFile(txtFile);
                basket.printCart();
            }
        } else {
            System.out.println("Создаем новую корзину..");
            System.out.println();
            basket = new Basket(products, prices);
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                scanner.close();
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            // логирование действий покупателя (нужно/нет, в какой файл)
            if (reader.getIsNeededToLog().equals("true")) {
                clientLog.log(productNumber + 1, productCount);
            }
        }
        clientLog.exportAsCSV(csvFile);
        basket.printCart();
        // сохранение данных корзины (нужно/нет, в каком формате, в какой файл)
        if (reader.getIsNeededToSaveDataFromBasket().equals("true")) {
            if (reader.getFormatForSaving().equals("json")) {
                basket.saveJSON(jsonFile);
            } else {
                basket.saveTxt(txtFile);
            }
        }
    }
}

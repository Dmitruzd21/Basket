import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        File textFile  = new File("basket.txt");
        File csvFile = new File("log.csv");
        ClientLog clientLog = new ClientLog();
        Basket basket;
        if (textFile.exists()) {
            System.out.println();
            System.out.println("Корзина уже существует...производим загрузку данных");
            System.out.println();
            basket  = Basket.loadFromTxtFile(textFile);
            basket.printCart();
        } else {
            System.out.println("Создаем новую корзину..");
            System.out.println();
            basket = new Basket(products, prices);
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            clientLog.log(productNumber + 1, productCount);
            clientLog.exportAsCSV(csvFile);
        }
        basket.printCart();
        basket.saveTxt(textFile);
    }

}

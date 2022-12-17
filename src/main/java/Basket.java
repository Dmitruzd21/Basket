import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    protected String[] products;
    protected int[] prices;
    protected int[] productsCount;

    public Basket(String[] products, int[] prices) {
        this.prices = prices;
        this.products = products;
        productsCount = new int[prices.length];
    }

    public void addToCart(int productNum, int amount) {
        productsCount[productNum] += amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        int sumProducts = 0;
        for (int i = 0; i < productsCount.length; i++) {
            if (productsCount[i] > 0) {
                int sumProduct = productsCount[i] * prices[i];
                System.out.println(products[i] + " " + productsCount[i] + " шт " + prices[i] + " руб/шт " + sumProduct + " руб в сумме");
                sumProducts += sumProduct;
            }
        }
        System.out.println("Итого: " + sumProducts + " руб");
    }

    public void saveTxt(File textFile) {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (String product : products) {
                writer.print(product + " ");
            }
            writer.append('\n');
            for (int price : prices) {
                writer.print(price + " ");
            }
            writer.append('\n');
            for (int productCount : productsCount) {
                writer.print(productCount + " ");
            }
            System.out.println("Корзина сохранена");
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String s;
            Basket basket;
            List<String> stringList = new ArrayList<>();
            while ((s = reader.readLine()) != null) {
                stringList.add(s);
            }
            // преобразуем 1ю строку в массив продуктов
            String[] products = stringList.get(0).split(" ");
            // преобразуем 2ю строку в массив цен
            String[] prices = stringList.get(1).split(" ");
            int[] intPrices = new int[prices.length];
            for (int i = 0; i < intPrices.length; i++) {
                intPrices[i] = Integer.parseInt(prices[i]);
            }
            // преобразуем 3ю строку в массив количества товаров
            String[] counts = stringList.get(2).split(" ");
            int[] intCounts = new int[counts.length];
            for (int i = 0; i < intCounts.length; i++) {
                intCounts[i] = Integer.parseInt(counts[i]);
            }
            // восстанавливаем корзину
            basket = new Basket(products, intPrices);
            basket.setProductsCount(intCounts);
            return basket;

        } catch (IOException ex) {
            ex.getMessage();
        }
        return null;
    }

    public void saveJSON(File jsonFile) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String gsonStr = gson.toJson(this);
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(gsonStr);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public static Basket loadFromJSONFile(File jsonFile) {
        // чтение JSON и преобразование к строке
        JSONParser parser = new JSONParser();
        String jsonString = "";
        try {
            Object obj = parser.parse(new FileReader(jsonFile));
            JSONObject jsonObject = (JSONObject) obj;
            jsonString = jsonObject.toString();
        } catch (IOException | ParseException ex) {
            ex.getMessage();
        }
        // процесс преобразования строки JSON в объект
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Basket basket = gson.fromJson(jsonString, Basket.class);
        return basket;
    }

    public void setProductsCount(int[] productsCount) {
        this.productsCount = productsCount;
    }
}

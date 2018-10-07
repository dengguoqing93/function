package org.guoqing.java_functional.service;

import model.OrderLine;
import model.Price;
import model.Product;
import model.Weight;

import java.util.List;

import static function.CollectionUtilities.*;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-09-29
 */
public class Store {
    public static void main(String[] args) {
        Product toothPaste =
                new Product("Tooth paste", Price.price(1.5), Weight.weight(0.5));
        Product toothBrush =
                new Product("Tooth brush", Price.price(3.5), Weight.weight(0.3));

        List<OrderLine> order =
                list(new OrderLine(toothBrush, 3), new OrderLine(toothPaste, 2));
        Price price = foldLeft(order, Price.ZERO, Price.sum);
        Weight weight = foldLeft(order, Weight.ZERO, Weight.sum);
        System.out.println(String.format("总价为：%s", price));
        System.out.println(String.format("总重量为：%s",weight));

    }
}
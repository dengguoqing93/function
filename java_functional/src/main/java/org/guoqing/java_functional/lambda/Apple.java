package org.guoqing.java_functional.lambda;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-08-26
 */
public class Apple {
    private String color;
    private int weight;

    public Apple() {
    }

    public Apple(int weight) {
        this.weight = weight;
    }

    public Apple(String color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public static void main(String[] args) {
        List<Integer> weights = Arrays.asList(7,6,9,10);
        List<Apple> apples = map(weights,Apple::new);
        for (Apple apple : apples) {
            System.out.println(apple);
        }
    }

    public static List<Apple> map(List<Integer> weights, Function<Integer, Apple> function) {
        List<Apple> result = new ArrayList<>();
        for (Integer weight : weights) {
            result.add(function.apply(weight));
        }
        return result;
    }

    public static List<Apple> filter(List<Apple> apples, Predicate<Apple> predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void test1() {
        List<Apple> apples = new ArrayList<>();
        Random random = new Random(100);
        for (int i = 0; i < 50; i++) {
            if (i % 3 == 0) {
                apples.add(new Apple("green", i + random.nextInt(10)));
            }
            if (i % 3 == 1) {
                apples.add(new Apple("blue", i + random.nextInt(10)));
            }
            if (i % 3 == 2) {
                apples.add(new Apple("red", i + random.nextInt(10)));
            }
        }

        Apple apple1 = new Apple();

        Predicate<Apple> predicate = apple1::isGreenApple;

        Supplier<Apple> supplier = Apple::new;

        List<Apple> greenApples = filter(apples, predicate);
        List<Apple> blueApples = filter(apples, (apple -> apple.color.equals("blue")));
        List<Apple> redApples = filter(apples, (apple -> apple.color.equals("red")));
        redApples.sort(Comparator.comparing(Apple::getWeight));

        for (Apple greenApple : greenApples) {
            System.out.println(greenApple);
        }
        for (Apple blueApple : blueApples) {
            System.out.println(blueApple);
        }

        for (Apple redApple : redApples) {
            System.out.println(redApple);
        }

    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                '}';
    }

    public boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

}

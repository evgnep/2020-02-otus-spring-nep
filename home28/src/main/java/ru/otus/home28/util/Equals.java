package ru.otus.home28.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * Хелперы для сравнения объектов, коллекций
 */
public class Equals {
    /**
     * Сравнивает коллекции, используя заданный предикат для объектов
     * null и == коллекций и элементов обрабатывает по стандартным правилам
     *
     * @return true, если коллекции одного размера и предикат вернул true для каждой пары
     */
    public static <T> boolean collections(Collection<T> a, Collection<T> b, BiPredicate<T, T> predicate) {
        if (a == b)
            return true;
        if (a == null || b == null)
            return false;
        if (a.size() != b.size())
            return false;

        var itA = a.iterator();
        var itB = b.iterator();
        while (itA.hasNext())
            if (!withPredicate(itA.next(), itB.next(), predicate))
                return false;

        return true;
    }

    /**
     * Сравнивает словари, используя заданный компаратор для значений (ключи сравнивает путем поиска элемента)
     *
     * @return true, если словари одного размера, все ключи первого найдены во втором и предикат вернул true для каждой значений
     */
    public static <K, V> boolean maps(Map<K, V> a, Map<K, V> b, BiPredicate<V, V> predicate) {
        if (a == b)
            return true;
        if (a == null || b == null)
            return false;
        if (a.size() != b.size())
            return false;

        for (var kvA : a.entrySet()) {
            var vB = b.get(kvA.getKey());
            if (!withPredicate(kvA.getValue(), vB, predicate))
                return false;
        }

        return true;
    }

    /**
     * Попарно сравнивает объекты (вызывая Objects.equals)
     *
     * @param objects массив из четного кол-ва объектов
     * @return true
     */
    public static boolean objects(Object... objects) {
        if (objects.length % 2 != 0)
            throw new IllegalArgumentException("objects length must be even");
        for (int i = 0; i < objects.length; i += 2)
            if (!Objects.equals(objects[i], objects[i + 1]))
                return false;
        return true;
    }

    /**
     * Сравнивает два объекта, используя заданный предикат.
     * Предварительно выполняет проверку на null и ==
     */
    public static <T> boolean withPredicate(T a, T b, BiPredicate<T, T> predicate) {
        if (a == b)
            return true;
        if (a == null || b == null)
            return false;

        return predicate.test(a, b);
    }
}

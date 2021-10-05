package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
//    return list.filterIndexed { index, _ -> index % 3 == 0 }.sum() - работает, но без withIndex()
    return list.withIndex().filter { it.index % 3 == 0 }.sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1)) { Pair(it.second, it.first + it.second) }.map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> {
    return this.customers.map { customer -> customer.city }.distinct().toSet()
}

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> =
    this.customers.map { customer -> customer.orders }.flatten().map { order -> order.products }.flatten().toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers.map { customer ->
    Pair(
        customer,
        customer.orders.count()
    )
}.maxByOrNull { it.second }!!.first

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = this.orders.map { it -> it.products }.flatten().maxByOrNull { it.price }!!

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    this.customers.groupBy { it.city }.mapValues { it.value.flatMap { it.orders }.filter { it.isDelivered }.flatMap { it.products }.count() }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> = emptyMap()

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> = emptySet()


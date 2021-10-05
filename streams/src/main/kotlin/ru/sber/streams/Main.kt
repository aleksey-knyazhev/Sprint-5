package ru.sber.streams

import io.mockk.InternalPlatformDsl.toArray

fun main() {
    //products
    val milk = Product("Milk", 88.0)
    val bread = Product("Bread", 53.0)
    val water = Product("Water", 19.0)
    val chocolate = Product("Chocolate", 129.0)
    val apple = Product("Apple", 9.0)
    val banana = Product("Banana", 19.0)
    val mango = Product("Mango", 99.0)
    val oil = Product("Oil", 99.0)
    val yogurt = Product("Yogurt", 99.0)
    val beef = Product("Beef", 149.0)
    val bag = Product("Bag", 1.0)

//cities
    val Moscow = City("Moscow")
    val Omsk = City("Omsk")
    val Tomsk = City("Tomsk")
    val Perm = City("Perm")
    val Tula = City("Tula")

    val shop = Shop("Product Shop", listOf(
        Customer("Alex", Moscow, listOf(
            Order(listOf(milk, bread, mango, oil)),
            Order(listOf(milk))
        )),
        Customer("Mari", Tula, listOf(
            Order(listOf(apple, banana), isDelivered = false)
        )),
        Customer("Pavel", Moscow, listOf(
            Order(listOf(beef, oil, milk))
        )),
        Customer("Anne", Omsk, listOf(
            Order(listOf(water, yogurt)),
            Order(listOf(beef), isDelivered = false)
        )),
        Customer("Andrew", Tomsk, listOf(
            Order(listOf(oil, bread, apple))
        )),
        Customer("Irina", Perm, listOf(
            Order(listOf(mango, banana)),
            Order(listOf(beef, bread)),
            Order(listOf(chocolate, oil, mango))
        ))
    ))




//    shop.customers.map { customer -> customer.city }.distinct().
    //shop.customers.map { customer -> Pair(customer, customer.orders.count()) }.sortedBy {it.second}.last().first.name
//    val customer1 = Customer("Ivan", Moscow, listOf(Order(listOf(milk, bread)), Order(listOf(beef))))
//    customer1.orders.map { it -> it.products }.flatten().sortedBy { it.price }.last()
    shop.customers.map { it -> Pair(it.city, it.orders.filter { it -> it.isDelivered }.map { it -> it.products.count() }) }
    shop.customers.flatMap { it.orders }.filter { it.isDelivered }.flatMap { it.products }
    shop.customers.groupBy { it.city }.mapValues { it.value.flatMap { it.orders }.filter { it.isDelivered }.flatMap { it.products }.count() }


    val a = 0

}
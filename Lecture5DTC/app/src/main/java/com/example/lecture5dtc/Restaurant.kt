package com.example.lecture5dtc

interface POSSystem {
    fun processOrder()
    fun calculateFoodInventory()
}

open class Restaurant:POSSystem {
    override fun processOrder() {
        print("Processing order...")
    }

    override fun calculateFoodInventory() {
        print("Calculating food inventory...")
    }

    fun reserveTable() {
        print("Reserving table...")
    }
}

class KrustyKrab:Restaurant() {
    fun prepareSpecialBurger() {
        print("Preparing special burger...")
    }
}

// the by keyword is used to delegate the implementation of the interface to the POS system
// it allows us to just implement the methods we need in the BikiniBottomCafe class
class BikiniBottomCafe(private val posSystem: POSSystem) :POSSystem by posSystem {
    // instead of inheriting/implementing from POSSystem, we delegate the implementation to the POS system
    override fun processOrder() {
        posSystem.processOrder()
    }

    // calculateFoodInventory() is not overridden, so it's delegated to the POS system

    fun processOnlineOrder() {
        print("Processing online order...")
    }
}
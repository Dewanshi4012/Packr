package com.example.packr.Data

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.packr.Constants.MyConstants
import com.example.packr.Database.RoomDB
import com.example.packr.Models.Items

class AppData(var database: RoomDB, var context: Context? = null) : Application() {

    companion object {
        const val LAST_VERSION = "LAST_VERSION"
        const val NEW_VERSION = 3
    }

    private var category: String = ""

    fun getBasicData(): List<Items> {
        category = "Basic Needs"
        return listOf(
            Items("Visa", category, false),
            Items("Passport", category, false),
            Items("Tickets", category, false),
            Items("Wallet", category, false),
            Items("Driving License", category, false),
            Items("Currency", category, false),
            Items("House Key", category, false),
            Items("Book", category, false),
            Items("Travel Pillow", category, false),
            Items("Eye Patch", category, false),
            Items("Umbrella", category, false),
            Items("Note Book", category, false)
        )
    }

    fun getPersonalCareData(): List<Items> {
        val data = arrayOf(
            "Tooth-brush", "Tooth-paste", "Floss", "Mouthwash", "Shaving Cream",
            "Razor Blade", "Soap", "Fiber", "Shampoo", "Hair Conditioner", "Brush", "Comb",
            "Hair Dryer", "Curling Iron", "Hair Moulder", "Hair Clip", "Moisturizer",
            "Lip Cream", "Contact Lens", "Perfume", "Deodorant", "Makeup Materials",
            "Makeup Remover", "Wet Wipes", "Pad", "Ear Stick", "Cotton", "Nail Polish",
            "Nail Polish Remover", "Tweezers", "Nail Scissors", "Nail Files", "Suntan Cream"
        )
        return prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE, data)
    }

    fun getClothingData(): List<Items> {
        val data = arrayOf(
            "Stockings", "Underwear", "Pajamas", "T-Shirts", "Casual Dress",
            "Evening Dress", "Shirt", "Cardigan", "Vest", "Jacket", "Skirt", "Trousers",
            "Jeans", "Shorts", "Suit", "Coat", "Rain Coat", "Glove", "Hat", "Scarf",
            "Bikini", "Belt", "Slipper", "Sneakers", "Casual Shoes", "Heeled Shoes",
            "Sports Wear"
        )
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE, data)
    }

    fun getBabyNeedsData(): List<Items> {
        val data = arrayOf(
            "Snapsuit", "Outfit", "Jumpsuit", "Baby Socks", "Baby Hat", "Baby Pyjamas",
            "Baby Bath Towel", "Muslin", "Blanket", "Dribble Bibs", "Baby Laundry Detergent",
            "Baby Bottles", "Baby Food Thermos", "Baby Bottle Brushes", "Brest-feeding Cover",
            "Breast Pump", "Water Bottle", "Storage Container", "Baby Food Spoon", "Highchairs",
            "Diaper", "Wet Wipes", "Baby Cotton", "Baby Care Cover", "Baby Shampoo", "Baby soap",
            "Baby Nail Scissors", "Body Moisturizer", "Potty", "Diaper Rash Cream", "Serum Physiological",
            "Nasal Aspirator", "Fly Repellent Lotion", "Pyrometer", "Antipyretic Syrup",
            "Probiotic Power", "Baby Backpack", "Stroller", "Baby Carrier", "Toys", "Teether",
            "Playpen", "Baby Radio", "Non-slip Sea Shoes", "Baby Sunglasses"
        )
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE, data)
    }

    fun getHealthData(): List<Items> {
        val data = arrayOf(
            "Aspirin", "Drugs Used", "Vitamins Used", "Lens Solutions", "Condom",
            "Hot Water Bag", "Tincture Of Lodine", "Adhesive Plaster", "First Aid Kit",
            "Replacement Lens", "Pain Reliever", "Fever Reducer", "Diarrhea Stopper",
            "Pain Relieve Spray"
        )
        return prepareItemsList(MyConstants.HEALTH_CAMEL_CASE, data)
    }

    fun getTechnologyData(): List<Items> {
        val data = arrayOf(
            "Mobile Phone", "Phone Cover", "E-Book Reader", "Camera", "Camera Charger",
            "Portable Speaker", "IPad (Tab)", "Headphone", "Laptop", "Laptop Charger", "Mouse",
            "Extension Cable", "Data Transfer Cable", "Battery", "Power Bank", "DVD Player",
            "Flash-Light", "MP3 Player", "MP3 Player Charger", "Voltage Adapter", "SD Card"
        )
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE, data)
    }

    fun getFoodData(): List<Items> {
        val data = arrayOf("Snack", "Sandwich", "Juice", "Tea Bags", "Coffee", "Water", "Thermos", "Chips", "Baby Food")
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE, data)
    }

    fun getBeachSuppliesData(): List<Items> {
        val data = arrayOf(
            "Sea Glasses", "Sea Bed", "Suntan Cream", "Beach Umbrella", "Swim Fins",
            "Beach Bag", "Beach Towel", "Beach Slippers", "Sunbed", "Snorkel", "Waterproof Clock"
        )
        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE, data)
    }

    fun getCarSuppliesData(): List<Items> {
        val data = arrayOf(
            "Pump", "Car Jack", "Spare Car Key", "Accident Record Set", "Auto Refrigerator",
            "Car Cover", "Car Charger", "Window Sun Shades"
        )
        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE, data)
    }

    fun getNeedsData(): List<Items> {
        val data = arrayOf(
            "Backpack", "Daily Bags", "Laundry Bag", "Sewing Kit", "Travel Lock",
            "Luggage Tag", "Magazine", "Sports Equipment", "Important Numbers"
        )
        return prepareItemsList(MyConstants.NEEDS_CAMEL_CASE, data)
    }

    private fun prepareItemsList(category: String, data: Array<String>): List<Items> {
        return data.map { Items(it, category, false) }
    }

    fun getAllData(): List<List<Items>> {
        return listOf(
            getBasicData(),
            getClothingData(),
            getPersonalCareData(),
            getBabyNeedsData(),
            getHealthData(),
            getTechnologyData(),
            getFoodData(),
            getBeachSuppliesData(),
            getCarSuppliesData(),
            getNeedsData()
        )
    }

    fun persistAllData() {
        val listOfAllItems = getAllData()
        listOfAllItems.forEach { list ->
            list.forEach { items ->
                database.mainDao().saveItem(items)
            }
        }
        println("Data Added")
    }

    fun persistDataByCategory(category: String, onlyDelete: Boolean) {
        try {
            val list = deleteAndGetListByCategory(category, onlyDelete)
            if (!onlyDelete) {
                list.forEach { item ->
                    database.mainDao().saveItem(item)
                }
            } else {
                Toast.makeText(context, "$category Reset Successfully", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteAndGetListByCategory(category: String, onlyDelete: Boolean): List<Items> {
        if (onlyDelete) {
            database.mainDao().deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL)
        } else {
            database.mainDao().deleteAllByCategory(category)
        }

        return when (category) {
            MyConstants.BASIC_NEEDS_CAMEL_CASE -> getBasicData()
            MyConstants.CLOTHING_CAMEL_CASE -> getClothingData()
            MyConstants.PERSONAL_CARE_CAMEL_CASE -> getPersonalCareData()
            MyConstants.BABY_NEEDS_CAMEL_CASE -> getBabyNeedsData()
            MyConstants.HEALTH_CAMEL_CASE -> getHealthData()
            MyConstants.TECHNOLOGY_CAMEL_CASE -> getTechnologyData()
            MyConstants.FOOD_CAMEL_CASE -> getFoodData()
            MyConstants.BEACH_SUPPLIES_CAMEL_CASE -> getBeachSuppliesData()
            MyConstants.CAR_SUPPLIES_CAMEL_CASE -> getCarSuppliesData()
            MyConstants.NEEDS_CAMEL_CASE -> getNeedsData()
            else -> emptyList()
        }
    }
}

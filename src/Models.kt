import kotlin.reflect.KClass

enum class Color : Attribute {
    PURPLE,
    PINK,
    RED,
    GREEN,
    YELLOW,
    BLUE,
    ORANGE,
    WHITE,
    BROWN,
    GREY
}

enum class Category {
    ;
    enum class Location : BaseCategory, Attribute {
        OUTDOOR, INDOOR
    }
    enum class Person : BaseCategory, Attribute {
        MALE, FEMALE
    }
    enum class Weapon : BaseCategory, Attribute {
        RANGED, CLOSED
    }
    interface BaseCategory
}

interface Attribute

abstract class Card(
    val color: Color,
    val category: Category.BaseCategory
) {
    fun <T: Any> hasCategory(clazz: KClass<T>): Boolean {
        return clazz == category::class
    }
    fun hasAttribute(value: Attribute) = color == value || category == value

    override fun toString() = "${this::class.simpleName} [$color, ${category::class.simpleName}:${category}]}"
}

// People
object Officer : Card(Color.PURPLE, Category.Person.MALE)
object Duke : Card(Color.PINK, Category.Person.MALE)
object Butcher : Card(Color.RED, Category.Person.MALE)
object Countess : Card(Color.GREEN, Category.Person.FEMALE)
object Nurse : Card(Color.YELLOW, Category.Person.FEMALE)
object Maid : Card(Color.BLUE, Category.Person.FEMALE)
object Dancer : Card(Color.ORANGE, Category.Person.FEMALE)

// Locations
object Parlor : Card(Color.PURPLE, Category.Location.INDOOR)
object Market : Card(Color.PINK, Category.Location.OUTDOOR)
object Library : Card(Color.RED, Category.Location.INDOOR)
object Park : Card(Color.GREEN, Category.Location.OUTDOOR)
object Museum : Card(Color.YELLOW, Category.Location.INDOOR)
object Harbor : Card(Color.BLUE, Category.Location.OUTDOOR)
object Theater : Card(Color.ORANGE, Category.Location.INDOOR)

// Weapons
object Knife : Card(Color.PURPLE, Category.Weapon.CLOSED)
object Crossbow : Card(Color.PINK, Category.Weapon.RANGED)
object Poison : Card(Color.RED, Category.Weapon.CLOSED)
object Sword : Card(Color.GREEN, Category.Weapon.CLOSED)
object Blowgun : Card(Color.YELLOW, Category.Weapon.RANGED)
object Rifle : Card(Color.BLUE, Category.Weapon.RANGED)
object Gun : Card(Color.ORANGE, Category.Weapon.RANGED)

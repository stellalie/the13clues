import kotlin.reflect.KClass

object ThreePlayerSetup: Setup {
    override val cards = setOf(
        // People
        Officer,
        Duke,
        Butcher,
        Countess,
        Nurse,
        Maid,
        Dancer,

        // Locations
        Parlor,
        Market,
        Library,
        Park,
        Museum,
        Harbor,
        Theater,

        // Weapons
        Knife,
        Crossbow,
        Poison,
        Sword,
        Blowgun,
        Rifle,
        Gun
    )
}

interface Setup {
    val cards: Set<Card>
}

fun Collection<Card>.filter(value: Attribute): Set<Card> {
    return this.filter { it.hasAttribute(value) }.toSet()
}

fun <T: Any> Collection<Card>.filter(clazz: KClass<T>): Set<Card> {
    return this.filter { it.hasCategory(clazz) }.toSet()
}
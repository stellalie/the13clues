
val SETUP = ThreePlayerSetup
val ALL_CARDS = SETUP.cards

class Player(
    val cards: Set<Card>
) {
    val possibleCardsOnHand: MutableSet<Card> = ALL_CARDS.toMutableSet()

    override fun toString() =
        "VISIBLE: " + cards.toString() +
            "\n" +
            "POSSIBLE ON HAND: " + possibleCardsOnHand.toString()
}

class Me (
    val cardsOnHand: Set<Card>
) {
    val possibleCardsOnFace: MutableSet<Card> = ALL_CARDS.toMutableSet()

    override fun toString() =
        "POSSIBLE SCENARIO: " +
            "\nPeople: " + possibleCardsOnFace.filter(Category.Person::class) +
            "\nLocation: " + possibleCardsOnFace.filter(Category.Location::class) +
            "\nWeapon: " + possibleCardsOnFace.filter(Category.Person::class) +
            "\n"
}

fun List<Player>.cards(): Set<Card> = this.fold(setOf()) { acc, it -> acc + it.cards }

class Board(val me: Me, val others: List<Player>) {
    init {
        // Remove what I see from my possible cards
        val allCardsSeen = others.cards() + me.cardsOnHand
        me.possibleCardsOnFace.removeAll(allCardsSeen)

        // Remove what I see from player's possible cards on hand
        others.forEach {
            it.possibleCardsOnHand.removeAll(allCardsSeen)
        }
    }

    fun useSecretInformant(cards: Set<Card>) {
        me.possibleCardsOnFace.removeAll(cards)
        others.forEach {
            it.possibleCardsOnHand.removeAll(cards)
        }
    }

    fun playerAccuse(player: Player, cards: Set<Card>) {
        player.possibleCardsOnHand.removeAll(cards)
    }

    fun playerSees(player: Player, attribute: Attribute, count: Int) {
        // Compare that with cards I've seen, except my secret hands and the opponent face cards
        val whatISee = others.cards()
            .minus(me.cardsOnHand)
            .minus(player.cards)
            .filter(attribute)

        // If the count of cards with the same attribute is the same with I saw,
        // then my card would not be of that attribute
        if (whatISee.size == count) {
            me.possibleCardsOnFace.removeIf { it.hasAttribute(attribute) }
        }

        // Otherwise he would probably see
        val possibleCardsSeen = ALL_CARDS
            .minus(me.cardsOnHand)
            .minus(others.cards())
            .filter(attribute)

        println("Possibly seen $possibleCardsSeen")
    }

    fun printBoardState() {
        println()
        println(me.cardsOnHand)
        println(me.possibleCardsOnFace)
        others.forEach {
            println()
            println(it)
        }
        println()
    }
}

fun main(args: Array<String>) {
    val board = Board(
        me = Me(cardsOnHand = setOf(Dancer, Rifle)),
        others = listOf(
            Player(cards = setOf(Maid, Library, Sword)),
            Player(cards = setOf(Officer, Theater, Crossbow))
        )
    )

    // Question time
    val Peggy = board.others[0]
    val Christina = board.others[1]

    // SI
    board.useSecretInformant(setOf(Knife, Countess, Market, Poison, Park, Harbor))

    // Accuse
    board.playerAccuse(Christina, setOf(Dancer, Theater, Crossbow))
    board.playerAccuse(Peggy, setOf(Duke, Library, Sword))
    board.playerAccuse(Peggy, setOf(Officer, Theater, Gun))

    // Question
    board.playerSees(Peggy, Category.Person.MALE, 2)
    board.playerSees(Christina, Color.PURPLE, 1)
    board.playerSees(Christina, Color.YELLOW, 2)
    board.playerSees(Christina, Category.Weapon.CLOSED, 2)
    board.playerSees(Peggy, Color.PINK, 1)
    board.playerSees(Christina, Color.RED, 2)
    board.playerSees(Peggy, Color.ORANGE, 2)
    board.playerSees(Christina, Color.PINK, 1)
    board.playerSees(Peggy, Color.BLUE, 0)
    board.playerSees(Christina, Color.GREEN, 1)
    board.playerSees(Christina, Color.RED, 2)
    board.playerSees(Peggy, Color.RED, 1)
    board.playerSees(Christina, Category.Weapon.RANGED, 1)
    board.playerSees(Christina, Category.Person.MALE, 2)
    board.playerSees(Peggy, Category.Person.MALE, 2)
    board.playerSees(Peggy, Color.YELLOW, 2)

    // Print state
    board.printBoardState()
}


fun main() {
    while(true) {
        println("1. Игра Камень-Ножницы-Бумага")
        println("2. Подсчет количества различных символов и расположить их в алфавитном порядке")
        println("0. Выход из приложения.")
        println("Введите пункт меню:")

        val point = readln()

        when (point) {
            "0" -> {
                println("Выход из приложения")
                return
            }
            "1" -> {
                playRockPaperScissors()
            }
            "2" -> {
                println("Введите строку: ")
                val inputString = readln()
                countSymbol(inputString)
            }
            else -> println("Введите корректный пункт меню")
        }
    }
}

fun playRockPaperScissors() {
    println("=== Игра Камень-Ножницы-Бумага ===")
    println("1 - Камень, 2 - Ножницы, 3 - Бумага")

    while (true) {
        println("\nВаш выбор (1-3):")
        when (val player = readln().toIntOrNull()) {
            1, 2, 3 -> {
                val computer = (1..3).random()
                val choices = listOf("Камень", "Ножницы", "Бумага")
                println("Вы: ${choices[player - 1]}")
                println("Компьютер: ${choices[computer - 1]}")

                if (player == computer) {
                    println("Ничья! Игра переигрывается.")
                    continue
                }

                val result = when {
                    player == 1 && computer == 2 -> "Вы победили! Камень затупляет ножницы."
                    player == 1 && computer == 3 -> "Компьютер победил! Бумага обёртывает камень."
                    player == 2 && computer == 1 -> "Компьютер победил! Камень затупляет ножницы."
                    player == 2 && computer == 3 -> "Вы победили! Ножницы разрезают бумагу."
                    player == 3 && computer == 1 -> "Вы победили! Бумага обёртывает камень."
                    player == 3 && computer == 2 -> "Компьютер победил! Ножницы разрезают бумагу."
                    else -> "Ошибка"
                }
                println(result)
                return
            }
            else -> println("Ошибка: введите 1, 2 или 3")
        }
    }
}

fun countSymbol(inputString: String) {
    val charCount = inputString.groupingBy { it }.eachCount()
    val sortedChars = charCount.toSortedMap()

    println("Различные символы в алфавитном порядке:")
    sortedChars.forEach { (char, count) ->
        println("'$char' - $count раз")
    }
}
fun rockPaperScissors() {
    println("=== Игра Камень-Ножницы-Бумага ===")
    println("1 - Камень, 2 - Ножницы, 3 - Бумага")

    while (true) {
        println("\nВаш выбор (1-3):")
        val playerInput = readln().toIntOrNull()

        if (playerInput in 1..3) {
            val computer = (1..3).random()
            val player = playerInput // Теперь player не nullable

            val choices = listOf("Камень", "Ножницы", "Бумага")
            if (player != null) {
                println("Вы: ${choices[player - 1]}")
            }
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
        } else {
            println("Ошибка: введите 1, 2 или 3")
        }
    }
}
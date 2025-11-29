fun main() {
    while(true) {
        println("1. Игра Камень-Ножницы-Бумага")
        println("2. Шифрование методом омофонической замены")
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
                homophonicCipher()
            }
            else -> {
                println("Неверный пункт меню")
            }
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

// Вероятности букв русского алфавита и пробела
val letterProbabilities = mapOf(
    'А' to 0.069, 'Б' to 0.013, 'В' to 0.038, 'Г' to 0.014,
    'Д' to 0.024, 'Е' to 0.071, 'Ё' to 0.071, 'Ж' to 0.007,
    'З' to 0.016, 'И' to 0.064, 'Й' to 0.010, 'К' to 0.029,
    'Л' to 0.039, 'М' to 0.027, 'Н' to 0.057, 'О' to 0.094,
    'П' to 0.026, 'Р' to 0.042, 'С' to 0.046, 'Т' to 0.054,
    'У' to 0.023, 'Ф' to 0.003, 'Х' to 0.008, 'Ц' to 0.005,
    'Ч' to 0.012, 'Ш' to 0.006, 'Щ' to 0.004, 'Ъ' to 0.001,
    'Ы' to 0.015, 'Ь' to 0.013, 'Э' to 0.002, 'Ю' to 0.005,
    'Я' to 0.017, ' ' to 0.146
)

fun homophonicCipher() {
    println("=== Шифрование методом омофонической замены ===")
    println("Введите сообщение для шифрования:")
    val message = readln().uppercase()

    // Создаем шифровальную таблицу
    val cipherTable = createCipherTable()

    // Шифруем сообщение
    val encryptedMessage = encryptMessage(message, cipherTable)

    // Выводим результаты
    println("\nИсходное сообщение: $message")
    println("Зашифрованное сообщение: $encryptedMessage")
    println("\nШифровальная таблица:")
    printCipherTable(cipherTable)

    // Демонстрация расшифровки
    val decryptedMessage = decryptMessage(encryptedMessage, cipherTable)
    println("Расшифрованное сообщение: $decryptedMessage")
}

fun createCipherTable(): Map<Char, List<String>> {
    val cipherTable = mutableMapOf<Char, MutableList<String>>()
    val allNumbers = (0..999).toMutableList()
    allNumbers.shuffle()

    var currentIndex = 0

    // Распределяем числа для каждого символа согласно вероятностям
    for ((char, probability) in letterProbabilities) {
        val count = (probability * 1000).toInt()
        val numbersForChar = mutableListOf<String>()

        for (i in 0 until count) {
            if (currentIndex < allNumbers.size) {
                val number = allNumbers[currentIndex]
                numbersForChar.add(String.format("%03d", number))
                currentIndex++
            }
        }

        cipherTable[char] = numbersForChar
    }

    return cipherTable
}

fun encryptMessage(message: String, cipherTable: Map<Char, List<String>>): String {
    val encryptedGroups = mutableListOf<String>()
    val usedIndices = mutableMapOf<Char, Int>()

    // Инициализируем счетчики использованных индексов для каждого символа
    for (char in cipherTable.keys) {
        usedIndices[char] = 0
    }

    for (char in message) {
        val availableCodes = cipherTable[char] ?: continue

        if (availableCodes.isNotEmpty()) {
            val currentIndex = usedIndices[char] ?: 0
            val code = availableCodes[currentIndex % availableCodes.size]
            encryptedGroups.add(code)

            // Увеличиваем индекс для следующего использования этого символа
            usedIndices[char] = currentIndex + 1
        }
    }

    return encryptedGroups.joinToString(" ")
}

fun decryptMessage(encryptedMessage: String, cipherTable: Map<Char, List<String>>): String {
    val decryptedChars = mutableListOf<Char>()
    val encryptedCodes = encryptedMessage.split(" ").filter { it.isNotBlank() }

    // Создаем обратное отображение: код -> символ
    val reverseTable = mutableMapOf<String, Char>()
    for ((char, codes) in cipherTable) {
        for (code in codes) {
            reverseTable[code] = char
        }
    }

    for (code in encryptedCodes) {
        val char = reverseTable[code]
        if (char != null) {
            decryptedChars.add(char)
        }
    }

    return decryptedChars.joinToString("")
}

fun printCipherTable(cipherTable: Map<Char, List<String>>) {
    println("Символ -> Коды шифрования")
    println("-".repeat(50))

    for (char in cipherTable.keys.sorted()) {
        val codes = cipherTable[char] ?: emptyList()
        if (codes.isNotEmpty()) {
            println("'$char': ${codes.joinToString(" ")}")
        }
    }
}
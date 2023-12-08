var glblN = 0

fun myReadInt() : Int {
    var exeptionMes = "Ошибка ввода: требуется ввести целое неотрицательное число. Попробуте снова!"
    while (true) {
        val str = readln()
        try {
            val res = str.toInt()
            if (res < 0) {
                println(exeptionMes)
                continue
            }
            return res
        } catch (e: Exception) {
            println(exeptionMes)
        }
    }
}

class Bank (
    var accounts: ArrayList<BankAccount>)
{
    class BankAccount (
        val num: Int,
        var name: String,
        var balance: Int)
    {
        fun print() {
            println("Счет #${num}. Имя: ${name}; баланс: ${balance}")
        }

        fun topUp(sum: Int) {
            balance += sum
        }
    }

    constructor() : this(arrayListOf())

    fun seeAccounts() {
        if (accounts.isEmpty()) {
            println("На текущий момент нет открытых счетов.")
        }
        for (acc in accounts) {
            acc.print()
        }
    }

    fun newAcc(name: String) {
        var acc = BankAccount(glblN++, name, 0)
        accounts.add(acc)
    }

    fun topUpAcc(num: Int, sum: Int) {
        if (num >= glblN) {
            val outOfRangeMes = "Ошибка: счета с таким номером не существует."
            println(outOfRangeMes)
            return
        }
        accounts[num].topUp(sum)
    }

    fun transferBetweenAcc(source: Int, recipient: Int, sum: Int) {
        if (source >= glblN) {
            val outOfRangeMes1 = "Ошибка: счета отправителя не существует."
            println(outOfRangeMes1)
            return
        }
        if (recipient >= glblN) {
            val outOfRangeMes2 = "Ошибка: счета получателя не существует."
            println(outOfRangeMes2)
            return
        }
        if (recipient == source) {
            val equalAccsMes = "Ошибка: счета получателя и отправителя совпадают."
            println(equalAccsMes)
            return
        }
        if (accounts[source].balance < sum) {
            val notEnoughMes = "Ошибка: на счету отправителя недостаточно средств."
            println(notEnoughMes)
            return
        }
        accounts[source].topUp(-sum)
        accounts[recipient].topUp(sum)
    }
}

fun main(args: Array<String>) {
    val welcomeMes = "Здравствуйте! Вас приветствует программа банка HSE Finances."
    println(welcomeMes)
    var bank = Bank()
    var command: Int
    val commandsMes1 = "Вам доступны следующие команды:\n1) Просмотр списка счетов\n2) Открытие счета\n3) Пополнение счета"
    val commandsMes2 = "4) Перевод денег между счетами\n5) Выход\nВведите номер команды:"
    do {
        println(commandsMes1)
        println(commandsMes2)
        command = myReadInt()
        when (command) {
            1 -> bank.seeAccounts()
            2 -> {
                val newAccMes = "Введите имя счета:"
                println(newAccMes)
                var name = readln()
                bank.newAcc(name)
            }
            3 -> {
                val topUpMes1 = "Введите номер счета:"
                println(topUpMes1)
                var num = myReadInt()
                val topUpMes2 = "Введите сумму, на которую Вы хотите пополнить счет:"
                println(topUpMes2)
                var sum = myReadInt()
                bank.topUpAcc(num, sum)
            }
            4 -> {
                val transferMes1 = "Введите номер счета отправителя:"
                println(transferMes1)
                var source = myReadInt()
                val transferMes2 = "Введите номер счета получателя:"
                println(transferMes2)
                var recipient = myReadInt()
                val transferMes3 = "Введите сумму, которую Вы хотите перевести между счетами:"
                println(transferMes3)
                var sum = myReadInt()
                bank.transferBetweenAcc(source, recipient, sum)
            }
            5 -> break;
            else -> println("Такой команды не существует, попробуйте снова!")
        }
    } while (true)
}

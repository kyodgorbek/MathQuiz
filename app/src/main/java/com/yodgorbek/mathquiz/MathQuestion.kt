package com.yodgorbek.mathquiz

data class MathQuestion(
    val question: String,
    val options: List<Int>,
    val correctAnswer: Int
)

fun generateQuestion(difficulty: Difficulty): MathQuestion {
    val range = when (difficulty) {
        Difficulty.EASY -> 1..10
        Difficulty.MEDIUM -> 10..50
        Difficulty.HARD -> 50..100
    }
    val a = range.random()
    val b = range.random()
    val correct = a + b
    val options = mutableSetOf(correct)
    while (options.size < 4) {
        options.add((correct - 10..correct + 10).random())
    }
    return MathQuestion(
        question = "$a + $b = ?",
        options = options.shuffled(),
        correctAnswer = correct
    )
}
package com.yodgorbek.mathquiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun MathQuizScreen(difficulty: Difficulty) {
    var currentQuestion by remember { mutableStateOf(generateQuestion(difficulty)) }
    var score by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(60) }
    var gameRunning by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val highScore by ScoreDataStore.getHighScore(context).collectAsState(initial = 0)

    // Countdown timer logic
    LaunchedEffect(gameRunning) {
        if (gameRunning) {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            gameRunning = false
            ScoreDataStore.saveHighScore(context, score)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (gameRunning) {
            // Quiz UI
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("⏱ Time Left: $timeLeft sec", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Text("🎯 Score: $score", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Text("🏆 High Score: $highScore", fontSize = 18.sp)
            }

            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Box(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = currentQuestion.question,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp) // <-- Added padding to lift buttons
            ) {
                currentQuestion.options.forEach { option ->
                    Button(
                        onClick = {
                            val isCorrect = option == currentQuestion.correctAnswer
                            if (isCorrect) score++
                            playSound(context, isCorrect)
                            currentQuestion = generateQuestion(difficulty)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(option.toString(), fontSize = 24.sp)
                    }
                }
            }
        } else {
            // Game Over UI
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("⏰ Time's up!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("Final Score: $score", fontSize = 24.sp, fontWeight = FontWeight.Medium)
                Text("🏆 High Score: $highScore", fontSize = 20.sp)

                Button(
                    onClick = {
                        score = 0
                        timeLeft = 60
                        currentQuestion = generateQuestion(difficulty)
                        gameRunning = true
                    },
                    modifier = Modifier
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Restart", fontSize = 18.sp)
                }
            }
        }

        // If you want to show AdMob banner later, you can place it here:
        // AdMobBanner(modifier = Modifier.padding(top = 24.dp))
    }
}

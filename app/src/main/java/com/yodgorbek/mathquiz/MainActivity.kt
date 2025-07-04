package com.yodgorbek.mathquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yodgorbek.mathquiz.ui.theme.MathQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedDifficulty by remember { mutableStateOf<Difficulty?>(null) }
            var darkTheme by remember { mutableStateOf(false) }

            val colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()

            MaterialTheme(colorScheme = colorScheme) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text("Dark Mode", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Switch(
                                checked = darkTheme,
                                onCheckedChange = { darkTheme = it }
                            )
                        }
                        if (selectedDifficulty == null) {
                            DifficultySelectionScreen { selectedDifficulty = it }
                        } else {
                            MathQuizScreen(difficulty = selectedDifficulty!!)
                        }
                    }
                }
            }
        }
    }
}

package com.yodgorbek.mathquiz

import android.content.Context
import android.media.MediaPlayer

fun playSound(context: Context, isCorrect: Boolean) {
    val soundRes = if (isCorrect) R.raw.correct else R.raw.wrong
    val player = MediaPlayer.create(context, soundRes)
    player.setOnCompletionListener { mp -> mp.release() } // <- Correct
    player.start()
}

package uz.gita.a2048

import android.media.MediaPlayer

object MusicPlayer{
    private val mediaPlayer: MediaPlayer = MediaPlayer.create(App.context, R.raw.item_click)
    fun playMusic(){
        mediaPlayer.start()
    }
    fun pauseMusic(){
        mediaPlayer.pause()
    }
}
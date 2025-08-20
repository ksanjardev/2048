package uz.gita.a2048

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    private val views = ArrayList<AppCompatTextView>(16)
    private lateinit var container: LinearLayout
    private val repository = AppRepository()
    private lateinit var matrix: Array<Array<Int>>
    private lateinit var txtScore: TextView
    private lateinit var txtHighScore: TextView
    private lateinit var restartGame: ImageButton
    private var score: Int? = null
    private var gameState: Boolean = false
    private lateinit var music: ImageButton
    private var musicState = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val window = window
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = Color.WHITE

        loadViews()
        newOrContinue()
        clickListeners()
    }

    private fun updateMusicDisplay() {
        if (musicState) {
            musicState = false
            music.setImageResource(R.drawable.sound_off)
        } else {
            musicState = true
            music.setImageResource(R.drawable.sound_on)
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun clickListeners() {


        restartGame.setOnClickListener {
            restartDialog()
        }

        findViewById<ImageButton>(R.id.music).setOnClickListener {
            updateMusicDisplay()
        }

        findViewById<ImageButton>(R.id.goHome).setOnClickListener {
            homeDialog()
        }

        val myTouchListener = MyTouchListener(this)
        myTouchListener.setDetectMotionDirectionListener { side ->

            when (side) {
                SideEnum.UP -> {
                    repository.moveUpDirection()
                    if (musicState) {
                        val media: MediaPlayer = MediaPlayer.create(this, R.raw.item_click)
                        media.start()
                        media.setOnCompletionListener {
                            media.reset()
                            media.release()
                        }
                    }

                }

                SideEnum.DOWN -> {
                    repository.moveDownDirection()
                    if (musicState) {
                        val media: MediaPlayer = MediaPlayer.create(this, R.raw.item_click)
                        media.start()
                        media.setOnCompletionListener {
                            media.reset()
                            media.release()
                        }
                    }
                }

                SideEnum.LEFT -> {
                    repository.moveLeftDirection()
                    if (musicState) {
                        val media: MediaPlayer = MediaPlayer.create(this, R.raw.item_click)
                        media.start()
                        media.setOnCompletionListener {
                            media.reset()
                            media.release()
                        }
                    }
                }

                SideEnum.RIGHT -> {
                    repository.moveRightDirection()
                    if (musicState) {
                        val media: MediaPlayer = MediaPlayer.create(this, R.raw.item_click)
                        media.start()
                        media.setOnCompletionListener {
                            media.reset()
                            media.release()
                        }
                    }
                }
            }

            showMatrix()
            if (!repository.checkFinish()) gameOverDialog()
        }
        findViewById<ConstraintLayout>(R.id.main).setOnTouchListener(myTouchListener)
    }

    private fun gameOverDialog() {
        val dialogView = layoutInflater.inflate(R.layout.game_over_dialog, null)
        var alertDialog = AlertDialog.Builder(this).setView(dialogView)
        val dialog = alertDialog.create()
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialogView.findViewById<ImageButton>(R.id.gameOverHome).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
            dialog.dismiss()
        }
        dialogView.findViewById<ImageButton>(R.id.gameOverRestart).setOnClickListener {
            restartGame()
            dialog.dismiss()
        }
        dialogView.findViewById<TextView>(R.id.gameOverScore).text = score.toString()
        dialogView.findViewById<TextView>(R.id.gameOverRecord).text = txtHighScore.text

        dialog.show()
    }

    private fun setScoreDefault() {
        score = 0
        txtScore.text = score.toString()
    }

    private fun homeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.home_dialog, null)
        var alertDialog = AlertDialog.Builder(this).setView(dialogView)
        var dialog = alertDialog.create()
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialogView.findViewById<AppCompatButton>(R.id.yesGoHome).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
            dialog.dismiss()
        }
        dialogView.findViewById<AppCompatButton>(R.id.noGoHome)
            .setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun setScore() {
        score = repository.score()
        txtScore.text = score.toString()
        if (score!! > txtHighScore.text.toString().toInt()) {
            txtHighScore.text = txtScore.text
        } else {
            txtHighScore.text = txtHighScore.text
        }
    }

    private fun restartGame() {
        gameState = true
        repository.restart()
        setScoreDefault()
        showMatrix()
    }

    private fun restartDialog() {
        val dialogView = layoutInflater.inflate(R.layout.restart_dialog, null)
        val alertDialog = AlertDialog.Builder(this).setView(dialogView).setCancelable(true)
        val dialog = alertDialog.create()
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val yesBtn = dialogView.findViewById<AppCompatButton>(R.id.yesButton)
        val noBtn = dialogView.findViewById<AppCompatButton>(R.id.noButton)
        yesBtn?.setOnClickListener {
            restartGame()
            dialog.dismiss()
        }
        noBtn?.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun newOrContinue() {
        if (gameState) {
            restartGame()
            showMatrix()
        } else {
            continueGame()
        }
    }

    private fun continueGame() {
        gameState = false

        repository.setMatrix(LocalStorage.getNumbers())
        Log.d("Score", "continueGame: ${LocalStorage.getScore()}")
        matrix = repository.getMatrix()

        score = LocalStorage.getScore()
        repository.setScore(score!!)
        txtScore.text = score.toString()
        txtHighScore.text = LocalStorage.getRecord().toString()

        showMatrix()
    }

    override fun onPause() {
        super.onPause()
        LocalStorage.saveNumbers(matrix)
        LocalStorage.saveRecord(txtHighScore.text.toString().toInt())
        LocalStorage.saveScore(score!!)
        Log.d("SCORE", "onPause: $gameState")
    }

    private fun loadViews() {
        music = findViewById(R.id.music)
        restartGame = findViewById(R.id.restartGame)
        txtScore = findViewById(R.id.txtScore)
        txtHighScore = findViewById(R.id.txtHighScore)

        container = findViewById(R.id.item_container)
        for (i in 0 until container.childCount) {
            val linear = container.getChildAt(i) as LinearLayout
            for (j in 0 until linear.childCount) {
                views.add(linear.getChildAt(j) as AppCompatTextView)
            }
        }
    }

    private fun showMatrix() {
        matrix = repository.getMatrix()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                views[4 * i + j].text = if (matrix[i][j] != 0) matrix[i][j].toString() else ""
                views[4 * i + j].setBackgroundResource(matrix[i][j].getBackgroundByAmount())
                setScore()

            }
        }
    }
}
package uz.gita.a2048

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Color
import android.graphics.Shader
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

@SuppressLint("CustomSplashScreen")
class SplashScreen: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        enableEdgeToEdge()
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT

        object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                val intent = Intent(this@SplashScreen, MenuActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
        textEffect()

    }
    private fun textEffect(){
        val textView = findViewById<TextView>(R.id.txtSplash)
        val bitMap = BitmapFactory.decodeResource(resources, R.drawable.img_1)
        val shader = BitmapShader(bitMap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        textView.paint.shader = shader
    }
}
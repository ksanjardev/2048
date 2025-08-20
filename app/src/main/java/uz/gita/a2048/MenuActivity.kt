package uz.gita.a2048

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Color
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.lottie.LottieAnimationView

class MenuActivity : AppCompatActivity() {
    private lateinit var playBtn: LottieAnimationView
    private lateinit var resumeBtn: AppCompatButton
    private val views = ArrayList<AppCompatTextView>(16)
    private lateinit var container: LinearLayout
    private val repository = AppRepository()
    private lateinit var matrix: Array<Array<Int>>
    private lateinit var txtScore: TextView
    private lateinit var txtHighScore: TextView
    private lateinit var restartGame: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_menu)

        enableEdgeToEdge()
        matrix = arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        )

//        val window = window
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = Color.TRANSPARENT


        playBtn = findViewById(R.id.playButton)
        playBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.infoButton).setOnClickListener { infoDialog() }
        textEffect()
    }
    private fun textEffect(){
        val textView = findViewById<TextView>(R.id.txtMenu)
        val bitMap = BitmapFactory.decodeResource(resources, R.drawable.img_1)
        val shader = BitmapShader(bitMap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        textView.paint.shader = shader
    }

    private fun infoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.info_laoyut, null)
        var alertDialog = AlertDialog.Builder(this).setView(dialogView)
        val dialog = alertDialog.create()
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialogView.findViewById<View>(R.id.telegram).setOnClickListener {
            val url = "https://t.me/Sanjarbek_Karimovv"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(url))
            startActivity(intent)
        }
        dialog.setOnShowListener {
            val width =
                (resources.displayMetrics.widthPixels * 0.9).toInt()
            val height =
                (resources.displayMetrics.widthPixels * 1.2).toInt()
            if (dialog.window != null) {
                dialog.window?.setLayout(width, height)
            }
        }
        dialogView.findViewById<View>(R.id.gmail).setOnClickListener {
            val intent =
                Intent(Intent.ACTION_SENDTO)
            intent.setData(Uri.parse("mailto:karimov.sanjar.dev@gmail.com"))
            startActivity(intent)
        }
        dialog.show()
    }

    private fun loadViews() {
        container = findViewById(R.id.item_container)
        for (i in 0 until container.childCount) {
            val linear = container.getChildAt(i) as LinearLayout
            for (j in 0 until linear.childCount) {
                views.add(linear.getChildAt(j) as AppCompatTextView)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        matrix = LocalStorage.getNumbers()
        loadViews()
        showMatrix()
    }

    private fun showMatrix() {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                views[4 * i + j].text = if (matrix[i][j] != 0) matrix[i][j].toString() else ""
                views[4 * i + j].setBackgroundResource(matrix[i][j].getBackgroundByAmount())
            }
        }
    }

}

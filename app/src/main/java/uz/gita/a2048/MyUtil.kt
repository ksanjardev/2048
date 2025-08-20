package uz.gita.a2048

fun Int.getBackgroundByAmount(): Int = when(this){
    0 -> R.drawable.button_bg
    2 -> R.drawable.bg_item2
    4 -> R.drawable.bg_item4
    8 -> R.drawable.bg_item8
    16 -> R.drawable.bg_item16
    32 -> R.drawable.bg_item32
    64 -> R.drawable.bg_item64
    128 -> R.drawable.bg_item128
    256 -> R.drawable.bg_item256
    512 -> R.drawable.bg_item512
    1024 -> R.drawable.bg_item1024
    else -> R.drawable.bg_item2048
}
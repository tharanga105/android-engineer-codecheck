package jp.co.yumemi.android.code_check.activity

import androidx.appcompat.app.AppCompatActivity
import jp.co.yumemi.android.code_check.R
import java.util.Date

class TopActivity : AppCompatActivity(R.layout.activity_top) {

    companion object {
        var lastSearchDate: Date? = null
    }
}

package com.wyq0918dev.flutter_mixed_example

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wyq0918dev.flutter_mixed.FlutterMixed
import com.wyq0918dev.flutter_mixed.MixedAware
import com.wyq0918dev.flutter_mixed.MixedSignals

class MainActivity : AppCompatActivity(), MixedAware {

    private var mMixedSignals: MixedSignals? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 启用边倒边
        enableEdgeToEdge()
        // 承载Flutter的控件
        val flutter: View = FlutterMixed.loadFlutter(
            activity = this@MainActivity,
            aware = this@MainActivity,
        )
        // 设置布局
        setContentView(R.layout.activity_main)
        // 设置安全区填充
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom,
            )
            return@setOnApplyWindowInsetsListener insets
        }
        // 设置应用栏
        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        // 添加Flutter控件
        findViewById<MaterialCardView>(R.id.card).apply {
            addView(flutter)
        }
        // 设置FAB点击事件
        findViewById<FloatingActionButton>(R.id.fab).apply {
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "我是View/ViewGroup的FAB",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    // 附加信号
    override fun onAttachSignals(signals: MixedSignals) {
        mMixedSignals = signals
    }

    // 销毁信号
    override fun onDetachSignals() {
        mMixedSignals = null
    }

    override fun onPostResume() {
        super.onPostResume()
        mMixedSignals?.onPostResume()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mMixedSignals?.onNewIntent(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mMixedSignals?.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mMixedSignals?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mMixedSignals?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onUserLeaveHint() {
        mMixedSignals?.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mMixedSignals?.onTrimMemory(level)
    }
}

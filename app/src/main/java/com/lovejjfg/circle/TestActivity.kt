package com.lovejjfg.circle

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Color
import android.media.AudioManager
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.lovejjfg.circle.view.TabActivity
import kotlinx.android.synthetic.main.activity_test.parent_container
import kotlinx.android.synthetic.main.activity_test.progressbar
import kotlinx.android.synthetic.main.content_test.test1



class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
//        setSupportActionBar(toolbar)
//        val viewStub = testViewStub

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//            testViewStub.visibility = if (testViewStub.visibility == View.VISIBLE) View.GONE else View.VISIBLE
//        }
//        testTag()
//
//        testInclude()


        var width = 1000
        testFactory()
        progressbar.setOnClickListener {
            //            it.invalidate()
            val layoutRequested = it.parent.isLayoutRequested
            println("parent layoutRequested:$layoutRequested")
//            progressbar.testCrash = true
            width += 20
            //            createSnapShot()
            progressbar.layoutParams.height = 800
            progressbar.layoutParams.width = width
            progressbar.requestLayout()
//            it.invalidate()
        }
        parent_container.setOnClickListener {
            width -= 20
            //            createSnapShot()
            parent_container.layoutParams.height = 800
            parent_container.layoutParams.width = width
            parent_container.requestLayout()
            startActivity(Intent(this, TabActivity::class.java))
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        println("TestActivity::dispatchTouchEvent...")
        return super.dispatchTouchEvent(ev)
    }

    private fun checkMode() {
        try {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as?AudioManager
            val mode = audioManager?.mode
            println("mode::$mode")
        } catch (e: Exception) {
            // ignore
        }
    }

    override fun onResume() {
        super.onResume()
        println("onResume。。。。。。。")
        checkMode()
    }

    private fun createSnapShot(view: View): Bitmap? {
        return try {
            val createSnapshot = ViewGroup::class.java.getDeclaredMethod(
                "createSnapshot",
                Config::class.java,
                Int::class.java,
                Boolean::class.java
            )
            createSnapshot.isAccessible = true
            val invoke = createSnapshot.invoke(view, ARGB_8888, Color.WHITE, false)
            invoke as? Bitmap
        } catch (e: Exception) {
            Log.e("createSnapShot", "error: ", e)
            null
        }
    }

    private fun testFactory() {
        //        LayoutInflater.from(this).factory2 = object :Factory2{
        //            override fun onCreateView(parent: View?, name: String?, context: Context?, attrs: AttributeSet?): View? {
        //                println("self name:$name")
        //                return null
        //            }
        //
        //            override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
        //                println("self name:$name")
        //                return null
        //            }
        //        }
        println("factory2:${LayoutInflater.from(this).factory2}")
        println("factory:${LayoutInflater.from(this).factory}")
    }

//    override fun onCreateView(parent: View?, name: String?, context: Context?, attrs: AttributeSet?): View {
//        println("name:$name")
//        return super.onCreateView(parent, name, context, attrs)
//    }

    private fun testInclude() {
        //test include mul ids
        //        findViewById<View>(R.id.includeContainer).setBackgroundColor(Color.RED)
        //        findViewById<View>(R.id.contentContainer).setBackgroundColor(Color.BLACK)
    }

    private fun testTag() {
        var tag: Any? = null
        var currentView: View = test1
        while (tag == null && currentView.parent != null) {
            currentView = currentView.parent as View
            tag = currentView.getTag(R.id.test1)
        }
        Log.e("TestActivity", "tag:$tag currentView tag:${currentView.tag} ")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //fixme
        Looper.myQueue().addIdleHandler {
            Log.e("IdleHandler", "start idel")
            true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        println("onDetachedFromWindow。。。。。。。")
    }
}

package com.lovejjfg.circle

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.lovejjfg.circle.R.id
import kotlinx.android.synthetic.main.activity_test.fab
import kotlinx.android.synthetic.main.content_test.test1
import kotlinx.android.synthetic.main.content_test.testViewStub

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test1)
        setContentView(R.layout.activity_test)
//        setSupportActionBar(toolbar)
//        val viewStub = testViewStub

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            testViewStub.visibility = if (testViewStub.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
//        testTag()
//
//        testInclude()
        testFactory()
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
            tag = currentView.getTag(id.test1)
        }
        Log.e("TestActivity", "tag:$tag currentView tag:${currentView.tag} ")
    }
}

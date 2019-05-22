/*
 * MainActivity.kt
 * exemplo_dragdrop
 *
 * Created by Flavio Campos on 21/05/19 21:58
 * Copyright © 2019 BuildBox. All rights reserved.
 */

package br.exemplodragdrop

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img1.setOnLongClickListener(MyOnLongClickListener())
        img2.setOnLongClickListener(MyOnLongClickListener())
        img3.setOnLongClickListener(MyOnLongClickListener())
        img4.setOnLongClickListener(MyOnLongClickListener())

        topleft.setOnDragListener(MyOnDragListener(1))
        topright.setOnDragListener(MyOnDragListener(2))
        bottomleft.setOnDragListener(MyOnDragListener(3))
        bottomright.setOnDragListener(MyOnDragListener(4))
    }


    internal inner class MyOnLongClickListener : View.OnLongClickListener {
        override fun onLongClick(v: View): Boolean {
            val data = ClipData.newPlainText("simple_text", "text")
            val sb = View.DragShadowBuilder(findViewById(R.id.shadow))
            v.startDrag(data, sb, v, 0)
            v.visibility = View.INVISIBLE
            return true
        }
    }

    internal inner class MyOnDragListener(private val num: Int) : View.OnDragListener {

        override fun onDrag(v: View, event: DragEvent): Boolean {
            val action = event.action

            when (action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    Log.i("Script", "$num - ACTION_DRAG_STARTED")
                    return if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        true
                    } else false
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    Log.i("Script", "$num - ACTION_DRAG_ENTERED")
                    v.setBackgroundColor(Color.YELLOW)
                }
                DragEvent.ACTION_DRAG_LOCATION -> Log.i("Script", "$num - ACTION_DRAG_LOCATION")
                DragEvent.ACTION_DRAG_EXITED -> {
                    Log.i("Script", "$num - ACTION_DRAG_EXITED")
                    v.setBackgroundColor(Color.BLUE)
                }
                DragEvent.ACTION_DROP -> {
                    Log.i("Script", "$num - ACTION_DROP")
                    val view = event.localState as View
                    val owner = view.parent as ViewGroup
                    owner.removeView(view)
                    val container = v as LinearLayout
                    container.addView(view)
                    view.visibility = View.VISIBLE
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    Log.i("Script", "$num - ACTION_DRAG_ENDED")
                    v.setBackgroundColor(Color.BLUE)
                }
            }

            return true
        }
    }

}
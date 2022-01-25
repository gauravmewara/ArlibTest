package com.arlib.arlibtest.common.utils

import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arlib.arlibtest.R

class MyRecyclerDecorator(
    private val headerOffset:Int,
    private val isSticky:Boolean,
    private val listener: DecoratorListener
) : RecyclerView.ItemDecoration() {
    private var headerLayout: View? = null
    private var headerText: TextView?= null

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos: Int = parent.getChildAdapterPosition(view)
        if (listener.isHeader(pos)) {
            outRect.top = headerOffset
            /*if(pos==0){
                outRect.top = headerOffset
            }else {
                outRect.top = 100
            }*/
        }
    }
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (headerLayout == null) {
            headerLayout = inflateHeaderView(parent)
            headerText = headerLayout!!.findViewById<View>(R.id.tv_disease) as TextView
            fixLayoutSize(headerLayout, parent)
        }
        var previousHeader: CharSequence = ""
        for (i in 0 until parent.getChildCount()) {
            val child: View = parent.getChildAt(i)
            val position: Int = parent.getChildAdapterPosition(child)
            val title = listener.getHeaderText(position)
            headerText!!.text = title
            if (previousHeader != title || listener.isHeader(position)) {
                drawHeader(c, child, headerLayout,position)
                previousHeader = title
            }
        }
    }

    private fun fixLayoutSize(view: View?, parent: ViewGroup) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(
            parent.width,
            View.MeasureSpec.EXACTLY
        )
        val heightSpec = View.MeasureSpec.makeMeasureSpec(
            parent.height,
            View.MeasureSpec.UNSPECIFIED
        )
        val childWidth = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view!!.layoutParams.width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom ,
            view.layoutParams.height
        )
        view.measure(childWidth, childHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun drawHeader(c: Canvas, child: View, headerView: View?,position: Int) {
        c.save()
        if (isSticky) {
            c.translate(0f, Math.max(0, child.top - headerView!!.height).toFloat())
        } else {
            c.translate(0f, (child.top - headerView!!.height).toFloat())
        }
        headerView!!.draw(c)
        c.restore()
    }

    private fun inflateHeaderView(parent: RecyclerView): View {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.disease_item, parent, false)
    }
}

public interface DecoratorListener{
    fun isHeader(position: Int): Boolean
    fun getHeaderText(position: Int): String
}
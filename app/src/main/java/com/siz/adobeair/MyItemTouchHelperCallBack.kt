package com.siz.adobeair

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MyItemTouchHelperCallBack : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val form = viewHolder.adapterPosition
        val to = target.adapterPosition

        var isMove = false
//        //AlbumPanoramaAdapter拖拽的适配器
//        val adapter: AlbumPanoramaAdapter? = recyclerView.adapter as AlbumPanoramaAdapter?
//        try {
//            //adapter.getPanoramaDataBeanList()  拖拽的适配器里的数据源
//            if (adapter != null && adapter.getPanoramaDataBeanList().size() > 0) {
//                if (form < to) {
//                    //从上往下拖动，每滑动一个item，都将list中的item向下交换，向上滑同理。
//                    for (i in form until to) {
//                        Collections.swap(adapter.getPanoramaDataBeanList(), i, i + 1) //交换数据源两个数据的位置
//                    }
//                } else {
//                    for (i in form downTo to + 1) {
//                        Collections.swap(adapter.getPanoramaDataBeanList(), i, i - 1) //交换数据源两个数据的位置
//                    }
//                }
//
//                // adapter.notifyDataSetChanged();//这个方法也可解决拖动后item边距错位问题，但是会丢失拖动动画效果
//                adapter.notifyItemMoved(form, to)
//                isMove = true
//            }
//        } catch (e: Exception) {
//        }
        return isMove
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }
}
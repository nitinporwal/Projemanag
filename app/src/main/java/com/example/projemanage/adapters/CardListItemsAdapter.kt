package com.example.projemanage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projemanage.R
import com.example.projemanage.models.Card
import kotlinx.android.synthetic.main.item_card.view.*

class CardListItemsAdapter(private val context: Context, private val list: ArrayList<Card>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if(holder is MyViewHolder) {
            holder.itemView.tv_card_name.text = model.name

            holder.itemView.setOnClickListener {
                if(onClickListener!=null) {
                    onClickListener!!.onClick(position)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener=onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

}
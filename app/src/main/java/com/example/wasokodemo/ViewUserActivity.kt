package com.example.wasokodemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wasokodemo.room.Users


class ViewUserActivity (val listener: RowClickListener): RecyclerView.Adapter<ViewUserActivity.MyViewHolder>() {

    var items = ArrayList<Users>()

    fun setListData(data: ArrayList<Users>){
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewUserActivity.MyViewHolder {
      val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false)
        return MyViewHolder(inflater, listener)
    }

    override fun onBindViewHolder(holder: ViewUserActivity.MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            listener.onItemClickListener(items[position])
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
   class MyViewHolder (view: View, val listener: RowClickListener): RecyclerView.ViewHolder(view){

       val tvfName = itemView.findViewById<TextView>(R.id.tvfName)
       val tvlName = itemView.findViewById<TextView>(R.id.tvlName)
       val tvEmail = itemView.findViewById<TextView>(R.id.tvEmail)
       val EditUserID = itemView.findViewById<Button>(R.id.btnEditUserDetails )


        fun bind(data:Users){
            tvfName.text = data.firstname
            tvlName.text = data.lastname
            tvEmail.text = data.email

            EditUserID.setOnClickListener{
               listener.onEditUserClickListener(data)
            }
        }
    }

    interface RowClickListener{
        fun onEditUserClickListener (user:Users)
        fun onItemClickListener(user: Users)
    }
}
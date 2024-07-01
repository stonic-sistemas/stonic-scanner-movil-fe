package com.stonic.stonic_scanner_movil_fe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.stonic.stonic_scanner_movil_fe.Option
import com.stonic.stonic_scanner_movil_fe.R

class OptionsAdapter(private val options: List<Option>) :
    RecyclerView.Adapter<OptionsAdapter.OptionViewHolder>() {

    class OptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.result_option_icon)
        val button: Button = view.findViewById(R.id.result_option_buttom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result_option, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val option = options[position]
        holder.icon.setImageResource(option.iconResId)
        holder.button.text = option.text
        holder.button.setOnClickListener { option.action() }
    }

    override fun getItemCount(): Int = options.size
}
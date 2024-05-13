package com.micewine.emu.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.micewine.emu.Preferences
import com.micewine.emu.R
import com.micewine.emu.activities.LogAppOutput
import com.micewine.emu.models.SettingsList

class AdapterSettings(private val settingsList: List<SettingsList>, private val context: Context) :
    RecyclerView.Adapter<AdapterSettings.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.settings_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sList = settingsList[position]
        holder.settingsName.setText(sList.titleSettings)
        holder.settingsDescription.setText(sList.descriptionSettings)
        holder.imageSettings.setImageResource(sList.imageSettings)
    }

    override fun getItemCount(): Int {
        return settingsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val settingsName: TextView
        val settingsDescription: TextView
        val imageSettings: ImageView

        init {
            settingsName = itemView.findViewById(R.id.title_preferences_model)
            settingsDescription = itemView.findViewById(R.id.description_preferences_model)
            imageSettings = itemView.findViewById(R.id.set_img)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val settingsModel = settingsList[getAdapterPosition()]
            if (R.string.x11_preference_title == settingsModel.titleSettings) {
                val intent = Intent(context, Preferences::class.java)
                context.startActivity(intent)
            } else if (R.string.log_level_normal == settingsModel.titleSettings) {
                val intent = Intent(context, LogAppOutput::class.java)
                context.startActivity(intent)
            }
        }
    }
}
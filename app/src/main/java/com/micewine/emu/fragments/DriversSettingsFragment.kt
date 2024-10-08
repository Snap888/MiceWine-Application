package com.micewine.emu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.micewine.emu.R
import com.micewine.emu.activities.GeneralSettings.Companion.SELECTED_D3DX_RENDERER_KEY
import com.micewine.emu.activities.GeneralSettings.Companion.SELECTED_DRIVER_KEY
import com.micewine.emu.activities.GeneralSettings.Companion.SELECTED_DXVK_HUD_PRESET_KEY
import com.micewine.emu.activities.GeneralSettings.Companion.SELECTED_DXVK_KEY
import com.micewine.emu.activities.GeneralSettings.Companion.SELECTED_MESA_VK_WSI_PRESENT_MODE_KEY
import com.micewine.emu.activities.GeneralSettings.Companion.SELECTED_TU_DEBUG_PRESET_KEY
import com.micewine.emu.activities.GeneralSettings.Companion.SELECTED_VIRGL_PROFILE_KEY
import com.micewine.emu.activities.GeneralSettings.Companion.SELECTED_WINED3D_KEY
import com.micewine.emu.activities.GeneralSettings.Companion.SPINNER
import com.micewine.emu.activities.MainActivity.Companion.appRootDir
import com.micewine.emu.adapters.AdapterSettingsPreferences
import com.micewine.emu.adapters.AdapterSettingsPreferences.SettingsListSpinner
import java.io.File

class DriversSettingsFragment : Fragment() {
    private val settingsList: MutableList<SettingsListSpinner> = ArrayList()
    private var rootView: View? = null
    private var recyclerView: RecyclerView? = null
    private var layoutManager: GridLayoutManager? = null
    private val dxvkFolder: File = File("$appRootDir/wine-utils/DXVK")
    private val dxvkVersions: MutableList<String> = mutableListOf()
    private val wined3dFolder: File = File("$appRootDir/wine-utils/WineD3D")
    private val wined3dVersions: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_settings_model, container, false)
        recyclerView = rootView?.findViewById(R.id.recyclerViewSettingsModel)

        layoutManager = recyclerView?.layoutManager as GridLayoutManager?
        layoutManager?.spanCount = 1

        setAdapter()

        return rootView
    }

    private fun setAdapter() {
        recyclerView?.setAdapter(AdapterSettingsPreferences(settingsList, requireActivity()))

        settingsList.clear()
        dxvkVersions.clear()
        wined3dVersions.clear()

        dxvkFolder.listFiles()?.sorted()?.forEach {
            dxvkVersions.add(it.name)
        }

        wined3dFolder.listFiles()?.sorted()?.forEach {
            wined3dVersions.add(it.name)
        }

        addToAdapter(R.string.select_driver_title, R.string.null_description, arrayOf(
            "Turnip/Zink", "Android/Zink", "VirGL"),
            SPINNER, "Turnip/Zink", SELECTED_DRIVER_KEY)

        addToAdapter(R.string.select_d3dx_title, R.string.null_description, arrayOf(
            "DXVK", "WineD3D"),
            SPINNER, "DXVK", SELECTED_D3DX_RENDERER_KEY)

        addToAdapter(R.string.select_dxvk_title, R.string.null_description, dxvkVersions.toTypedArray(),
            SPINNER, "DXVK-1.10.3-async", SELECTED_DXVK_KEY)

        addToAdapter(R.string.select_dxvk_hud_preset_title, R.string.null_description, arrayOf(
            "Off", "FPS", "GPU Load", "FPS/GPU Load", "FPS/GPU Load/Dev Info"),
            SPINNER, "FPS/GPU Load", SELECTED_DXVK_HUD_PRESET_KEY)

        addToAdapter(R.string.mesa_vk_wsi_present_mode_title, R.string.null_description, arrayOf(
            "fifo", "relaxed", "mailbox", "immediate"),
            SPINNER, "mailbox", SELECTED_MESA_VK_WSI_PRESENT_MODE_KEY)

        addToAdapter(R.string.tu_debug_title, R.string.null_description, arrayOf(
            "noconform", "noconform,flushall", "noconform,flushall,syncdraw", "noconform,syncdraw"),
            SPINNER, "mailbox", SELECTED_TU_DEBUG_PRESET_KEY)

        addToAdapter(R.string.select_wined3d_title, R.string.null_description, wined3dVersions.toTypedArray(),
            SPINNER, "WineD3D-9.12", SELECTED_WINED3D_KEY)

        addToAdapter(R.string.select_virgl_profile_title, R.string.null_description, arrayOf(
            "GL 2.1", "GL 3.3"),
            SPINNER, "GL 3.3", SELECTED_VIRGL_PROFILE_KEY)
    }

    private fun addToAdapter(titleId: Int, descriptionId: Int, valuesArray: Array<String>, type: Int, defaultValue: String, keyId: String) {
        settingsList.add(SettingsListSpinner(titleId, descriptionId, valuesArray, type, defaultValue, keyId))
    }
}

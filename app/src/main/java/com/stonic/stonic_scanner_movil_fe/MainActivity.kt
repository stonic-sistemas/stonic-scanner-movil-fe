package com.stonic.stonic_scanner_movil_fe

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.stonic.stonic_scanner_movil_fe.adapter.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val PREFS_NAME = "scanner_prefs"
        const val KEY_SELECTED_TAB = "selected_tab"
    }

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Resume scanning in BarcodeScannerFragment if needed
        } else {
            // Handle permission denial
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        tabLayout = findViewById(R.id.main_tablay_options)
        viewPager = findViewById(R.id.main_viepag_fragment)
        setupViewPager(viewPager)

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        // Restaurar la pestaña seleccionada
        val selectedTab = sharedPreferences.getInt(KEY_SELECTED_TAB, 0)
        viewPager.currentItem = selectedTab

        // Configurar listener para guardar la pestaña seleccionada
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val editor = sharedPreferences.edit()
                editor.putInt(KEY_SELECTED_TAB, tab.position)
                editor.apply()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // No action needed
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // No action needed
            }
        })

        // Inicializar otras partes de tu actividad según sea necesario
    }

    private fun setupViewPager(viewPager: ViewPager2) {
        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(QrScannerFragment(), getString(R.string.option_qrscanner))
        adapter.addFragment(BarcodeScannerFragment(), getString(R.string.option_barcodescanner))
        adapter.addFragment(TextScannerFragment(), getString(R.string.option_textscanner))
        viewPager.adapter = adapter

        val tabIcons = arrayOf(
            R.drawable.ic_qr,
            R.drawable.ic_barcode,
            R.drawable.ic_text
        )

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
            tab.setIcon(tabIcons[position])
        }.attach()
    }

}
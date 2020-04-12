package com.morris.concepcionapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.morris.concepcionapp.ui.main.GeneralActivity
import com.morris.concepcionapp.ui.main.adapters.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarInicio)

        configurarTabLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.button_comerciante -> {

                //val intent = Intent(this, ComercianteActivity::class.java)

                val intent = Intent(this, GeneralActivity::class.java)
                intent.putExtra("FrangmentName", "ComercianteFragment")

                startActivity(intent)

                true
            }
            R.id.button_reportar_error -> {

                val intent = Intent(this, GeneralActivity::class.java)
                intent.putExtra("FrangmentName", "ReporteErrorFragment")

                startActivity(intent)

                true
            }
            else -> {

                val intent = Intent(this, GeneralActivity::class.java)
                intent.putExtra("FrangmentName", "InfoFragment")

                startActivity(intent)

                true
            }
        }
    }

    private fun configurarTabLayout() {

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )

        val viewPager = findViewById<ViewPager>(R.id.viewpager_main)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        viewPager.currentItem = 1
    }

}

package com.amebaownd.pikohan_nwiatori.knighttour

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class HowToPlayActivity() : AppCompatActivity() {

    lateinit var adapter: TabAdapter
    lateinit var tabLayout: TabLayout
    lateinit var tab: TabLayout.Tab
    private val TAB_SIZE = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_play)

        setTabLayout()

        findViewById<Button>(R.id.back_how_to_play_button)
            .setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        findViewById<Button>(R.id.next_button)
            .setOnClickListener {
                nextTab()
            }
        findViewById<Button>(R.id.previous_button)
            .setOnClickListener {
                previousTab()
            }
    }

    private fun setTabLayout() {
        adapter = TabAdapter(supportFragmentManager, this)
        tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        tab = tabLayout.getTabAt(0)!!
        tab.customView = adapter.getTabView(tabLayout, 0)
    }

    private fun nextTab() {
        if (tab.position < TAB_SIZE - 1) {
            tab = tabLayout.getTabAt(tab.position + 1)!!
            tab.customView = adapter.getTabView(tabLayout, tab.position + 1)
        }
    }

    private fun previousTab() {
        if (tab.position > 0) {
            tab = tabLayout.getTabAt(tab.position - 1)!!
            tab.customView = adapter.getTabView(tabLayout, tab.position - 1)
        }
    }
}
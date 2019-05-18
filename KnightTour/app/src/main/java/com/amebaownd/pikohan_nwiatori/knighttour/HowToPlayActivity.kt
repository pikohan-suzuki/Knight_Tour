package com.amebaownd.pikohan_nwiatori.knighttour

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TableLayout
import kotlinx.android.synthetic.main.activity_how_to_play.*

class HowToPlayActivity() : AppCompatActivity() {

    lateinit var adapter: TabAdapter
    private var currentPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_play)

        setTabLayout()

        findViewById<ImageButton>(R.id.back_how_to_play_button)
            .setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        findViewById<ImageButton>(R.id.next_button)
            .setOnClickListener {
                nextTab()
            }
        findViewById<ImageButton>(R.id.previous_button)
            .setOnClickListener {
                previousTab()
            }
        findViewById<ImageView>(R.id.red_tab_imageButton).setOnClickListener(setTabClickListener(findViewById<ImageView>(R.id.red_tab_imageButton)))
        findViewById<ImageView>(R.id.blue_tab_imageButton).setOnClickListener(setTabClickListener(findViewById<ImageView>(R.id.blue_tab_imageButton)))
        findViewById<ImageView>(R.id.green_tab_imageButton).setOnClickListener(setTabClickListener(findViewById<ImageView>(R.id.green_tab_imageButton)))
        findViewById<ImageView>(R.id.orange_tab_imageButton).setOnClickListener(setTabClickListener(findViewById<ImageView>(R.id.orange_tab_imageButton)))
    }

    private fun setTabLayout() {
        adapter = TabAdapter(supportFragmentManager, this)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
    }

    private fun nextTab() {
        if (currentPage < viewPager.childCount ) {
            currentPage += 1
            viewPager.setCurrentItem(currentPage)
        }
    }

    private fun previousTab() {
        if (currentPage > 0) {
            currentPage -= 1
            viewPager.setCurrentItem(currentPage)
        }
    }

    fun setTabClickListener(view: View): View.OnClickListener? {
        when (view.id) {
            R.id.red_tab_imageButton -> {
                return View.OnClickListener {
                    currentPage = 0
                    viewPager.setCurrentItem(currentPage)
                }
            }
            R.id.blue_tab_imageButton -> {
                return View.OnClickListener {
                    currentPage = 1
                    viewPager.setCurrentItem(currentPage)
                }
            }
            R.id.green_tab_imageButton -> {
                return View.OnClickListener {
                    currentPage = 2
                    viewPager.setCurrentItem(currentPage)
                }
            }
            R.id.orange_tab_imageButton -> {
                return View.OnClickListener {
                    currentPage = 3
                    viewPager.setCurrentItem(currentPage)
                }
            }
        }
        return null
    }
}
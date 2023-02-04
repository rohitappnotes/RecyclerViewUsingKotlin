package com.recyclerview.using.kotlin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.recyclerview.using.kotlin.adapter.ProductRecyclerViewAdapter
import com.recyclerview.using.kotlin.model.Product

class TestActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var productArrayList: ArrayList<Product> = ArrayList()
    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
        init()
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun init() {
        productArrayList = getData()
        setUpRecyclerView()

        productRecyclerViewAdapter.setItemClickListener(object :ProductRecyclerViewAdapter.ItemClickListener {
            override fun onClick(position: Int, view: View?, item: Product) {
                Toast.makeText(applicationContext, item.name, Toast.LENGTH_SHORT).show()
            }
        })

        productRecyclerViewAdapter.setItemLongClickListener(object : ProductRecyclerViewAdapter.ItemLongClickListener {
            override fun onLongClick(position: Int, view: View?, item: Product) {
                Toast.makeText(applicationContext, item.name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getData(): ArrayList<Product> {
        val items: ArrayList<Product> = ArrayList()
        items.add(Product("", "Samsung Galaxy M32", 12000))
        items.add(Product("", "TIMEX Analog Men's Watch", 250))
        items.add(Product("", "boAt Airdopes", 450))
        return items

        /*val items: ArrayList<Product> = ArrayList()
        for (i in 1..50) {
            items.add(Product("", "Samsung Galaxy M32 " + i, 12000))
        }
        return items*/
    }

    private fun setUpRecyclerView() {
       /* recyclerView.let {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            productRecyclerViewAdapter = ProductRecyclerViewAdapter(applicationContext, productArrayList)
            it.adapter = productRecyclerViewAdapter
        }*/

        recyclerView.isNestedScrollingEnabled = false

       /* val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager*/

        //val linearLayoutManager = LinearLayoutManager(this)
        //val gridLayoutManager = GridLayoutManager(this,2)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2,1)

        val gridLayoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = gridLayoutManager

        productRecyclerViewAdapter = ProductRecyclerViewAdapter(applicationContext, productArrayList)
        recyclerView.adapter = productRecyclerViewAdapter
    }
}
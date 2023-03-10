package com.recyclerview.using.kotlin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recyclerview.using.kotlin.adapter.ProductRecyclerViewAdapter
import com.recyclerview.using.kotlin.model.Product

class TestActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private var productArrayList: ArrayList<Product> = ArrayList()
    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
        init()
    }

    private fun initView() {
        searchEditText = findViewById(R.id.searchEditText)
        recyclerView = findViewById(R.id.recyclerView)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                productRecyclerViewAdapter.filter.filter(s)
            }
        })
    }

    private fun init() {
        productArrayList = getData()
        setUpRecyclerView()

        productRecyclerViewAdapter.setItemClickListener(object :ProductRecyclerViewAdapter.ItemClickListener {
            override fun onClick(position: Int, view: View?, item: Product) {
                Toast.makeText(applicationContext, item.name, Toast.LENGTH_SHORT).show()

                if (item.isSelected) {
                    item.isSelected = false
                    productRecyclerViewAdapter.checkCheckBox(position, item)
                } else {
                    item.isSelected = true
                    productRecyclerViewAdapter.checkCheckBox(position, item)
                }

                var commaSeparatedIds: String? = null
                var commaSeparatedNames: String? = null

                var selectedArrayList: ArrayList<Product> = ArrayList()
                for (i in 0 until productArrayList.size) {
                    if (productArrayList[i].isSelected) {
                        selectedArrayList.add(productArrayList.get(i))
                    }
                }

                if (selectedArrayList.size > 0) {
                    val idsStringBuilder = StringBuilder()
                    val namesStringBuilder = StringBuilder()
                    val lastIdx = selectedArrayList.size - 1
                    for (i in selectedArrayList.indices) {
                        val id: String? = selectedArrayList[i].id
                        val name = selectedArrayList[i].name
                        if (i == lastIdx) {
                            idsStringBuilder.append(id)
                            namesStringBuilder.append(name)
                        } else {
                            idsStringBuilder.append(id).append(",")
                            namesStringBuilder.append(name).append(",")
                        }
                    }

                    commaSeparatedIds = idsStringBuilder.toString()
                    commaSeparatedNames = namesStringBuilder.toString()
                }

                println("======ids======"+commaSeparatedIds)
                println("======Names======"+commaSeparatedNames)
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
        items.add(Product("1","", "Samsung Galaxy M32", 12000))
        items.add(Product("2","", "TIMEX Analog Men's Watch", 250))
        items.add(Product("3","", "boAt Airdopes", 450))
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

        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        //val linearLayoutManager = LinearLayoutManager(this)
        //val gridLayoutManager = GridLayoutManager(this,2)
        //val staggeredGridLayoutManager = StaggeredGridLayoutManager(2,1)

      /*  val gridLayoutManager = GridLayoutManager(this,2)
        recyclerView.layoutManager = gridLayoutManager*/

        productRecyclerViewAdapter = ProductRecyclerViewAdapter(applicationContext, productArrayList)
        recyclerView.adapter = productRecyclerViewAdapter
    }

    fun selectAll() {
        for (i in 0 until productArrayList.size) {
            productArrayList[i].isSelected = true
        }

        for (i in 0 until productRecyclerViewAdapter.items.size) {
            val item:Product = productRecyclerViewAdapter.items[i]
            item.isSelected = true
            productRecyclerViewAdapter.checkCheckBox(i, item)
        }
    }

    fun deselectAll() {
        for (i in 0 until productArrayList.size) {
            productArrayList[i].isSelected = true
        }

        for (i in 0 until productRecyclerViewAdapter.items.size) {
            val item:Product = productRecyclerViewAdapter.items[i]
            item.isSelected = false
            productRecyclerViewAdapter.checkCheckBox(i, item)
        }
    }
}
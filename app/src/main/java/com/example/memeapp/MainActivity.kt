package com.example.memeapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.time.toDuration


class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var button: Button
    lateinit var progress:ProgressBar
    lateinit var toggle:ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var button2: Button
    lateinit var appbar1:MaterialToolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView=findViewById(R.id.img1)
        button=findViewById(R.id.btn)
        progress=findViewById(R.id.progess)
        drawerLayout=findViewById(R.id.drawerlayout)
        button2=findViewById(R.id.btn10)
        navigationView=findViewById(R.id.nav_view)
        appbar1=findViewById(R.id.appbar)
        setupview()
        loadmeme()

        button.setOnClickListener(View.OnClickListener {
         progress.visibility = View.VISIBLE
         loadmeme()

     })
        button2.setOnClickListener(View.OnClickListener {
            val bitmap=getBitmapFromImageView(imageView)
            savememe(bitmap)

        })




    }

    private fun savememe(bitmap: Bitmap) {
        val saveimageurl=MediaStore.Images.Media.insertImage(contentResolver,bitmap,"Meme","Meme")
        val saveimageuri= Uri.parse(saveimageurl)
        Toast.makeText(this,"Meme Saved",Toast.LENGTH_SHORT).show()
    }

    private fun setupview() {
        setupdrawer()
    }

    private fun setupdrawer() {
        setSupportActionBar(appbar1)
        toggle=ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        toggle.syncState()
        drawerLayout.addDrawerListener(toggle)

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mitem1->Toast.makeText(this,"Home is selected",Toast.LENGTH_SHORT).show()
                R.id.mitem2->Toast.makeText(this,"Item is selected",Toast.LENGTH_SHORT).show()
                R.id.mitem3->Toast.makeText(this,"Rating  is selected",Toast.LENGTH_SHORT).show()
                R.id.mitem4->Toast.makeText(this," Favourite is selected",Toast.LENGTH_SHORT).show()
                R.id.mitem4->Toast.makeText(this," Setting is selected",Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    fun loadmeme() {
        Retrofitinstance.apiInterfaces.getMeme().enqueue(object : Callback<Mydata?> {
            override fun onResponse(call: Call<Mydata?>, response: Response<Mydata?>) {
                if (response.isSuccessful) {
                    val memeData = response.body()


                    //get the url of the image from the memedata
                    val imageurl = memeData?.preview?.get(2)

                    //glide
                    Glide.with(this@MainActivity).load(imageurl).into(imageView)
                    progress.visibility=View.GONE

                }
            }

            override fun onFailure(call: Call<Mydata?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: $t", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getBitmapFromImageView(imageView: ImageView):Bitmap{
        imageView.isDrawingCacheEnabled=true
        imageView.buildDrawingCache()
        val bitmap=(imageView.drawable as BitmapDrawable).bitmap
        imageView.isDrawingCacheEnabled=false
        return bitmap

    }
    }



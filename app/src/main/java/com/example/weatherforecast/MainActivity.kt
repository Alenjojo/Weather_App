package com.example.weatherforecast

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.displayscreen.*
import org.json.JSONException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
             var tempdesc:String="sunny"
    lateinit var tempcity:String
    lateinit var tempfeel:String
    lateinit var citylocation:String
    lateinit var citymin:String
    lateinit var citymax:String
    lateinit var citypressure:String
    lateinit var cityhumidity:String
    lateinit var citywind:String
    lateinit var citycloud:String
    lateinit var city:String
    lateinit var citylocation2:String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        city=intent.getStringExtra("city")
       // var state:String=intent.getStringExtra("state")
        citylocation2=city

       if(city!="") {
           citylocation=city
           json()
           setContentView(R.layout.displayscreen)
       }else {
           setContentView(R.layout.activity_main)
           btnlocation.setOnClickListener {
               citylocation = edtlocation.getText().toString()
               json()
               setContentView(R.layout.displayscreen)
           }
       }
    }
    private fun json(){
        println("zzz")
        val queue = Volley.newRequestQueue(this@MainActivity)
        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$citylocation&appid=a6f41d947e0542a26580bcd5c3fb90ef&units=metric"
        if (ConnectionManager().checkConnectivity(this@MainActivity)) {

            val jsonObjectRequest =
                @RequiresApi(Build.VERSION_CODES.O)
                object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {


                    try {
                        // progressLayout.visibility = View.GONE
                        val data = it.getJSONArray("weather")
                        val JsonObject = data.getJSONObject(0)
                        tempdesc = JsonObject.getString("description")

                        val data1 = it.getJSONObject("main")
                        val temp1:Double = data1.getString("temp").toDouble()
                        var temp2:Double = data1.getString("feels_like").toDouble()
                        var temp3:Double=data1.getString("temp_min").toDouble()
                        var temp4:Double=data1.getString("temp_max").toDouble()
                        var temp5:Double=data1.getString("pressure").toDouble()
                        var temp6:Double=data1.getString("humidity").toDouble()

                        var data2=it.getString("visibility").toInt()

                        var data3=it.getJSONObject("wind")
                        var wind:Double=data3.getString("speed").toDouble()

                        val data4=it.getJSONObject("clouds")
                        citycloud=data4.getString("all")



                        txt5.setText(citylocation)
                        tempcity=  temp1.roundToInt().toString()
                        txt1.setText(tempcity+"°C")
                        tempfeel=temp2.roundToInt().toString()
                        txt6.setText("Feels like:"+tempfeel+"°C")
                        citymin=temp3.roundToInt().toString()
                        txt4.setText(citymin+"°C")
                        citymax=temp4.roundToInt().toString()
                        txt3.setText(citymax+"°C")
                        citypressure=temp5.roundToInt().toString()
                        txt9.setText("Pressure:"+citypressure+"mb")
                        cityhumidity=temp6.roundToInt().toString()
                        txt10.setText("Humidity:"+tempfeel+"%")
                        txt2.setText(tempdesc)
                        var visi=data2/1000
                        txt11.setText("Visibility:"+visi+"Km")
                        citywind=wind.roundToInt().toString()
                        txt7.setText("Wind:"+citywind+"km/h")
                        txt8.setText("Cloud:"+citywind+"%")


                    } catch (e: JSONException) {
                        Toast.makeText(
                            this@MainActivity,
                            "Some unexpected error occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    val c= tempdesc.toUpperCase()

                    val current=LocalDateTime.now()
                    val currentt=DateTimeFormatter.ofPattern("HH")
                    val time=current.format(currentt)

                    if(time>="06"&&time<="18") {


                        when (c) {
                            "CLEAR SKY" -> img1.setImageResource(R.drawable.sun)
                            "SCATTERED CLOUDS" -> img1.setImageResource(R.drawable.sunnynightscatteredcloud)
                            "HAZE" -> img1.setImageResource(R.drawable.sunnyhaze)
                            "BROKEN CLOUDS" -> img1.setImageResource(R.drawable.sunnynightscatteredcloud)
                            "LIGHT RAIN" -> img1.setImageResource(R.drawable.sunnynightlightrain)
                            "OVERCAST CLOUDS" -> img1.setImageResource(R.drawable.sunnyovercastclouds)
                            "FEW CLOUDS" -> img1.setImageResource(R.drawable.sunnyfewclouds)
                            "MIST" -> img1.setImageResource(R.drawable.sunnymist)
                            "LIGHT INTENSITY DRIZZLE" -> img1.setImageResource(R.drawable.sunnynightlightintensitydrizzle)
                            "HEAVY RAIN" -> img1.setImageResource(R.drawable.sunnynightheavyrain)
                            "SNOW" -> img1.setImageResource(R.drawable.sunnynightsnow)
                        }
                    }else{
                        when (c) {
                            "CLEAR SKY" -> img1.setImageResource(R.drawable.nightclearsky)
                            "SCATTERED CLOUDS" -> img1.setImageResource(R.drawable.sunnynightscatteredcloud)
                            "HAZE" -> img1.setImageResource(R.drawable.nighthaze)
                            "BROKEN CLOUDS" -> img1.setImageResource(R.drawable.sunnynightscatteredcloud)
                            "LIGHT RAIN" -> img1.setImageResource(R.drawable.sunnynightlightrain)
                            "OVERCAST CLOUDS" -> img1.setImageResource(R.drawable.nightfewcloudsovercast)
                            "FEW CLOUDS" -> img1.setImageResource(R.drawable.nightfewcloudsovercast)
                            "MIST" -> img1.setImageResource(R.drawable.nightmist)
                            "LIGHT INTENSITY DRIZZLE" -> img1.setImageResource(R.drawable.sunnynightlightintensitydrizzle)
                            "HEAVY RAIN" -> img1.setImageResource(R.drawable.sunnynightheavyrain)
                            "SNOW" -> img1.setImageResource(R.drawable.sunnynightsnow)
                        }
                    }



                }, Response.ErrorListener {
                    //Here we will handle the errors
                        Toast.makeText(this@MainActivity, "Some error occurred!", Toast.LENGTH_SHORT)
                            .show()

                }) {
                    /*   override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        // headers["Content-type"] = "application/json"
                        // headers["token"] = "9bf534118365f1"
                        headers["q"] = "Thrissur"
                        headers["appid"] = "a6f41d947e0542a26580bcd5c3fb90ef"
                        headers["units"] = "metric"
                        return headers
                    }*/
                }


            queue.add(jsonObjectRequest)

        } else {
            val dialog = AlertDialog.Builder(this@MainActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open Settings") { _, _ ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
            }

            dialog.setNegativeButton("Exit") { _, _ ->
                ActivityCompat.finishAffinity(this@MainActivity)
            }
            dialog.create()
            dialog.show()
        }
    }
    /* override fun onBackPressed() {
            setContentView(R.layout.activity_main)

            btnlocation.setOnClickListener {
                citylocation2 = edtlocation.getText().toString()
                json()
                setContentView(R.layout.displayscreen)
            }
      //super.onBackPressed()
        } */


}


package com.tphtwe.myapplication

import android.net.Uri.parse
import android.net.UrlQuerySanitizer
import android.os.AsyncTask
import android.os.Bundle
import android.util.JsonReader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONStringer
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpCookie.parse
import java.net.HttpURLConnection
import java.net.URL
import java.time.Duration.parse
import java.util.Locale.LanguageRange.parse
import java.util.logging.Level.parse
import javax.security.auth.callback.Callback


class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trans.setOnClickListener {

//            lifecycleScope.launch {
//                val result = httpGet("http://hmkcode-api.appspot.com/rest/api/hello/Android")
//                transTxt.text = result
//            }

            CoroutineScope(Dispatchers.IO).launch {
                var res=httpGet("https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl=en&dt=t&q=hello")
//                var res=httpGet("http://hmkcode-api.appspot.com/rest/api/hello/Android")
//                var res=httpGet("https://my-json-server.typicode.com/typicode/demo/posts?id=1&title=Post 1")

                withContext(Dispatchers.Main){
                    transTxt.text=res
                }
            }
        }


        nxt.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragment_to_blankFragment2)
        }
    }


    private fun httpGet(myURL: String?): String {

        val inputStream:InputStream
        val result:String

        // create URL
        val url:URL = URL(myURL)

        // create HttpURLConnection
        val conn:HttpURLConnection = url.openConnection() as HttpURLConnection

        // make GET request to the given URL
        conn.connect()

        // receive response as inputStream
        inputStream = conn.inputStream

        // convert inputstream to string
        if(inputStream != null)
            result = readStream(inputStream,1000000000)
        else
            result = "Did not work!"

        return result
    }
    fun readStream(stream: InputStream, maxReadSize: Int): String {
        val reader: Reader? = InputStreamReader(stream, "UTF-8")
        val rawBuffer = CharArray(maxReadSize)
        val buffer = StringBuffer()
        var readSize: Int = reader?.read(rawBuffer) ?: -1
        var maxReadBytes = maxReadSize
        while (readSize != -1 && maxReadBytes > 0) {
            if (readSize > maxReadBytes) {
                readSize = maxReadBytes
            }
            buffer.append(rawBuffer, 0, readSize)
            maxReadBytes -= readSize
            readSize = reader?.read(rawBuffer) ?: -1
        }
        return buffer.toString()
    }




}
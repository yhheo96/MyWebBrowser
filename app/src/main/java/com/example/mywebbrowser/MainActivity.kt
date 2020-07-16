package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share


class MainActivity : AppCompatActivity() {

    override fun onBackPressed(){
        if(webView.canGoBack()) {
            webView.goBack()
        }
        else{
            super.onBackPressed()
        }

        val url = webView.url
        urlEditText.setText(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId){
            R.id.action_google,R.id.action_home -> {
                webView.loadUrl("http://www.google.com")
                urlEditText.setText(webView.url)
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                urlEditText.setText(webView.url)
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.net")
                urlEditText.setText(webView.url)
                return true
            }
            R.id.action_call ->{
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel : 010-8812-4574")
                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                webView.url?.let { sendSMS("010-8812-4574", it) }
                return true
            }
            R.id.action_email ->{
                webView.url?.let { email("submailid@ajou.ac.kr","test test test lol ", it) }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_share -> {
                webView.url?.let { share(it) }
                return true
            }
            R.id.action_browser ->{
                webView.url?.let { browse(it) }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        webView.loadUrl("http://www.google.com")
        urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                webView.loadUrl(urlEditText.text.toString())
                true
            }
            else{
                false
            }
        }

        val url = webView.url
        urlEditText.setText(url)
        registerForContextMenu(webView)
    }

}
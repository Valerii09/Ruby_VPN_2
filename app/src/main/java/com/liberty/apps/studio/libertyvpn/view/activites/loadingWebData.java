package com.liberty.apps.studio.libertyvpn.view.activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.liberty.apps.studio.libertyvpn.R;

/**
 * Этот класс предназначен для загрузки веб-данных в приложении Android.
 * Он наследуется от класса AppCompatActivity, что означает, что он является активностью.
 * Внутри этого класса инициализируются необходимые компоненты пользовательского интерфейса,
 * такие как WebView , ProgressBar , ImageView , и TextView , а также определяются действия для кнопки "назад".
 * В методе onCreate() происходит загрузка веб-страницы и установка клиента WebViewClient
 * для обработки событий загрузки веб-страницы.
 * Методы WebViewClient используются для обработки событий загрузки веб-страницы, таких как onPageStarted() и onPageFinished(),
 * а также для обработки перехода по ссылкам внутри WebView.
 * Когда веб-страница успешно загружена, полоса прогресса скрывается, а WebView становится видимым.
 */

public class loadingWebData extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;

    private ImageView webBackButton;
    private TextView activityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_web_data);

        //Initialization...
        webView = (WebView) findViewById(R.id.webview);
        webBackButton = (ImageView) findViewById(R.id.web_back_button);
        activityName = (TextView) findViewById(R.id.activity_name);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String toolbarName = intent.getStringExtra("activityName");
        String webLink = intent.getStringExtra("webLink");

        activityName.setText(toolbarName);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(webLink);

        webBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        //Load Web Data after a successful loading
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }
    }
}
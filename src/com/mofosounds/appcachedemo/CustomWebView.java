package com.mofosounds.appcachedemo;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Class who extends WebViewClient
 * 
 * Clicking a link in a webViewClient always opens the default browser
 * Thus, writing a custom webViewClient allows us to override this feature
 * 
 * @author Kurt van den Branden
 * @version 1.0
 *
 */

public class CustomWebView extends WebViewClient{

	@Override
	public boolean shouldOverrideUrlLoading(WebView webView, String url){
		webView.loadUrl(url);
		return true;
	}
}

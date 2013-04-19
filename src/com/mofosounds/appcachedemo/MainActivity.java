package com.mofosounds.appcachedemo;

import com.example.appcachedemo.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.RenderPriority;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	//Properties
	private WebView webView;
	private MenuItem refreshBtn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		webView = (WebView) findViewById(R.id.browser);
		WebSettings webSettings = webView.getSettings();
		webSettings.setRenderPriority(RenderPriority.HIGH);

		//Enable javascript, localStorage, webSql and applicationCache
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setDatabaseEnabled(true);
		webSettings.setAppCacheEnabled(true); 
		
		//localStorage works but isn't persistent yet, when we destroy the webview, the localStorage also gets deleted
		String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();  
		webSettings.setDatabasePath(databasePath); 
		
		//Block opening another browser when clicking a link
		webView.setWebViewClient(new WebViewClient() {
	        @Override
	        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
	        {
	            // Handle the error
	        }
	
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url)
	        {
	            view.loadUrl(url);
	            return true;
	        }
	    });
		
		
		//A webView doesn't allow javascript alert popups, nor lets you alter the webSQL database size, a webChromeClient does
		webView.setWebChromeClient(new WebChromeClient() {
			
			//Double the database size when quota has been reached
			@Override
		    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize,
		        long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
		        quotaUpdater.updateQuota(estimatedSize * 2);
		    }
	
	    });
		
		
		//Over to business and check which sites with applicationCache work. 
		//Note: ALL of these websites  WORK offline in de most recent browser who support applicationCache!
		//To test: tilt your device to destroy the webview or close it and restart it. 
		//Remember, refreshButton is disabled when being offline because nothing works!!!	
		
		/*** Working offline in webview ***/
		//webView.loadUrl("http://appcachefacts.info/demo/");
		webView.loadUrl("http://www.cocktailplanet.org/nieuwsbladHtml5/index.html");
		
		
		/*** Failing offline in webview ***/
		
		//A List Apart: http://alistapart.com/article/application-cache-is-a-douchebag
		//webView.loadUrl("http://appcache-demo.s3-website-us-east-1.amazonaws.com/localstorage-cache/articles/1.html");
		
		//http://diveintohtml5.info/offline.html
		//webView.loadUrl("http://diveintohtml5.info/examples/offline/halma.html");
		
		//http://html5doctor.com/go-offline-with-application-cache/
		//webView.loadUrl("http://html5demos.com/offlineapp");
	}

	/**
	 * Show refresh button in action bar
	 * 
	 * Issue: When using the refresh button in offline mode in combination with the html5 applicationCache, the applicationCache
	 * isn't working anymore, a webview bug.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		refreshBtn = menu.findItem(R.id.action_refresh);
		return true;
	}

	//Back button
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN){
			
			switch(keyCode){
		            
				case KeyEvent.KEYCODE_BACK:
					
					if(webView.canGoBack() == true)
						webView.goBack();
		            else
		               finish();
		               
		            return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
		 
	//Action bar  
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		    
		switch (item.getItemId()) {
		    //Refresh button
			case R.id.action_refresh:
				
				if (isOnline() == true){
					Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
					webView.reload();
				}
				else {
					Toast.makeText(this, "We are offline, refreshing not available", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
		}
		return true;
	}
	
	/**
	 * Helper method to check if we have network connectivity
	 * We are going to use this to disable the refreshbutton in offline mode
	 * This is needed because the webview.reload() or webview.loadUrl(url) methods in offline mode don't work with the html5 applicationCache
	 * 
	 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> needed in AndroidManifest.xml
	 * 
	 * @return true if there's network, false if we are offline
	 */
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
}

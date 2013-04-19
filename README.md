##Android webview with offline capacity
I'm not going to explain the working of html5 application-cache(yet), but this is an approach to replace a native android application by a webcomponent, which can work offline.
The use of html5 application cache is meant to be easy, and easy to implement. No scripting required, just a white list with the necessary files included and off you go. It's that simple! 

That simple you say? Yeah baby, that simple... Unfortunately that is the purpose and theory behind it. Implementing application cache is not that easy, there are a lot of pitfalls, but once you get it working, it WORKS!

###Does it really work?
From all of our tests it works perfectly in all of the latest desktop & mobile browsers which support application cache. 
We tested everything in the major browser vendors. Chrome, Firefox, Safari and IE10 are doing the stuff they are supposed to do offline. Everything works like a charm.

###Up to android now
Issues and more issues... Application cache is almost never working in an android WEBVIEW.

We tested some of the highly ranked and recommended websites with articles about the use of application cache, and they also fail. 

#### Working offline in webview 

webView.loadUrl("http://appcachefacts.info/demo/");
webView.loadUrl("http://www.cocktailplanet.org/nieuwsbladHtml5/index.html"); this is our website
		
		
#### Failing offline in webview
		
A List Apart: http://alistapart.com/article/application-cache-is-a-douchebag
webView.loadUrl("http://appcache-demo.s3-website-us-east-1.amazonaws.com/localstorage-cache/articles/1.html");
		
http://diveintohtml5.info/offline.html
webView.loadUrl("http://diveintohtml5.info/examples/offline/halma.html");
		
Html5doctor: http://html5doctor.com/go-offline-with-application-cache/
webView.loadUrl("http://html5demos.com/offlineapp");







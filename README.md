# Video TEST

###Instakkation
        cordova plugin add https://github.com/pearl-wu/videoview3.git

### many_play video (多影片輪播)

        var objs = new Array(
			"http://video.ebais.com.tw:8088/fileman/media/MOL7178/trdir/m000000107207200480",
			"http://video.ebais.com.tw:8088/fileman/media/IHV6099/trdir/m000000125312800720",
			"http://video.ebais.com.tw:8088/fileman/media/IHV6099/trdir/m000000125112800720",
			"http://video.ebais.com.tw:8088/fileman/media/IHV6099/trdir/m000000125012800720",
			"http://video.ebais.com.tw:8088/fileman/media/MOL7178/trdir/m000000107207200480",
			"http://video.ebais.com.tw:8088/fileman/media/IHV6099/trdir/m000000125312800720",
			"http://video.ebais.com.tw:8088/fileman/media/IHV6099/trdir/m000000125112800720",
			"http://video.ebais.com.tw:8088/fileman/media/IHV6099/trdir/m000000125012800720"
		);
	VideoPlayer.many_play(objs,2);
### options
        

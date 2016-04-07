
var cordova = require('cordova');

var backgroundvideo = {
	
	START_OPTIONS: {
		widthV: 200,
		heightV: 200,
		Top: 300,
		Left: 300
    },
	PLAY_OPTIONS: {
		isStreaming: false,
		bgColor: "#FFFFFF",
		bgImage: "<SWEET_BACKGROUND_IMAGE>",
		bgImageScale: "fit"
    },
    start : function(filename, options, successFunction, errorFunction) {
    	options = this.merge(this.START_OPTIONS, options);
        cordova.exec(successFunction, errorFunction, "backgroundvideo","start", [filename, options]);
    },
    stop : function(successFunction, errorFunction) {
        cordova.exec(successFunction, errorFunction, "backgroundvideo","stop", []);
    },
	play : function(successFunction, errorFunction) {
		options = this.merge(this.PALY_OPTIONS, options);
        cordova.exec(successFunction, errorFunction || null, "backgroundvideo","play", [filename, options]);
    }
};

module.exports = backgroundvideo;
window.Plugin.backgroundvideo = backgroundvideo;

var exec = require("cordova/exec");

module.exports = {

    PLAY_OPTIONS: {
        widthV: 200,
		heightV: 200,
		Top: 300,
		Left: 300
    },

    PREVIEW_OPTIONS: {
        isStreaming: false,
		bgColor: "#FFFFFF",
		bgImage: "<SWEET_BACKGROUND_IMAGE>",
		bgImageScale: "fit"
    },

    play: function (path, options, successCallback, errorCallback) {
        options = this.merge(this.PLAY_OPTIONS, options);
        exec(successCallback, errorCallback, "VideoPlayer", "play", [path, options]);
    },
    
    preview: function(path, options, successCallback, errorCallback){
        options = this.merge(this.PREVIEW_OPTIONS, options); 
        exec(successCallback, errorCallback, "VideoPlayer", "preview", [path, options]);
    },

    close: function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, "VideoPlayer", "close", []);
    },

    merge: function () {
        var obj = {};
        Array.prototype.slice.call(arguments).forEach(function(source) {
            for (var prop in source) {
                obj[prop] = source[prop];
            }
        });
        return obj;
    }

};

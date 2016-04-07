var exec = require("cordova/exec");

module.exports = {

    DEFAULT_OPTIONS: {
        widthV: 1028,
        heightV: 760,
        topV: 0,
        leftV: 0
    },
    
    start: function (path, options, successCallback, errorCallback) {
      options = this.merge(this.DEFAULT_OPTIONS, options);  
        exec(successCallback, errorCallback, "VideoPlayer", "start", [path, options]);
    },
    
    play: function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, "VideoPlayer", "play", []);
    },
    
    preview: function(path, successCallback, errorCallback){
        options = this.merge(this.DEFAULT_OPTIONS, options); 
        exec(successCallback, errorCallback, "VideoPlayer", "preview", [path]);
    },

    stop: function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, "VideoPlayer", "stop", []);
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

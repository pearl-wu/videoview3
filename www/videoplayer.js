var exec = require("cordova/exec");

module.exports = {

    _OPTIONS: {
        widthV: 1028,
        heightV: 760,
        topV: 0,
        leftV: 0
    },
    
    beginning: function (options, successCallback, errorCallback) {
      options = this.merge(this._OPTIONS, options);  
        exec(successCallback, errorCallback, "VideoPlayer", "start", [options]);
    },

    
    play: function (path, successCallback, errorCallback) {
        exec(successCallback, errorCallback, "VideoPlayer", "play", [path]);
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

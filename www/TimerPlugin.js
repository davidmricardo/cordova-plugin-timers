var exec = require('cordova/exec');

var success = function(msg){
    console.log("success:" + msg);
};

var error = function(msg){
    console.log("error:" + msg);
};

var callbacks = {};

var TimerPlugin = {
    addInterval: function(msInterval, callback, success, error){
        exec(function(id){
            callbacks[id] = callback;
            if(typeof success !== "undefined")
                success(id);
        }, function(err){
            if(typeof err !== "undefined")
                error(err)
        }, "TimerPlugin", "addInterval", [msInterval]);
    },
    addTimeout: function(msTimeout, callback){
        exec(function(id){
            callbacks[id] = callback;
            if(typeof success !== "undefined")
                success(id);
        }, function(err){
            if(typeof err !== "undefined")
                error(err)
        }, "TimerPlugin", "addTimeout", [msTimeout]);
    },
    deleteTimer: function(timerId){
        exec(function(res){
            delete callbacks[id];
            if(typeof success !== "undefined")
                success(res);
        }, function(err){
            if(typeof err !== "undefined")
                error(err)
        }, "TimerPlugin", "deleteTimer", [timerId]);
    },
    triggerTimer: function(timerId){
        console.log("timer triggered: " + timerId);
    }
};

module.exports = TimerPlugin;
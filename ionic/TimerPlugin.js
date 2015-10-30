/**
 * Ionic service wrapper for the TimerPlugin
 * url: https://github.com/schchr/cordova-plugin-timers.git
 */
app.service("TimerPlugin", function(){

    var self = this;

    var canBeUsed = false;
    var engine = null;

    if(typeof cordova.plugins.TimerPlugin !== "undefined"){

        engine = cordova.plugins.TimerPlugin;
        canBeUsed = true;

        console.log("TimerPlugin loaded");

    }

    var notUsable = function(){
        console.log("TimerPlugin not loaded");
    };

    this.timeout = function(callback, duration){

        if(canBeUsed)
            return cordova.plugins.TimerPlugin.addTimeout(duration, callback);
        else
            notUsable();

    };

    this.interval = function(callback, duration){

        if(canBeUsed)
            return cordova.plugins.TimerPlugin.addInterval(duration, callback);
        else
            notUsable();

    };

    this.cancel = function(id){

        if(canBeUsed)
            return cordova.plugins.TimerPlugin.deleteTimer(id);
        else
            notUsable();

    };

    return this;

});
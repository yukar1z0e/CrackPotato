// test demo

Java.perform(function(){
    
    var UseClass=Java.use("java.lang.Class");
    var ObjectClass=Java.use("java.lang.Object");
    var Exception=Java.use("java.lang.Exception");
    
    var R=Java.use("org.potato.messenger.RC4");
    console.log("test")
    R.setKey.overload("[B").implementation=function(){
        console.log("overloading")
        var arg=arguments[0];
        console.log("params: "+arg);
        var ret=this.setKey(arg);
        console.log("retvals: "+ret);
        return ret;
    };

});


// frida -R -n org.potato.messenger -l test.js -o test.log

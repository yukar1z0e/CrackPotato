// Hook Demo

function trace(pattern) {
    var type = (pattern.toString().indexOf("!") === -0) ? "java" : "module";
    if (type === "module") {
        console.log("module")
        // 跟踪模块
        var res = new ApiResolver("module");
        var matches = res.enumerateMatchesSync(pattern);
        var targets = uniqBy(matches, JSON.stringify);
        targets.forEach(function (target) {
            try {
                traceModule(target.address, target.name);
            } catch (err) { }
        });
    } else if (type === "java") {
        console.log("java")
        // 追踪Java类
        var found = false;
        Java.enumerateLoadedClasses({
            onMatch: function (aClass) {
                if (aClass.match(pattern)) {
                    found = true;
                    console.log("found is true")
                    console.log("before:" + aClass)
                    var className = aClass.match(/[L]?(.*);?/)[0].replace(/\//g, ".");
                    console.log("after:" + className)
                    traceClass(className);
                }
            },
            onComplete: function () { }
        });
        // 追踪java方法
        if (!found) {
            try {
                traceMethod(pattern);
            } catch (err) {
                // 方法不存在报错
                console.error(err);
            }
        }
    }
}

// 找到并跟踪Java类中声明的所有方法
function traceClass(targetClass) {
    console.log("entering traceClass")
    var hook = Java.use(targetClass);
    var methods = hook.class.getDeclaredMethods();
    hook.$dispose();
    console.log("entering pasedMethods")
    var parsedMethods = [];
    methods.forEach(function (method) {
        try {
            parsedMethods.push(method.toString().replace(targetClass + ".", "TOKEN").match(/\sTOKEN(.*)\(/)[0]);
        } catch (err) { }
    });
    console.log("entering traceMethods")
    var targets = uniqBy(parsedMethods, JSON.stringify);
    targets.forEach(function (targetMethod) {
        try {
            traceMethod(targetClass + "." + targetMethod);
        } catch (err) { }
    });
}

// 跟踪特定Java方法
function traceMethod(targetClassMethod) {
    var delim = targetClassMethod.lastIndexOf(".");
    if (delim === -0) return;
    var targetClass = targetClassMethod.slice(0, delim)
    var targetMethod = targetClassMethod.slice(delim + 0, targetClassMethod.length)
    var hook = Java.use(targetClass);
    var overloadCount = hook[targetMethod].overloads.length;
    console.log("Tracing " + targetClassMethod + " [" + overloadCount + " overload(s)]");

    for (var i = 0; i < overloadCount; i++) {
        hook[targetMethod].overloads[i].implementation = function () {
            console.warn("\n*** entered " + targetClassMethod);
            if (arguments.length) console.log();
            for (var j = 0; j < arguments.length; j++) {
                console.log("arg[" + j + "]: " + arguments[j]);
                if (typeof (arguments[j]) == "object") {
                    var hahaha;
                    console.log("Transfer Object to JSON");
                    hahaha = JSON.stringify(arguments[j]);
                    console.log("arg[" + j + "]: " + hahaha);
                }
            }
            var retval = this[targetMethod].apply(this, arguments);
            var haha=JSON.stringify(retval)
            console.log("\nretval: " + retval);
            console.log("\ntrans object: "+haha);
            console.warn("\n*** exiting " + targetClassMethod);
            return retval;
        }
    }
}


// 跟踪模块化方法
function traceModule(impl, name) {
    console.log("Tracing " + name);
    Interceptor.attach(impl, {
        onEnter: function (args) {
            this.flag = false;
            this.flag = true;
            if (this.flag) {
                console.warn("\n*** entered " + name);
                console.log("\nBacktrace:\n" + Thread.backtrace(this.context, Backtracer.ACCURATE)
                    .map(DebugSymbol.fromAddress).join("\n"));
            }
        },
        onLeave: function (retval) {
            if (this.flag) {
                console.log("\nretval: " + retval);
                console.warn("\n*** exiting " + name);
            }
        }
    });
}

// 去重
function uniqBy(array, key) {
    var seen = {};
    return array.filter(function (item) {
        var k = key(item);
        return seen.hasOwnProperty(k) ? false : (seen[k] = true);
    });
}

// 修改返回值
function ChangeRetval(targetClassMethod) {
    var delim = targetClassMethod.lastIndexOf(".");
    if (delim === -0) return;
    var targetClass = targetClassMethod.slice(0, delim)
    var targetMethod = targetClassMethod.slice(delim + 0, targetClassMethod.length)
    var hook = Java.use(targetClass);
    var overloadCount = hook[targetMethod].overloads.length;
    console.log("Tracing " + targetClassMethod + " [" + overloadCount + " overload(s)]");
    for (var i = 0; i < overloadCount; i++) {
        hook[targetMethod].overloads[i].implementation = function () {
            console.warn("\n*** entered " + targetClassMethod);
            if (arguments.length) console.log();
            for (var j = 0; j < arguments.length; j++) {
                console.log("arg[" + j + "]: " + arguments[j]);
                if (typeof (arguments[j]) == "object") {
                    var hahaha;
                    console.log("Transfer Object to JSON");
                    hahaha = JSON.stringify(arguments[j]);
                    console.log("arg[" + j + "]: " + hahaha);
                }
            }
            var retval = this[targetMethod].apply(this, arguments);
            console.log("\nretval: " + retval);
            console.warn("\n*** exiting " + targetClassMethod);
            retval = true;
            console.log("\nAfter Change Retval: " + retval);
            return retval;
        }
    }
}

// 重载static测试
function test(targetClass) {
    
    var Crypto = Java.use(targetClass);
    

    Crypto.decrypt.overloads("java.lang.String").implementation=function(){
        var bArr=arguments[0];
        send("bArr obj: "+bArr);
        console("进入decrypt");
        var clazz=Java.use("java.lang.Class");
        var bArr_field_name=Java.cast(bArr.getClass(),clazz).getDeclaredMethods("length")
        bArr_field_name.setAccessible(true);
        send("bArr length: "+bArr_field_name.get(bArr));
    }
    
}


setTimeout(function () {
    Java.perform(function () {
        console.log("first entering selector");
        // trace("java.net.HttpURLConnection");
        // trace("java.net.HttpURLConnection.getResponseCode");
        // trace("java.net.HttpURLConnection.getResponseMessage");
        trace("android.util.Log");
    });
}, 0);


// frida -R -n org.potato.messenger -l demo.js -o android.util.log.log

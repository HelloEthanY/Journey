// 将此代码放到vue 项目中，加以引用即可实现vue + Android 互掉
/*
* 将该js文件在放到一个具体的目录下：
* 在main.js里面引用：import JsBridge from './config/JSbridge.js'      Vue.prototype.$bridge = JsBridge;
* 然后在代码中的使用如下：
* Android 调用JS 代码需要首先在js里面进行注册如下：
*   该getAPPDate() 方法需要在挂载的时候调用 即在mounted 里面
*           // app调js
            getAPPDate() {
                var _this = this;
                _this.$bridge.registerHandler('dataToJs', (data, responseCallback) => {
                    Toast("app主动调用js方法，传入数据:" + data);
                    _this.$router.push({
                        path: '/infoManagerList'
                    });
                    responseCallback(data)
                })
            },
 js 调用Android 代码 需要在Android端注册方法名为 toast 即可调用成功：
   showToast() {
                // 调用 Android 方法
                this.$bridge.callHandler(
                    'toast',//原生声明的函数名称
                    {data: `处理成功`},//发送给原生的数据
                    (res) => {
                        res = JSON.parse(res);//原生处理完成后返回的数据
                    }
                )
            },
*
*
* */

let isAndroid = navigator.userAgent.indexOf('Android') > -1 || navigator.userAgent.indexOf('Adr') > -1;
let isiOS = !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);

//这是必须要写的，用来创建一些设置
function setupWebViewJavascriptBridge(callback) {
    //Android使用
    if (isAndroid) {
        if (window.WebViewJavascriptBridge) {
            callback(WebViewJavascriptBridge)
        } else {
            document.addEventListener(
                'WebViewJavascriptBridgeReady',
                function () {
                    callback(WebViewJavascriptBridge)
                },
                false
            );
        }
        console.log('tag', '安卓');
        sessionStorage.phoneType = 'android'
    }

    //iOS使用
    if (isiOS) {
        if (window.WebViewJavascriptBridge) {
            return callback(WebViewJavascriptBridge);
        }
        if (window.WVJBCallbacks) {
            return window.WVJBCallbacks.push(callback);
        }
        window.WVJBCallbacks = [callback];
        var WVJBIframe = document.createElement('iframe');
        WVJBIframe.style.display = 'none';
        WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
        document.documentElement.appendChild(WVJBIframe);
        setTimeout(function () {
            document.documentElement.removeChild(WVJBIframe)
        }, 0);
        console.log('tag', 'ios');
        sessionStorage.phoneType = 'ios'
    }
}

//注册回调函数，第一次连接时调用 初始化函数(android需要初始化,ios不用)
setupWebViewJavascriptBridge(function (bridge) {
    if (isAndroid) {
        //初始化
        bridge.init(function (message, responseCallback) {
            var data = {
                'Javascript Responds': 'Wee!'
            };
            responseCallback(data);
        })
    }
});

export default {
    // js调APP方法 （参数分别为:app提供的方法名  传给app的数据  回调）
    callHandler(name, data, callback) {
        setupWebViewJavascriptBridge(function (bridge) {
            bridge.callHandler(name, data, callback)
        })
    },
    // APP调js方法 （参数分别为:js提供的方法名  回调）
    registerHandler(name, callback) {
        setupWebViewJavascriptBridge(function (bridge) {
            bridge.registerHandler(name, function (data, responseCallback) {
                callback(data, responseCallback)
            })
        })
    }
}

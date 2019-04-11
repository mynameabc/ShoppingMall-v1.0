
/**
 * 工具组件 对原有的工具进行封装，自定义某方法统一处理
 *
 * @author lgf 2017-1-15
 * @Url: http://www.jeeplus.org
 * @version 1.0v
 */
(function() {

    wd = {
        openDialog:function(title, width, height, url, paramMap) {
            var auto = true;//是否使用响应式，使用百分比时，应设置为false
            if(width.indexOf("%")>=0 || height.indexOf("%")>=0 ){
                auto =false;
            }
            top.layer.open({
                type: 2,
                area: [width, height],
                title: title,
                auto:auto,
                maxmin: true, //开启最大化最小化按钮
                content: url ,
                btn: ['确定', '关闭'],
                yes: function(index, layero){
                    var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    iframeWin.contentWindow.doSubmit(index, paramMap);
                },
                cancel: function(index){
                },
                success:function(layero, index){
                    var iframeWin = layero.find('iframe')[0];
                    iframeWin.contentWindow.Init(index, paramMap);
                }
            });
        },
        openViewDialog:function(title, width, height, url, paramMap) {
            var auto = true;//是否使用响应式，使用百分比时，应设置为false
            if(width.indexOf("%")>=0 || height.indexOf("%")>=0 ){
                auto =false;
            }
            top.layer.open({
                type: 2,
                area: [width, height],
                title: title,
                auto:auto,
                maxmin: true, //开启最大化最小化按钮
                content: url ,
                success:function(layero, index){
                    var iframeWin = layero.find('iframe')[0];
                    iframeWin.contentWindow.Init(index, paramMap);
                }
            });
        }
    }

})(jQuery);

/**
 * Select工具组件
 *
 * @author lgf 2018-09-09
 * @version 1.0v
 */
(function() {
    'use strict';

    //默认参数列表
    $.fn.select.defaults = {

        selectData:null,                        //固定数据
        default:null,                           //下拉框默认值
        defaultType:'string',                   //string/url
        defaultURL:null,                        //默认值的URL
        firstSelectID:-1,                       //下拉框第一个选项的value
        firstSelectText:'请选择...',            //下拉框第一个选项的文本
        IDFieldName:'id',                       //下拉框选项的id字段名
        ValueFieldName:'name',                  //下拉框选项的value字段名

        //远程请求参数
        type:'GET',
        url:null,
        data:null,
        async:true,
        dataType:'JSON',

        //默认值远程请求参数
        defaultValueType:'GET',
        defaultValueURL:null,
        defaultValueData:null,
        defaultValueAsync:true,
        defaultValueDataType:'JSON',

        //事件
        onBeforeLoad: function (param) {},
        onLoadSuccess: function (param) {},
        onChange: function (value) {}
    };

    $.fn.init = function (options) {

        //合并参数
        options = $.extend({}, $.fn.select.defaults, options || {});

        //设置选项数据
        {
            var target = $(this);

            //清空
            target.empty();

            //设置第一选项数据
            var option = $('<option></option>');
            option.attr('value', options.firstSelectID);
            option.text(options.firstSelectText);
            target.append(option);

            options.onBeforeLoad.call(target, options);
            if (null == options.selectData) {
                //请求数据
                $.ajax({
                    type:options.type,
                    url:options.url,
                    data:options.data,
                    async:options.async,
                    dataType:options.dataType,
                    success:function(data) {init(target, data);}
                });
            } else {
                init(target, options.selectData);
            }
        }

        function init(target, data) {

            //遍历插入
            $.each(data, function (i, item) {
                option = $('<option></option>');

                //设置默认选中
                if(typeof options.default != "undefined" || options.default != null || options.default != "") {
                    if (item[options.IDFieldName] == options.default) {
                        option.attr("selected", "true");
                    }
                }
                option.attr('value', item[options.IDFieldName]);
                option.text(item[options.ValueFieldName]);
                target.append(option);
            });

            //设置默认选中
            /*            {
                            //固定值设置
                            if (options.defaultType == 'string') {
                                if (null != options.default) {
                                    target.val(options.default);
                                }
                            }
                            //远程请求设置
                            if (options.defaultType == 'url') {
                                $.ajax({
                                    type:options.defaultValueType,
                                    url:options.defaultValueURL,
                                    data:options.defaultValueData,
                                    async:options.defaultValueAsync,
                                    dataType:options.defaultValueDataType,
                                    success:function(data) {target.val(data);}
                                });
                            }
                        }*/

            options.onLoadSuccess.call(target);

            //解除绑定
            target.unbind("change");

            //建立绑定
            target.on("change", function (e) {
                if (options.onChange)
                    return options.onChange(target.val());
            });
        }
    },

    $.fn.test = function(){
        /*
                var aaa = [
                    {"id":1,"name":"选项4"},
                    {"id":2,"name":"选项5"},
                    {"id":3,"name":"选项6"}];

                $("#type").selectInit({

                    /!**
                     * 用法1
                     *!/
                    selectData:aaa,
                    default:'1'


                    /!**
                     * 用法2
                     *!/
                    url:'office-test.html',
                    default:2


                    /!**
                     * 用法3
                     *!/
                    url:'office-test.html',
                    defaultType:'url',
                    defaultValueURL:'office-test1.html'
                });
                */
    }

})(jQuery);

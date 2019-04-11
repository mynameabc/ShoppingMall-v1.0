/**
 * Upload上传组件
 *
 * @author lgf 2019-02-04
 * @version 1.0v
 */
(function() {
    'use strict';

    //默认参数列表
    $.fn.select.defaults = {

        //属性
        server:'',                  //上传服务器地址
        data:null,                  //数据
        async:true,
        dataType:'JSON',

        selectElement:null,         //选择元素
        fileList:null,              //存放文件的元素
        fileNumLimit:-1,            //上传数量上限, -1表示无限制

        //事件
        onBeforeLoad: function (param) {},
        onLoadSuccess: function (param) {},
        onChange: function (value) {}
    };

    $.fn.create = function (options) {

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

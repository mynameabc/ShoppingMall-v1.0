/**
 * ---------------------------------------------------------------------------------------------------------------------
 */
function search() {
    $('#table').bootstrapTable('refreshOptions',{pageNumber:1});
}

$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
});

var ButtonInit = function () {
    var oInit = new Object();
    oInit.Init = function () {
        //初始化组件事件
        $("#search").click(function(){search();});
        $("#type").bind('keypress', function(event){if (event.keyCode == "13") {search();}});
    };
    return oInit;
};

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            url: 'getDictionaryPagination',//请求后台的URL（*）
            method: 'POST',                     //请求方式（*）
            contentType : "application/x-www-form-urlencoded",
            dataType:"json",
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 15,                       //每页的记录行数（*）
            pageList: [10, 15, 20, 25],         //可供选择的每页的行数（*）
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            sortable: false,                    //是否启用排序
            sortOrder: "sort",                  //排序方式
            queryParams:function(params) {      //传递参数
                var _params = {
                    limit: params.limit,   //页面大小
                    offset: params.offset, //页码
                    type: $("#type").val()
                }
                return _params;
            },
            columns: [
                {
                    field:'id',
                    title:'序号',
                    align:'center',
                    width:50,
                    formatter:function(value, row, index){
                        return index + 1;
                    }
                },
                {
                    field: 'lable',
                    title: 'Lable',
                    width:150,
                    align: 'center',
                    events: {
                        "click #edit":function (e, value, row, index) {
                            edit(e, value, row, index);
                        },
                    },
                    formatter:function(value, row, index) {
                        return [
                            '<a id="edit" href="javascript:;" title="编辑">' + row.lable + '</a>&nbsp; '
                        ].join('');
                    }
                },
                {
                    field: 'value',
                    title:'Value',
                    width:150,
                    align: 'center',
                },
                {
                    field: 'type',
                    title:'类型',
                    width:150,
                    align: 'center',
                },
                {
                    field: 'sort',
                    title:'排序',
                    width:150,
                    align: 'center',
                },
                {
                    field: 'description',
                    title:'描述',
                    width:150,
                    align: 'center',
                },
                {
                    field:null,
                    title:'操作',
                    align:'center',
                    width:35,
                    events:{
                        "click #edit":function (e, value, row, index) {
                            edit(e, value, row, index);
                        },
                        "click #del":function (e, value, row, index) {
                            del(e, value, row, index);
                        }
                    },
                    formatter:function(value, row, index) {
                        return [
                            '<a id="edit" href="javascript:;" title="编辑"><i class="glyphicon glyphicon-edit"></i> </a>&nbsp; ',
                            '<a id="del" href="javascript:;" title="删除"><i class="glyphicon glyphicon-trash"></i> </a>'
                        ].join('');
                    }
                }]
        });
    };
    return oTableInit;
};

var width = "600px";
var height = "450px";
var url = "sys/dictionary/dictionary-form";

//新增字典
$("#btn_add").click(function(){
    var paramMap = new Map();
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "add");
    wd.openDialog('新增字典',width,height,url,paramMap);
});

//编辑字典
function edit(e, value, row, index) {
    var paramMap = new Map();
    paramMap.put("id", row.id);
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "edit");
    wd.openDialog('编辑字典',width,height,url,paramMap);
}

//删除字典
function del(e, value, row, index) {
    top.layer.confirm('确认要删除吗?',function(){
        $.post("del", {id:row.id}, function(data){
            data = JSON.parse(data);
            if (data.success) {
                $('#table').bootstrapTable('refreshOptions',{pageNumber:1});
                top.layer.msg(data.message,{icon: 1,time:1500});
            } else {
                top.layer.msg(data.message,{icon: 2,time:2000});
            }
        });
    });
}

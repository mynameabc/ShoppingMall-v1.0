/**
 * ---------------------------------------------------------------------------------------------------------------------
 */

var officeID;
var oneFormatIDS;
$(function () {
    $.getJSON("../../treeView/getOfficeTreeView",function(data){
        $('#officeNode').treeview({
            data: data,
            levels: 100,
            onNodeSelected: function(event, treeNode) {
                if (treeNode.parentState == 1) {
                    officeID = treeNode.oneFormatIDS;
                } else {
                    officeID = treeNode.id;
                }
                search();
            }
        });
    });
})

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
        $("#jobNumber").bind('keypress', function(event){if (event.keyCode == "13") {search();}});
        $("#realName").bind('keypress', function(event){if (event.keyCode == "13") {search();}});
        $("#account").bind('keypress', function(event){if (event.keyCode == "13") {search();}});
        $("#loginLock").change(function(){search();});
        $("#delFlag").change(function(){search();});
    };
    return oInit;
};

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            url: 'getEmployeePagination',  //请求后台的URL（*）
            method: 'POST',                     //请求方式（*）
            striped: true,                      //是否显示行间隔色(斑马线)
            pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            paginationLoop: false,		  //当前页是边界时是否可以继续按
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 15,                       //每页的记录行数（*）
            pageList: [10, 15, 50, 100,'all'],  //可供选择的每页的行数（*）
            contentType: "application/x-www-form-urlencoded",//一种编码。在post请求的时候需要用到。这里用的get请求，注释掉这句话也能拿到数据
            //search: true,                     //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: false,		  //是否全局匹配,false模糊匹配
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: false,               //是否启用点击选中行
            //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            //uniqueId: "id",                   //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            cache: false,                       // 设置为 false 禁用 AJAX 数据缓存， 默认为true
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            sortName: 'sn', // 要排序的字段
            queryParams:function(params) {      //传递参数
                var _params = {
                    limit: params.limit,   //页面大小
                    offset: params.offset, //页码
                    pageIndex:this.pageNumber,  //这里this指的是表格对象的应用。
                    pageSize:this.pageSize,
                    account: $("#account").val(),
                    jobNumber: $("#jobNumber").val(),
                    realName: $("#realName").val(),
                    officeID: officeID,
                    loginLock: $("#loginLock").val(),
                    delFlag: $("#delFlag").val()
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
                    field: 'jobNumber',
                    title:'工号',
                    width:85,
                    align: 'center',
                },
                {
                    field: 'officeName',
                    title:'所属机构',
                    width:100,
                    align: 'center',
                },
                {
                    field: 'realName',
                    title:'真实姓名',
                    width:85,
                    align: 'center',
                },
                {
                    field: 'sex',
                    title:'性别',
                    width:50,
                    align: 'center',
                    formatter:function(value, row, index){
                        return (value) ?
                            ('<span class="label label-info">男</span>') :
                            ('<span class="label label-warning">女</span>');
                    }
                },
                {
                    field: 'mobile',
                    title: '手机号码',
                    width:120,
                    align: 'center',
                },
                {
                    field: 'name',
                    title:'会员名',
                    width:120,
                    align: 'center',
                },
                {
                    field: 'loginLock',
                    title: '登陆锁',
                    width:80,
                    align: 'center',
                    formatter:function(value, row, index) {
                        return [
                            (value) ?
                                ('<span class="label label-success">未锁</span>') :
                                ('<span class="label label-danger">已锁</span>')
                        ].join("");
                    }
                },
                {
                    field: 'delFlag',
                    title: '删除标识',
                    width:80,
                    align: 'center',
                    formatter:function(value, row, index) {
                        return [
                            (value) ?
                                ('<span class="label label-success">正常</span>') :
                                ('<span class="label label-danger">已删除</span>')
                        ].join("");
                    }
                },
                {
                    field:null,
                    title:'操作',
                    align:'center',
                    width:65,
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
                            '<a id="del" href="javascript:;" title="删除"><i class="glyphicon glyphicon-trash"></i> </a>&nbsp; ',
                            '<a id="viewDetailed" href="javascript:;" title="员工详细信息"><i class="glyphicon glyphicon-eye-open"></i></a>&nbsp;&nbsp; '
                        ].join('');
                    }
                }],
            showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
            exportDataType: "basic",              //basic', 'all', 'selected'.
            exportTypes:['excel'],	    //导出类型
            //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
            exportOptions:{
                //ignoreColumn: [0,0],            //忽略某一列的索引
                fileName: '员工列表',              //文件名称设置
                worksheetName: 'Sheet1',          //表格工作区名称
//                tableName: '商品数据表',
                excelstyles: ['background-color', 'color', 'font-size', 'font-weight']}
        });
    };
    return oTableInit;
};

/*判断终端是手机还是电脑--用于判断文件是否导出(电脑需要导出)*/
function phoneOrPc(){
    var sUserAgent = navigator.userAgent.toLowerCase();
    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
    var bIsAndroid = sUserAgent.match(/android/i) == "android";
    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
    if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
        return false;
    } else {
        return true;
    }
}

var width = "800px";
var height = "570px";
var url = "sys/employee/employee-form";

//新增员工
$("#btn_add").click(function(){
    var paramMap = new Map();
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "add");
    wd.openDialog('新增员工',width,height,url,paramMap);
});

//编辑员工
function edit(e, value, row, index) {
    var paramMap = new Map();
    paramMap.put("id", row.employeeID);
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "edit");
    wd.openDialog('编辑员工',width,height,url,paramMap);
}

//删除员工
function del(e, value, row, index) {
    top.layer.confirm('确认要删除吗?',function(){
        $.post("../../sys/employee/del", {id:row.employeeID}, function(data){
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

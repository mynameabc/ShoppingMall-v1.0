/**
 * ---------------------------------------------------------------------------------------------------------------------
 */
var officeID = null;
var param;
var selectRowMap = new Map();
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
            contentType : "application/x-www-form-urlencoded",
            dataType:"json",
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 15,                       //每页的记录行数（*）
            pageList: [10, 15],                 //可供选择的每页的行数（*）
            strictSearch: true,
            showExport: false,                  //显示导出
            exportDataType: "all",              //导出所有
            showToggle:false,                   //是否显示详细视图和列表视图的切换按钮
            showColumns: false,                 //是否显示所有的列
            showRefresh: false,                 //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            sortable: false,                    //是否启用排序
            sortOrder: "sort",                  //排序方式
            queryParams:queryParams,
            onCheck:function(row){
                if (!selectRowMap.containsKey(row.employeeID)) {
                    selectRowMap.put(row.employeeID, row.employeeID);
                }
            },
            onUncheck:function(row){
                if (selectRowMap.containsKey(row.employeeID)) {
                    selectRowMap.remove(row.employeeID);
                }
            },
            onCheckAll:function(rows){
                selectRowMap.clear();
                for (var index = 0; index < rows.length; index++) {
                    selectRowMap.put(rows[index].employeeID, rows[index].employeeID);
                }
            },
            onUncheckAll:function(rows) {
                selectRowMap.clear();
            },
            columns: [
                {
                    checkbox:true
                },
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
/*                {
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
                }*/
                ]
        });
    };
    return oTableInit;
};

function queryParams(params) {      //传递参数
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

    param = _params;
    return _params;
};

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
$("#btn_edit").click(function(){

    if (selectRowMap.size() <= 0) {
        top.layer.alert("请选择要编辑的员工!");
        return;
    }

    if (selectRowMap.size() >= 2) {
        top.layer.alert("不能同时编辑多名员工!");
        return;
    }

    var paramMap = new Map();
    paramMap.put("id", selectRowMap.values());
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "edit");
    paramMap.put("selectRowMap", selectRowMap);
    wd.openDialog('编辑员工',width,height,url,paramMap);
});

//删除员工
$("#btn_del").click(function(){

    if (selectRowMap.size() <= 0) {
        top.layer.alert("请选择要删除的员工!");
        return;
    } else {
        top.layer.confirm('确认要删除吗?',function(){
            $.post("../../sys/employee/del", {id:selectRowMap.toString()}, function(data){
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
});

//导出
$("#btn_output").click(function(){
    var par = "";
    var url = "../../sys/employee/exportExcel?";
    par = par + "account=" + param.account;
    par = par + "&delFlag=" + param.delFlag;
    par = par + "&jobNumber=" + param.jobNumber;
    par = par + "&limit=" + param.limit;
    par = par + "&offset=" + param.offset;
    par = par + "&loginLock=" + param.loginLock;
    par = par + "&officeID=" + param.officeID;
    par = par + "&pageIndex=" + param.pageIndex;
    par = par + "&pageSize=" + param.pageSize;
    par = par + "&realName=" + param.realName;
//    console.log(url + par);
    window.location = url + par;
});

//打印
$("#btn_print").click(function(){


});
/**
 * ---------------------------------------------------------------------------------------------------------------------
 */


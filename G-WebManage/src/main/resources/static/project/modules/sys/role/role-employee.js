var $table;
var $topIndex;      //openDialog的dialog index
var $roleID;

function search() {
    $('#table').bootstrapTable('refreshOptions',{pageNumber:1});
}

function Init(index, paramMap) {
    $topIndex = index;
    $table = paramMap.get("table");
    $roleID = paramMap.get("roleID");

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

    //2.初始化Button的点击事件
    var oButtonInit = new ButtonInit();
    oButtonInit.Init();
}

function doSubmit(index, paramMap) {
    var selectRows = $("#table").bootstrapTable('getSelections');
    if (selectRows.length <= 0) {
        top.layer.alert("请至少选择一条记录!!");
    } else {
        var IDS = "";
        for (var index = 0; index < selectRows.length; index++) {
            IDS = IDS + selectRows[index].employeeID + ",";
        }

        IDS = IDS.substr(0,IDS.length - 1);

        $.ajax({
            async:false,
            type: "POST",
            url:"addRoleEmployees.html",
            data: {roleID:$roleID, IDS:IDS},
            dataType: "json",
            success: function(data){
                $table.bootstrapTable('refreshOptions',{pageNumber:1});
                top.layer.close($topIndex);
            }
        });
    }
}

var officeID;
$(function () {
    $.getJSON("../../treeView/getOfficeTreeView.html",function(data){
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
            url: '../../sys/role/getRoleEmployeeListForUndistributed.html',  //请求后台的URL（*）
            method: 'POST',                     //请求方式（*）
            contentType : "application/x-www-form-urlencoded",
            dataType:"json",
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 15],                 //可供选择的每页的行数（*）
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "employeeID",             //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            sortable: false,                    //是否启用排序
            sortOrder: "sort",                  //排序方式
            queryParams:function(params) {      //传递参数
                var _params = {
                    limit: params.limit,   //页面大小
                    offset: params.offset, //页码
                    account: $("#account").val(),
                    jobNumber: $("#jobNumber").val(),
                    realName: $("#realName").val(),
                    officeID: officeID,
                    loginLock: $("#loginLock").val(),
                    delFlag: $("#delFlag").val(),
                    roleID:$roleID
                }
                return _params;
            },
            columns: [
                {
                    checkbox: true,
                    align:'center',
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
                }]
        });
    };
    return oTableInit;
};

/**
 * ---------------------------------------------------------------------------------------------------------------------
 */

var oInit = new Object();
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

    oInit.Init = function () {
        //初始化组件事件
    };
    return oInit;
};

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            url: '../../sys/role/getRolePagination',  //请求后台的URL（*）
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
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            detailView: true,                   //是否显示父子表
            sortable: false,                    //是否启用排序
            sortOrder: "sort",                  //排序方式
            queryParams:function(params) {      //传递参数
                var _params = {
                    limit: params.limit,   //页面大小
                    offset: params.offset, //页码
                    isValid: $("#isValid").val()
                }
                return _params;
            },
            columns: [
                {
                    field:'roleID',
                    title:'序号',
                    align:'center',
                    width:50,
                    formatter:function(value, row, index){
                        return index + 1;
                    }
                },
                {
                    field: 'name',
                    title:'角色名称',
                    width:115,
                    align: 'center',
                    events:{
                        "click #edit":function (e, value, row, index) {
                            edit(e, value, row, index);
                        }
                    },
                    formatter:function(value, row, index) {
                        return [
                            '<a id="edit" href="javascript:;" title="编辑">' + value + '</a>&nbsp; '
                        ].join('');
                    }
                },
                {
                    field: 'validFlag',
                    title:'是否有效',
                    width:20,
                    align: 'center',
                    formatter:function(value, row, index){
                        return [
                            (value) ?
                                ('<span class="label label-success">有效</span>') :
                                ('<span class="label label-danger">失效</span>')
                        ].join("");
                    }
                },
                {
                    field: 'sort',
                    title:'排序',
                    width:30,
                    align: 'center'
                },
                {
                    field: 'createTime',
                    title:'创建时间',
                    width:160,
                    align: 'center',
                    formatter:function(value, row, index) {
                        return changeDateFormat(value);
                    }
                },
                {
                    field: 'remark',
                    title:'描述',
                    width:100,
                    align: 'center'
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
                        },
                        "click #viewDetailed":function(e, value, row, index) {
                            var paramMap = new Map();
                            paramMap.put("roleID", row.roleID);
                            wd.openDialog('选择菜单','360px','800px',"sys/role/select-permission", paramMap);
                        },
                        "click #employeeDistribute":function (e, value, row, index) {
                            var paramMap = new Map();
                            paramMap.put("roleID", row.roleID);
                            paramMap.put("table", $("#roleEmployee_" + index));
                            wd.openDialog('选择员工','1500px','780px',"sys/role/select-employee", paramMap);
                        },
                    },
                    formatter:function(value, row, index) {
                        return [
                            '<a id="edit" href="javascript:;" title="编辑"><i class="fa fa-edit"></i> </a>&nbsp; ',
                            '<a id="del" href="javascript:;" title="删除"><i class="fa fa-trash-o"></i> </a>&nbsp; ',
                            '<a id="viewDetailed" href="javascript:;" title="权限"><i class="fa fa-cog"></i></a>&nbsp;&nbsp; ',
                            '<a id="employeeDistribute" href="javascript:;" title="人员分配"><i class="fa fa-user-circle"></i></a>&nbsp;&nbsp; '
                        ].join('');
                    }
                }],
                onExpandRow:function(index, row, detail) {
                    oInit.InitSubTable(index, row, detail);
                }
        });
    };
    return oTableInit;
};

oInit.InitSubTable = function (index, roleRow, detail) {
    var cur_table = detail.html('<table id="roleEmployee_' + index + '"></table>').find('table');
    $(cur_table).bootstrapTable({
        url: '../../sys/role/getRoleEmployeeListForAssigned',
        method: 'get',
        queryParams: { roleID: roleRow.roleID },
        ajaxOptions: { roleID: roleRow.roleID },
        clickToSelect: false,
        uniqueId: "employeeID",
        pageSize: 10,
        pageList: [10, 25],
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
                width:65,
                align:'center',
                events:{
                    "click #delRoleEmployee":function (e, value, row, index) {
                        delRoleEmployee(e, value, roleRow, row, index, cur_table);
                    }
                },
                formatter:function(value, row, index) {
                    return [
                        '<a id="delRoleEmployee" href="javascript:;" title="删除"><i class="fa fa-trash-o"></i> </a>&nbsp; '
                    ].join('');
                }
            }]
    });
};

var width = "650px";
var height = "400px";
var url = "sys/role/role-form";

//打开新增角色窗口
$("#btn_add").click(function(){
    var paramMap = new Map();
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "add");
    wd.openDialog('新增角色',width,height,url,paramMap);
});

//打开编辑角色窗口
function edit(e, value, row, index) {
    var paramMap = new Map();
    paramMap.put("id", row.roleID);
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "edit");
    wd.openDialog('编辑角色',width,height,url,paramMap);
}

//删除员工
function del(e, value, row, index) {
    top.layer.confirm('确认要删除吗?',function(){
        $.post("../../sys/role/del", {id:row.roleID}, function(data){
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

//删除角色与员工的关系
function delRoleEmployee(e, value, roleRow, row, index, table) {
    top.layer.confirm('确认要删除吗?',function(){
        $.post("../../sys/role/delRoleEmployee", {roleID:roleRow.roleID, employeeID:row.employeeID}, function(data){
            data = JSON.parse(data);
            if (data.success) {
                table.bootstrapTable('refreshOptions',{pageNumber:1});
                top.layer.msg(data.message,{icon: 1,time:1500});
            } else {
                top.layer.msg(data.message,{icon: 2,time:2000});
            }
        });
    });
}

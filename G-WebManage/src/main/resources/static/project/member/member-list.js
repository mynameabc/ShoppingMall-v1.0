/**
 * ---------------------------------------------------------------------------------------------------------------------
 */
function search() {
    $('#table').bootstrapTable('refreshOptions',{pageNumber:1});
}

var memberRanksID;
$(function () {
    $.getJSON("../treeView/getMemberGradeTreeView",function(data){
        $('#memberGradeNode').treeview({
            data: data,
            levels: 5,
            onNodeSelected: function(event, treeNode) {
                memberRanksID = treeNode.id;
                search();
            }
        });
    });
})

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
        $("#parentID").change(function(){search();});
        $("#login_lock").change(function(){search();});
        $("#account").bind('keypress', function(event){if (event.keyCode == "13") {search();}});
    };
    return oInit;
};

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            url: 'getMemberList',          //请求后台的URL（*）
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
            showExport: true,
            exportDataType: 'all',
            exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xls', 'pdf'],
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "memberID",               //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            sortable: false,                    //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams:function(params) {      //传递参数
                var _params = {
                    limit: params.limit,   //页面大小
                    offset: params.offset, //页码
                    account: $("#account").val(),
                    memberRanksZTreeID: memberRanksID,
                    login_lock: $("#login_lock").val(),
                    parentID: $("#parentID").val()
                }
                return _params;
            },
            columns: [
                {
                    field:'memberID',
                    title:'序号',
                    align:'center',
                    width:60,
                    formatter:function(value, row, index){
                        return index + 1;
                    }
                },
                {
                    field: 'parentID',
                    title: '账号属性',
                    width:100,
                    align: 'center',
                    formatter:function(value, row, index){
                        return (value == 0) ?
                            ('<span class="label label-info">主账号</span>') :
                            ('<span class="label label-warning">子账号</span>');
                    }
                },
                {
                    field: 'mobile',
                    title: '手机号码',
                    width:150,
                    align: 'center',
                },
                {
                    field: 'name',
                    title:'会员名',
                    width:150,
                    align: 'center',
                },
                {
                    field: 'useMoney',
                    title:'可用余额',
                    width:150,
                    align: 'center',
                },
                {
                    field: 'storeCount',
                    title:'门店数量',
                    width:150,
                    align: 'center',
                },
                {
                    field: 'email',
                    title:'邮箱',
                    align: 'center',
                },
                {
                    field: 'register_time',
                    title: '注册时间',
                    width:160,
                    align: 'center',
                    formatter:function(value, row, index) {
                        return changeDateFormat(value);
                    }
                },
                {
                    field: 'login_lock',
                    title: '状态',
                    width:80,
                    align: 'center',
                    formatter:function(value, row, index) {
                        return [
                            (value) ?
                                ('<span class="label label-success">已启用</span>') :
                                ('<span class="label label-danger">已停用</span>')
                        ].join("");
                    }
                },
                {
                    field:null,
                    title:'操作',
                    align:'center',
                    width:100,
                    events:{
                        "click #memberStart":function(e, value, row, index) {   //启用会员
                            top.layer.confirm('确认要启用吗？',function(){
                                $.post("member_start", {memberID:row.memberID}, function(data){
                                    if (data) {
                                        $('#table').bootstrapTable('updateCell', {index:index, field:"login_lock", value:1});
                                        top.layer.msg('已启用!',{icon: 6,time:1500});
                                    }
                                });
                            });
                        },
                        "click #memberStop":function(e, value, row, index) {    //停用会员
                            top.layer.confirm('确认要停用吗?',function(){
                                $.post("member_stop", {memberID:row.memberID}, function(data){
                                    if (data) {
                                        $('#table').bootstrapTable('updateCell', {index:index, field:"login_lock", value:0});
                                        top.layer.msg('已停用!',{icon: 5,time:1500});
                                    }
                                });
                            });
                        },
                        "click #editPws":function (e, value, row, index) {      //修改密码
                            change_password(row.memberID, row.mobile);
                        },
                        "click #viewDetailed":function (e, value, row, index) { //查看会员详细信息
                            view_detailed(row.memberID);
                        }
                    },
                    formatter:function(value, row, index) {
                        return [
                            (row.login_lock == 0) ?
                                ("<a id='memberStart' title='启用' href='javascript:;'><i class='glyphicon glyphicon-ok'></i></a>&nbsp;&nbsp; ") :
                                ("<a id='memberStop' title='停用' href='javascript:;'><i class='glyphicon glyphicon-minus'></i></a>&nbsp;&nbsp; "),

                            '<a id="editPws" style="text-decoration:none" title="修改密码" href="javascript:;"><i class="glyphicon glyphicon-lock"></i></a>&nbsp;&nbsp; ',
                            '<a id="viewDetailed" style="text-decoration:none" title="个人详细信息" href="javascript:;"><i class="glyphicon glyphicon-eye-open"></i></a>&nbsp;&nbsp; '
                        ].join("");
                    }
                }]
        });
    };
    return oTableInit;
};
/**
 * ---------------------------------------------------------------------------------------------------------------------
 */
/*个人详细信息视图*/
function view_detailed(memberID) {
    var paramMap = new Map();
    paramMap.put("memberID", memberID);
    wd.openViewDialog('详细信息', '1200px','800px','member/member-detailed',paramMap);
}

/*密码-修改*/
function change_password(memberID, mobile){
    var paramMap = new Map();
    paramMap.put("mobile", mobile);
    paramMap.put("memberID", memberID);
    wd.openDialog('修改密码','650px','270px','member/change-password',paramMap);
}

/*密码-修改*/
/*
function change_password2(memberID, mobile){

    var w = "800";
    var h = "260";
    var url = "member/change-password.html";
    var title = "修改密码";

    if (title == null || title == '') {
        title=false;
    };
    if (url == null || url == '') {
        url="404.html";
    };
    if (w == null || w == '') {
        w=800;
    };
    if (h == null || h == '') {
        h=($(window).height() - 50);
    };
    top.layer.open({
        type: 2,
        area: [w+'px', h +'px'],
        fix: false, //不固定
        maxmin: true,
        shade:0.4,
        title: title,
        content: url,
        btn: ['确定', '关闭'],
        yes: function(index, layero){
            var iframeWin = window[layero.find('iframe')[0]['name']];

            //父窗口调用子窗口的JavaScript函数
            iframeWin.submit(index);
        },
        cancel: function(index){
            top.layer.close(index);
        },
        success:function(layero, index){
            var body = layer.getChildFrame('body', index);
            body.contents().find("#memberID").val(memberID);
            body.contents().find("#mobile").html(mobile);


            body.contents()
        }
    });
}
*/

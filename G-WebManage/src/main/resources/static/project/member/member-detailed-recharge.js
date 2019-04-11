
function Init(index, paramMap) {

    $("#memberID").val(paramMap.get("memberID"));
}

$(function () {

    $("#tab4_target").click(function(){
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();
    });

});

function rechargeTableSearch() {
    $('#rechargeTable').bootstrapTable('refreshOptions',{pageNumber:1});
}

var ButtonInit = function () {
    var oInit = new Object();
    oInit.Init = function () {
        //初始化组件事件
        $("#search").click(function(){rechargeTableSearch();});
    };
    return oInit;
};

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#rechargeTable').bootstrapTable({
            url: 'recharge/getRechargePagination',          //请求后台的URL（*）
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
            pageList: [10],                     //可供选择的每页的行数（*）
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "rechargeID",             //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            sortable: false,                    //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams:function(params) {      //传递参数
                var _params = {
                    limit: params.limit,   //页面大小
                    offset: params.offset, //页码
                    memberID:$("#memberID").val(),
                    startDate:$("#startDate").val(),
                    endDate:$("#endDate").val()
                }
                return _params;
            },
            columns: [
                {
                    field:'rechargeID',
                    title:'序号',
                    align:'center',
                    width:45,
                    formatter:function(value, row, index){
                        return index + 1;
                    }
                },
                {
                    field: 'mobile',
                    title: '手机号码',
                    width:125,
                    align: 'center'
                },
                {
                    field: 'mode',
                    title: '充值方式',
                    width:100,
                    align: 'center',
                    formatter:function(value, row, index) {
                        if (value == 1) {
                            return "微信";
                        } else if (value == 2) {
                            return "支付宝";
                        } else {
                            return "未知";
                        }
                    }
                },
                {
                    field: 'beforeMoney',
                    title: '充值前余额',
                    width:100,
                    align: 'center'
                },
                {
                    field: 'money',
                    title: '充值金额',
                    width:100,
                    align: 'center'
                },
                {
                    field: 'afterMoney',
                    title: '充值后余额',
                    width:100,
                    align: 'center'
                },
                {
                    field: 'platformSerialNumber',
                    title: '平台流水号',
                    width:150,
                    align: 'center'
                },
                {
                    field: 'createTime',
                    title: '充值时间',
                    width:160,
                    align: 'center',
                    formatter:function(value, row, index) {
                        return changeDateFormat(value);
                    }
                }]
        });
    };
    return oTableInit;
};

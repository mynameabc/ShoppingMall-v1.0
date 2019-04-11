/**
 * ---------------------------------------------------------------------------------------------------------------------
 */
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
    oInit.Init = function () {};
    return oInit;
};

var TableInit = function () {
    var $table;
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $table = $('#table').bootstrapTable({
            url: 'getOfficeList',
            striped: true,
            sidePagination: 'client',
            idField: 'officeID',
            parentIdField: 'parentID',
            treeShowField: 'name',
            columns: [
                {
                    field: 'name',
                    title: '机构名称',
                    align: 'left',
                    width:270,
                    events: {
                        "click #edit":function (e, value, row, index) {
                            edit(e, value, row, index);
                        }
                    },
                    formatter:function(value, row, index) {
                        return [
                            '<a id="edit" href="javascript:;" title="编辑">' + row.name + '</a>&nbsp; '
                        ].join('');
                    }
                },
                {
                    field: 'sort',
                    title: '排序',
                    align: 'center',
                    width:270,
                },
                {
                    field: 'useable',
                    title: '是否可用',
                    align: 'center',
                    width:270,
                    formatter:function(value, row, index) {
                        return (value) ?
                            ('<span class="label label-success">已启用</span>') :
                            ('<span class="label label-danger">已停用</span>');
                    }
                },
                {
                    field: 'master',
                    title: '负责人',
                    align: 'center',
                    width:270
                },
                {
                    field: 'phone',
                    title: '联系电话',
                    align: 'center',
                    width:270
                },
                {
                    field: 'type',
                    title: '机构类型',
                    align: 'center',
                    width:270,
                    formatter:function(value, row, index) {
                        var type;
                        switch(value)
                        {
                            case 1:
                                type = "公司";
                                break;
                            case 2:
                                type = "部门";
                                break;
                            case 3:
                                type = "小组";
                                break;
                            case 4:
                                type = "其它";
                                break;
                            default:
                                break;
                        }
                        return type;
                    }
                },
                {
                    field: 'remarks',
                    title: '备注',
                    align: 'center',
                },
                {
                    field:null,
                    title:'操作',
                    align:'center',
                    width:125,
                    events:{
                        "click #edit":function (e, value, row, index) {
                            edit(e, value, row, index);
                        },
                        "click #del":function (e, value, row, index) {
                            del(e, value, row, index);
                        },
                        "click #addChild":function(e, value, row, index){
                            addChild(e, value, row, index);
                        }
                    },
                    formatter:function(value, row, index) {
                        return [
                            '<a id="edit" href="javascript:;" title="编辑"><i class="glyphicon glyphicon-edit"></i> </a>&nbsp; ',
                            '<a id="del" href="javascript:;" title="删除"><i class="glyphicon glyphicon-trash"></i> </a>&nbsp; ',
                            '<a id="addChild" href="javascript:;" title="添加下级机构"><i class="glyphicon glyphicon-plus"></i></a> '
                        ].join('');
                    }
                }
            ],
            onClickRow: function (row, $element) {
                //点击记录触发
            },
            onLoadSuccess: function(data) {
                $table.treegrid({
                    /*                        initialState: 'collapsed',*/
                    treeColumn: 0,
                    expanderExpandedClass: 'glyphicon glyphicon-minus',
                    expanderCollapsedClass: 'glyphicon glyphicon-plus',
                    onChange: function() {
                        $table.bootstrapTable('resetWidth');
                    }
                });
            }
        });
    };
    return oTableInit;
};

var width = "800px";
var height = "570px";
var url = "sys/office/office-form";

//新增机构
$("#btn_add").click(function(){
    var paramMap = new Map();
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "add");
    wd.openDialog('新增机构',width,height,url,paramMap);
});

//添加下级机构
function addChild(e, value, row, index) {
    var paramMap = new Map();
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "addChild");
    paramMap.put("parentID", row.officeID);
    paramMap.put("parentName", row.name);
    wd.openDialog('新增下级机构',width,height,url,paramMap);
}

//编辑机构
function edit(e, value, row, index) {
    var paramMap = new Map();
    paramMap.put("id", row.officeID);
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "edit");
    wd.openDialog('编辑机构',width,height,url,paramMap);
}

//删除机构
function del(e, value, row, index) {
    top.layer.confirm('确认要删除吗?',function(){
        $.post("del", {id:row.officeID}, function(data){
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

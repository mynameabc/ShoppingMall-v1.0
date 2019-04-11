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
            url: 'getProductCategoryList',
            striped: true,
            sidePagination: 'client',
            idField: 'categoryID',
            parentIdField: 'parentID',
            treeShowField: 'name',
            columns: [
                {
                    field: 'name',
                    title: '名称',
                    align: 'left',
                    width:100,
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
                    field: 'icon_1',
                    title: '图标',
                    align: 'center',
                    width:100,
                    formatter:function(value, row, index) {
                        return [
                            '<span class="' + value + '"></span>'
                        ].join('');
                    }
                },
                {
                    field: 'icon_2',
                    title: '图标',
                    align: 'center',
                    width:100,
                    formatter:function(value, row, index) {
                        return [
                            '<span class="' + value + '"></span>'
                        ].join('');
                    }
                },
                {
                    field: 'icon_3',
                    title: '图标',
                    align: 'center',
                    width:100,
                    formatter:function(value, row, index) {
                        return [
                            '<span class="' + value + '"></span>'
                        ].join('');
                    }
                },
                {
                    field: 'showFlag',
                    title: '是否显示',
                    align: 'center',
                    width:100,
                    formatter:function(value, row, index) {
                        return (value) ?
                            ('<span class="label label-success">是</span>') :
                            ('<span class="label label-danger">否</span>');
                    }
                },
                {
                    field: 'sort',
                    title: '排序',
                    align: 'center',
                    width:100,
                },
                {
                    field:null,
                    title:'操作',
                    align:'center',
                    width:25,
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

                        result = "";
                        result = '<a id="edit" href="javascript:;" title="编辑"><i class="glyphicon glyphicon-edit"></i> </a>&nbsp; ';
                        result += '<a id="del" href="javascript:;" title="删除"><i class="glyphicon glyphicon-trash"></i> </a>&nbsp; ';
                        result += '<a id="addChild" href="javascript:;" title="新增下级分类"><i class="glyphicon glyphicon-plus"></i></a> ';

                        return result;
/*                        return [
                            '<a id="edit" href="javascript:;" title="编辑"><i class="glyphicon glyphicon-edit"></i> </a>&nbsp; ',
                            '<a id="del" href="javascript:;" title="删除"><i class="glyphicon glyphicon-trash"></i> </a>&nbsp; ',
                            '<a id="addChild" href="javascript:;" title="新增下级资源"><i class="glyphicon glyphicon-plus"></i></a> '
                        ].join('');*/
                    }
                }
            ],
            onClickRow: function (row, $element) {
                //点击记录触发
            },
            onLoadSuccess: function(data) {
                $table.treegrid({
//                    initialState: 'collapsed',// 所有节点都折叠
                    initialState: 'expanded',// 所有节点都展开，默认展开
                    treeColumn: 0,      //哪个字段前显示+号
                    expanderExpandedClass: 'glyphicon glyphicon-minus',
                    expanderCollapsedClass: 'glyphicon glyphicon-plus',
                    onChange: function() {
                        $table.bootstrapTable('resetWidth');
                    }
                });

//                $table.treegrid('getRootNodes').treegrid('expand');
            }
        });
    };
    return oTableInit;
};

var width = "650px";
var height = "750px";
var url = "productCategory/form";

//新增资源
$("#btn_add").click(function(){
    var paramMap = new Map();
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "add");
    wd.openDialog('新增分类',width,height,url,paramMap);
});

//添加下级资源
function addChild(e, value, row, index) {
    var paramMap = new Map();
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "addChild");
    paramMap.put("parentID", row.categoryID);
    paramMap.put("parentName", row.name);
    wd.openDialog('新增下级分类',width,height,url,paramMap);
}

//编辑资源
function edit(e, value, row, index) {
    var paramMap = new Map();
    paramMap.put("id", row.categoryID);
    paramMap.put("table", $('#table'));
    paramMap.put("handlerType", "edit");
    wd.openDialog('编辑分类',width,height,url,paramMap);
}

//删除资源
function del(e, value, row, index) {
    top.layer.confirm('确认要删除吗?',function(){
        $.post("del", {id:row.categoryID}, function(data){
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

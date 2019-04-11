(function ($) {
    "use strict";
    var CON_TYPE_NULL = "请选择";
    var BASEHtml = "<div class=\"dropdown\"><button class=\"btn dropdown-toggle form-control\" type=\"button\" data-toggle=\"dropdown\" data-ztree=\"check\" id=\"btnType\"> <span >请选择类别</span> <span class=\"caret\"></span></button> <div class=\"panel dropdown-menu selecttree\"> <div class=\"panel-body\"> <ul class=\"ztree\" ></ul></div><div class=\"panel-footer\"> <div class=\"btn-group btn-group-justified\"><a class=\"btn\" id=\"btnCancel\">取消</a><a class=\"btn btn-success\" id=\"btnOK\">确定</a></div></div></div></div>";
    var TreeIndex = 1;
    /**
     * selectTree插件基于jquery，bootstrap,ztree,使用时要引用相应的js
     *   param: {
     *      NullText:默认提示信息
     *      objTitle: "btnTitle", 选择后需要显示的控件I
     *      isRadio: false,        是否是单选
     *      url: "/Machine/KBS/GetContentTypes"   数据请求的URL 暂时基于post方式请求
     *      requestCallBack:数据获取后的回调方法
     *      succes：点击确定按钮的处理事件
     *      cancel：点击取消按钮的处理事件
     *      selected:单选选定回调
     *      isParentSelect:父级可选
     *  },
     * 使用：$("#type").selectTree();
     */

    function SelectTree(obj, params) {
        this.tiggerObj = obj;

        this.param = this.param || {};
        if (!this.param.NullText || this.param.NullText == "") {
            this.param.NullText = CON_TYPE_NULL;
        }
        this.param = $.extend(this.param, params);
        this.initDom();
        this.objTitle = $(this.tiggerObj).prev().children("button").children("span").eq(0);
        this.ishide = true;
        this.isCancel = true;
        if (this.param.title) {
            this.objTitle.text(this.param.title);
        }
        this.getContent();
        this.initValue();
        this.initEvent();
    }
    SelectTree.prototype.reload = function (params) {
        this.param = $.extend(this.param, params);
        //this.objTitle = $(this.tiggerObj).prev().children("button").children("span").eq(0);
        this.ishide = true;
        this.isCancel = true;
        if (this.param.title) {
            this.objTitle.text(this.param.title);
        }
        this.getContent();
        this.initValue();
        this.initEvent();
    };
    //初始化组件DOM元素
    SelectTree.prototype.initDom = function () {
        this.tiggerObj.css({ "height": "0.5px", "width": "0px", "padding": "0px", "margin": "0px" });
        this.tiggerObj.before(BASEHtml);

        if (this.param.isRadio) {
            $(".selecttree .panel-footer .btn").hide();
        }
    };
    //初始化事件定义
    SelectTree.prototype.initEvent = function () {
        var self = this;
        var isthis = false;
        //点击组件内部触发动作
        $(".dropdown .panel-body").click(function () {
            self.ishide = false;
            if (self.param.isRadio) {
                self.isCancel = false;
            }
        });
        if (!self.param.isRadio) {
            var btnCancel = $(this.tiggerObj).parent().find("#btnCancel");
            $(btnCancel).click(function () {
                //console.log("btnCancel");
                self.ishide = true;
                if (self.param.cancel) {
                    self.param.cancel();
                }
            });
            var btnOk = $(this.tiggerObj).parent().find("#btnOK");
            $(btnOk).click(function () {
                // console.log("btnOk");
                self.ishide = true;
                self.isCancel = false;
                if (self.param.succes) {
                    self.param.succes();
                }
            });
        }
        //组件消失时触发
        $(this.tiggerObj).parent().on("hide.bs.dropdown", function (e) {
            var node = self.TreeObj.getSelectedNodes()[0];
            if (self.param.isRadio && self.param.isParentSelect) {
                var selectedNodes = self.TreeObj.getSelectedNodes();
                if (self.param.selected) {
                    self.param.selected(selectedNodes);
                }
                var names = [];
                var id = [];
                var tid = [];
                var pname = "";
                var pid = "";
                $(selectedNodes).each(function (i, o) {
                    names.push(o.name);
                    id.push(o.id);
                    tid.push(o.tId);
                    var pnode = o.getParentNode();
                    if (pnode && pnode != null) {
                        pname += pnode.name;
                        if (pnode.value) {
                            pid += pnode.value;
                        } else {
                            pid += pnode.id;
                        }
                    }
                });
                var name = names.join(",");
                if (name == "")
                    name = self.param.NullText;
                $(self.objTitle).text(name);
                if (self.param.name) {
                    $("#" + self.param.name).val(name);
                }
                if (self.param.parentName) {
                    $("#" + self.param.parentName).val(pname);
                }
                if (self.param.parentValue) {
                    $("#" + self.param.parentValue).val(pid);
                }
                $(self.tiggerObj).val(id.join(","));
                $(self.tiggerObj).data("tids", tid.join(","));
                return;
            }
            if ((!self.ishide && !self.param.isRadio) || (self.param.isRadio && !self.ishide && node && node.isParent)) {
                self.ishide = true;
                if (self.param.isRadio) {
                    self.isCancel = true;
                }
                e.preventDefault();
            } else {
                if (!self.isCancel) {
                    var treeObj = self.TreeObj;
                    var selectedNodes;
                    if (self.param.isRadio) {
                        selectedNodes = treeObj.getSelectedNodes();
                    } else {
                        selectedNodes = treeObj.getCheckedNodes(true);
                    }
                    if (self.param.selected) {
                        self.param.selected(selectedNodes);
                    }
                    var names = [];
                    var id = [];
                    var tid = [];
                    var pname = "";
                    var pid = "";
                    $(selectedNodes).each(function (i, o) {
                        if (!o.isParent) {
                            names.push(o.name);
                            id.push(o.id);
                            tid.push(o.tId);
                            var pnode = o.getParentNode();
                            if (pnode && pnode != null) {
                                pname += pnode.name;
                                if (pnode.value) {
                                    pid += pnode.value;
                                } else {
                                    pid += pnode.id;
                                }
                            }
                        }
                    });
                    var name = names.join(",");
                    if (name == "")
                        name = self.param.NullText;
                    $(self.objTitle).text(name);
                    if (self.param.name) {
                        $("#" + self.param.name).val(name);
                    }
                    if (self.param.parentName) {
                        $("#" + self.param.parentName).val(pname);
                    }
                    if (self.param.parentValue) {
                        $("#" + self.param.parentValue).val(pid);
                    }
                    $(self.tiggerObj).val(id.join(","));
                    $(self.tiggerObj).data("tids", tid.join(","));
                }
            }

        });
        //组件显示时触发
        $(this.tiggerObj).parent().on("show.bs.dropdown", function (e) {
            // console.log($(self.tiggerObj).data("tids"));
            self.setCheckNode();
            self.isCancel = true;
        });
    };
    //设置选择node
    SelectTree.prototype.setCheckNode = function () {
        var self = this;
        if (self.param.isRadio) {
            var selectnode = self.TreeObj.getSelectedNodes();
            if (selectnode.length > 0) {
                self.TreeObj.cancelSelectedNode(selectnode[0]);
            }
        } else {
            self.TreeObj.checkAllNodes(false);
        }
        var check = $(self.tiggerObj).data("tids");
        if (check) {
            var tids = check.split(",");
            $(tids).each(function (i, o) {
                var node = self.TreeObj.getNodeByTId(o);
                if (self.param.isRadio) {
                    self.TreeObj.selectNode(node);
                } else {
                    self.TreeObj.checkNode(node, true, true);
                }
            });
        }
    };
    //获取树内容
    SelectTree.prototype.getContent = function () {
        var url = this.param.url;
        var self = this;
        $.ajax({
            "type": "Get",
            //"contentType": "application/json",
            "url": url,
            "async": false,
            //"dataType": "json",
            "success": function (resp) {
                var zNodes = [];
                var setting = treeSetting(self.param.isRadio);
                if (self.param.requestCallBack) {
                    zNodes = self.param.requestCallBack($.parseJSON(resp));
                } else {
                    zNodes = $.parseJSON(resp).data;
                }
                var treeContent = $(self.tiggerObj).prev().children().children(".panel-body").children(".ztree").eq(0);
                $(treeContent)[0].id = "tree" + TreeIndex;

                self.TreeObj = $.fn.zTree.init(treeContent, setting, zNodes);
                TreeIndex++;
                //  self.initValue();
            }
        });
    };
    //初始化组件值
    SelectTree.prototype.initValue = function () {
        var value = $(this.tiggerObj).val();
        var self = this;
        if (value && value != "") {
            var ids = value.split(',');
            //var nodes = [];
            var tids = [];
            var name = [];
            $(ids).each(function (i, o) {
                var node = self.TreeObj.getNodesByParam("id", o, null)[0];
                if (node && node != null) {
                    //nodes.push(node);
                    name.push(node.name);
                    tids.push(node.tId);
                }

            });

            if (tids.length > 0) {
                this.objTitle.text(name.join(','));
                $(this.tiggerObj).data("tids", tids.join(','))
            }
        } else {
            this.objTitle.text(this.param.NullText);
        }
    };
    //获取ztree设置
    function treeSetting(isradio) {
        if (isradio) {
            return {
                data: {
                    simpleData: {
                        enable: true
                    }
                }
            };
        } else {
            return {
                check: {
                    enable: true,
                    nocheckInherit: false
                },
                data: {
                    simpleData: {
                        enable: true
                    }

                }
            };
        }
    }

    $.fn.selectTree = function (options) {
        var select = new SelectTree(this, options);
        return select;
    };
})(jQuery);

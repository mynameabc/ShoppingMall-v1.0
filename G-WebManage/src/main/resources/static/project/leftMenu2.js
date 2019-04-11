$(function () {
    leftMenu();
});

//无子节点html
function aaa(node) {

    var html = "";
    html += "<li class='active'>";
    html += "<a href='" + node.href + "' target='main'>";
    html += "<i class='" + node.icon + "'></i>" + "<span class='menu-text'>" + node.name +"</span>";
    html += "</a><b class='arrow'></b>";
    html += "</li>";
    return html;
}

//有子节点html
function bbb(node) {

    var html = "";
    html += "<li class=''>";
    html += "<a href='" + node.href + "' class='dropdown-toggle'>";
    html += "<i class='" + node.icon + "'></i>" + "<span class='menu-text'>" + node.name +"</span>";
    html += "<b class='arrow fa fa-angle-down'></b>";
    html += "</a><b class='arrow'></b>";
    html += "<ul class='submenu'>";
    html += "<li class=''>";
    html += "<a href='" + node.href + "' target='main'>";
    html += "<i class='" + node.icon + "'></i>" + "<span class='menu-text'>" + node.name +"</span>";
    html += "</a><b class='arrow'></b>";
    html += "</li>";
    html += "</ul>";
    html += "</li>";
    return html;
}

//有子节点html
function Submenu(node) {

    var html = "";
    var childrenNodeList = node.children;
    if ((typeof childrenNodeList == "undefined")) { //无子节点时

        if (node.href == 'welcome') {
            html += "<li class='active'>";
        } else {
            html += "<li class=''>";
        }

        html += "<a href='" + node.href + "' target='main'>";
        html += "<i class='" + node.icon + "'></i>" + "<span class='menu-text'>" + node.name +"</span>";
        html += "</a><b class='arrow'></b>";
        html += "</li>";
    } else {

        html += "<li class=''>";
        html += "<a href='" + node.href + "' class='dropdown-toggle'>";
        html += "&nbsp;<i class='" + node.icon + "'>&nbsp;&nbsp;</i>" + "<span class='menu-text'>" + node.name +"</span>";
        html += "<b class='arrow fa fa-angle-down'></b>";
        html += "</a><b class='arrow'></b>";

        html += "<ul class='submenu'>";
        for (var index = 0; index < childrenNodeList.length; index++) {

            var childrenNode = childrenNodeList[index];
            if ((typeof childrenNode.children != "undefined")) {
                html += Submenu(childrenNode);
            } else {

                html += "<li class='lowestNode'>";
                html += "<a href='" + childrenNode.href + "' target='main'>";
                html += "&nbsp;&nbsp;<i class='" + childrenNode.icon + "'>&nbsp;&nbsp;</i>" + "<span class='menu-text'>" + childrenNode.name +"</span>";
                html += "</a><b class='arrow'></b>";
            }

            html += "</li>";
        }
        html += "</ul>";
        html += "</li>";
    }

    return html;
}

function leftMenu() {
    $("#leftMenu").html("");

    $.ajax({
        async:false,
        url: 'manageMenu2',
        type: 'GET',
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success: function(data){
            data = JSON.parse(data);
            var html = "";
            for (var index = 0; index < data.length; index++) {
                html += Submenu(data[index]);
            }
//            console.log(html);
            $("#leftMenu").html(html);
        }
    });

    /*
        $.getJSON("manageMenu",function (data) {
            var html = "";
            $.each(data, function(index, field){
                html += Submenu(data[index]);
            });
            $("#leftMenu").html(html);
        })
    */

}

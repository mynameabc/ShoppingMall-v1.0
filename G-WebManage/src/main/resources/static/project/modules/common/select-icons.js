
//有默认值就赋值
function Init(index, paramMap) {}

//传值回父窗口
function doSubmit(index, paramMap) {

    var iconValue = $("#iconValue").val();
    if(iconValue.length >= 1) {

        var iframeIndex = paramMap.get("iframeIndex");
        if(!((typeof iframeIndex != "undefined") && (typeof iframeIndex.valueOf() == "number") && (iframeIndex.toString().length > 0))){
            iframeIndex = 0;
        }

        top.parent.$("iframe")[iframeIndex].contentWindow.$("#icon").val("");
        top.parent.$("iframe")[iframeIndex].contentWindow.$("#icon_key").removeClass();
        top.parent.$("iframe")[iframeIndex].contentWindow.$("#icon_value").html("");

        top.parent.$("iframe")[iframeIndex].contentWindow.$("#icon").val(iconValue);
        top.parent.$("iframe")[iframeIndex].contentWindow.$("#icon_key").addClass(iconValue);
        top.parent.$("iframe")[iframeIndex].contentWindow.$("#icon_value").html(iconValue);
    }
    top.layer.close(index);
}

$(function (){

    $("div").hover(function(){
        $(this).css("background-color","#87CEFA");
        $(this).css("cursor","pointer");
    },function(){
        $(this).css("background-color","");
        $(this).css("cursor","");
    });

    $("div").click(function(){
        $("div").removeClass("select");
        $(this).addClass("select");

        var iconValue = $(this).find('i').attr('class').split(" ")[0] + ' ' + $(this).find('i').attr('class').split(" ")[1];
        $("#iconValue").val(iconValue);
    });
});

$(function(){
    //导航点击效果
    $(".main_nav .nav-pills li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
    });
    //鼠标移入显示二级菜单
    $(".main_nav .nav-pills li").mouseover(function(){
        var id=$(this).attr("data-target");
        if(id!=undefined){
            $(id).fadeIn('fast').siblings(".sec_nav").css('display','none');    //如果获取的id不为空，则让这个id对应的二级菜单显示
        }else{
            $(".sec_nav").each(function(){
                $(this).css('display','none');     //若对应的id为空，则不显示
            });
        }
    });
    var delay;
    $(".main_nav").mouseover(function(){
        clearTimeout(delay);    //防止二级菜单重复显示隐藏
    });
    $(".main_nav").mouseleave(function(){
        delay=setTimeout(function(){
            delay=$(".main_nav .sec_nav").fadeOut();    //当鼠标移出导航区域，导航隐藏
        },1000);
    });
    //高级搜索区域展开收缩
    $('#advance').click(function(){
        var advance_area=$('div.advance_search').css('display');
        if(advance_area=='none'){
            $('div.advance_search').slideDown();
            $(this).children('i').removeClass('icon-zhankai').addClass('icon-shousuo');
        }else{
            $('div.advance_search').slideUp();
            $(this).children('i').removeClass('icon-shousuo').addClass('icon-zhankai');
        }
    });
});
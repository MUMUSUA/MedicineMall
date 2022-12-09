$(function(){
    $.getJSON("index/catelog.json",function (data) {

        var ctgall=data;
        $(".header_main_left_a").each(function(){
            var ctgnums= $(this).attr("ctg-data");
            if(ctgnums){
                var panel=$("<ul class=\"row mx-0\"></ul>");
                var panelol=$(" <li class=\"h_title text-uppercase font-weight-bold border-bottom\"></li>");
                var  ctgnumArray = ctgnums.split(",");
                $.each(ctgnumArray,function (i,ctg1Id) {
                    var ctg2list= ctgall[ctg1Id];
                    $.each(ctg2list,function (i,ctg2) {
                        var cata2link=$("<a href='#' style= 'color: #111;' class='aaa'>"+ctg2.name+"  ></a>");


                        console.log(cata2link.html());
                        var li=$("<li></li>");
                        var  ctg3List=ctg2["catalog3List"];
                        var len=0;
                        $.each(ctg3List,function (i,ctg3) {
                            var cata3link = $("<a class=\"dropdown-item font-weight-bolder\" href=\"http://search.mall.com/list.html?catalog3Id="+ctg3.id+"\" style=\"color: #999;\">" + ctg3.name + "</a>");
                            li.append(cata3link);
                            len=len+1+ctg3.name.length;
                        });
                        if(len>=46&&len<92){
                            li.attr("style","height: 60px;");
                        }else if(len>=92){
                            li.attr("style","height: 90px;");
                        }
                        panelol.append(cata2link).append(li);

                    });

                });
                panel.append(panelol);
                $(this).after(panel);
                $(this).parent().addClass("header_li2");
                console.log($(".header_main_left").html());
            }
        });
    });
});
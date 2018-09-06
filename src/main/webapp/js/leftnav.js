$(document).ready(function(){

	$.ajax({
		type:"GET",
		url:"${ctx}/sys/getmenus",
		dataType:"json",
		success:function(data){
			console.log(data)
			for(var i = 0 ; i < data.length ; i ++){
				$("#menus").append(
						'<dl  id="menu-live" style="margin-top: 4px;">' + 
						'<dt>' +' <img width="20" alt="" src= "${ctx}/img/icon/1.png">'+
						"管理员" + '<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;'+'</i>' +
						'</dt>'+
						'<dd>' +
						'<ul>'+ 
						'<li>'+
						'<a  style="border: 1px solid #C8C8C8; border-radius: 10px;" '+
						'data-href="${ctx}/user/getadminconfig" '+
						'data-title="角色管理" href="javascript:void(0)">' +
						'<img alt="" width="20"src="${ctx}/img/icon/2.png"> '
						+"&nbsp;&nbsp;角色管理"
							+'</a>'+
						'</li>'+
						'</ul>'+
						'</dd>'+
						'</dl>'
				);
			}
			$(function(){
				getHTMLDate($("#top_time"));
				getskincookie();
				//layer.config({extend: 'extend/layer.ext.js'});
				Huiasidedisplay();
				var resizeID;
				$(window).resize(function(){
					clearTimeout(resizeID);
					resizeID = setTimeout(function(){
						Huiasidedisplay();
					},500);
				});
				
				$(".nav-toggle").click(function(){
					$(".Hui-aside").slideToggle();
				});
				$(".Hui-aside").on("click",".menu_dropdown dd li a",function(){
					if($(window).width()<768){
						$(".Hui-aside").slideToggle();
					}
				});
				/*左侧菜单*/
				$(".Hui-aside").Huifold({
					titCell:'.menu_dropdown dl dt',
					mainCell:'.menu_dropdown dl dd',
				});
				
				/*选项卡导航*/
				$(".Hui-aside").on("click",".menu_dropdown a",function(){
					Hui_admin_tab(this);
				});
				
				$(document).on("click","#min_title_list li",function(){
					var bStopIndex=$(this).index();
					var iframe_box=$("#iframe_box");
					$("#min_title_list li").removeClass("active").eq(bStopIndex).addClass("active");
					iframe_box.find(".show_iframe").hide().eq(bStopIndex).show();
				});
				$(document).on("click","#min_title_list li i",function(){
					var aCloseIndex=$(this).parents("li").index();
					$(this).parent().remove();
					$('#iframe_box').find('.show_iframe').eq(aCloseIndex).remove();	
					num==0?num=0:num--;
					tabNavallwidth();
				});
				$(document).on("dblclick","#min_title_list li",function(){
					var aCloseIndex=$(this).index();
					var iframe_box=$("#iframe_box");
					if(aCloseIndex>0){
						$(this).remove();
						$('#iframe_box').find('.show_iframe').eq(aCloseIndex).remove();	
						num==0?num=0:num--;
						$("#min_title_list li").removeClass("active").eq(aCloseIndex-1).addClass("active");
						iframe_box.find(".show_iframe").hide().eq(aCloseIndex-1).show();
						tabNavallwidth();
					}else{
						return false;
					}
				});
				tabNavallwidth();
				
				$('#js-tabNav-next').click(function(){
					num==oUl.find('li').length-1?num=oUl.find('li').length-1:num++;
					toNavPos();
				});
				$('#js-tabNav-prev').click(function(){
					num==0?num=0:num--;
					toNavPos();
				});
				
				function toNavPos(){
					oUl.stop().animate({'left':-num*100},100);
				}
				
			}); 
			
			
		},
		error:function(data){
		   layer.alert("error")
		}
	});
 
});

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico">
<link rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/attdence.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title><shiro:principal property="username"/></title>
<meta name="keywords" content="">
<meta name="description" content="">
</head>
<body>
	<header class="navbar-wrapper">
		<div class="navbar navbar-fixed-top">
			<div class="container-fluid cl">
				<img style="height: 30px; margin-top: 10px;" alt="领军智能"
					src="${ctx}/img/lingjunLogo.png"> <span
					class="logo navbar-slogan f-l mr-10 hidden-xs"></span> <a
					aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs"
					href="javascript:;">&#xe667;</a>
				<nav id="Hui-userbar"
					class="nav navbar-nav navbar-userbar hidden-xs">
					<ul class="cl">
						<li>超级管理员</li>
						<li class="dropDown dropDown_hover"><a href="#"
							class="dropDown"><shiro:principal property="username"/> <i class="Hui-iconfont">&#xe6d5;</i></a>
							<ul class="dropDown-menu menu radius box-shadow">
								<li><a onclick="changePwd()">修改密码</a></li>
								<li><a href="${ctx}/sys/out">退出</a></li>

							</ul></li>
					</ul>
				</nav>
				<!--  <a>四川领军智能科技有限公司</a> -->
			</div>
		</div>
	</header>
	<aside class="Hui-aside">
		<div class="menu_dropdown bk_2" id="menus">
			<!-- menus -->
		</div>
	</aside>
	<div class="dislpayArrow hidden-xs">
		<a class="pngfix" href="javascript:void(0);"
			onClick="displaynavbar(this)"></a>
	</div>
	<section class="Hui-article-box">
		<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
			<div class="Hui-tabNav-wp">
				<ul id="min_title_list" class="acrossTab cl">
					<li class="active"><span title="" data-href="welcome.html">首页</span>
						<em></em></li>
				</ul>
			</div>
			<div class="Hui-tabNav-more btn-group">
				<a id="js-tabNav-prev" class="btn radius btn-default size-S"
					href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a
					id="js-tabNav-next" class="btn radius btn-default size-S"
					href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a>
			</div>
		</div>
		<div id="iframe_box" class="Hui-article">
			<div class="show_iframe">
				<div style="display: none" class="loading"></div>
				<iframe scrolling="yes" frameborder="0" src="${ctx}/sys/welcome"></iframe>
			</div>
		</div>
	</section>

	<div class="contextMenu" id="Huiadminmenu">
		<ul>
			<li id="closethis">关闭当前</li>
			<li id="closeall">关闭全部</li>
		</ul>
	</div>


	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript"
		src="${ctx}/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/layer/layer.js"></script>

	<script type="text/javascript" src="${ctx}/static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/h-ui.admin/js/H-ui.admin.js"></script>
	<!--/_footer 作为公共模版分离出去-->

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript"
		src="${ctx}/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>

	<%-- <c:if test="${user != 'admin' }">
  <script src="${ctx}/js/nocode.js" ></script>
</c:if> --%>
</body>
<script>
function changePwd(){
	  layer.prompt({title: '输入新密码，并确认' , formType: 1},function(val, index){
		 
		 $.ajax({
			 type:'post',
			 url:'${ctx}/sys/changePwd',
			 data:{"pwd":val},
			  dataType:'json',
		  	   success:function(data){
		  		  if(data.code ==1){
		  			  layer.msg("更改成功,请重新登录" ,{time:1500},function(){
		  				 window.location.href = "${ctx}/sys/index";  
		  			  });
		  		  }
		  	   }
		 });
		  layer.close(index);
		});
}
</script>
<script type="text/javascript">
$(document).ready(function(){

	$.ajax({
		type:"GET",
		url:"${ctx}/sys/getmenus",
		dataType:"json",
		success:function(data){
			var  dlhtml =  '<dl  id="menu-live" style="margin-top: 4px;">' ;
			/* console.log(data) */
			for(var i = 0 ; i < data.length ; i ++){
					dlhtml  +=	'<dt>' +' <img width="20" alt="" src= "${ctx}/'+ data[i].icon+'"> '+
						data[i].title + '<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;'+'</i>' +
						'</dt>'+
						'<dd>' +
						'<ul>' ; 
             for(var j = 0 ; j <data[i].children.length ; j++){
	                dlhtml += '<li>'+
	                '<a  style="border: 1px solid #C8C8C8; border-radius: 10px;" '+
	                 'data-href="${ctx}/'+data[i].children[j].href+'" '+
	                 'data-title="'+data[i].children[j].title+'" href="javascript:void(0)">' +
	                 '<img alt="" width="20"src="${ctx}/'+data[i].children[j].icon+'"> '
	                 +"&nbsp;&nbsp;"+ data[i].children[j].title
		             +'</a>'+
	                 '</li>' ;}
			dlhtml +='</ul>'+'</dd>'+'</dl>';
			
			}
			
			$("#menus").append(dlhtml);
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
</script>

</html>
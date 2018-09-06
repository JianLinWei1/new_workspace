<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加角色</title>
<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" href="${ctx}/layui/css/layui.css">
<link rel="stylesheet" href="${ctx}/layer/theme/default/layer.css">
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript"  src="${ctx}/layui/layui.js"></script>

</head>
<body>
<div class="page-container">
<form class="layui-form" action="" method="POST">
	<div class="layui-form-item">
    <label class="layui-form-label">角色名：</label>
    <div class="layui-input-block">
      <input name="rolename" id="roleName" lay-verify="required" placeholder="请输入角色名" autocomplete="off" class="layui-input" type="text">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">角色描述：</label>
    <div class="layui-input-block">
      <input name="roledescription" lay-verify="required" placeholder="请输入描述" autocomplete="off" class="layui-input" type="text">
    </div>
  </div>
  <shiro:hasPermission name="sys:role:op">
  <div class="layui-form-item"  >
    <label class="layui-form-label">设为超管：</label>
    <div class="layui-input-block">
      <input name="flag" value="1" title="是"  type="radio">
        <input name="flag" value="0" title="默认不设为超级管理员" checked="checked" disabled="" type="radio">
    </div>
  </div>
  </shiro:hasPermission>
    <div class="layui-form-item" pane="">
    <label class="layui-form-label">分配权限：</label>
  	<div id="xtree1" style="width:250px;margin-left: 100px">
  </div> 
  </div>
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
    </div>
  </div>
  </form>
  </div>
</body>
<script type="text/javascript" src="${ctx}/layer/layer.js"></script>
<script type="text/javascript"   src="${ctx}/js/layui-xtree.js"></script>
<script> 

//角色名唯一性校验
	$("#roleName").blur(function(){
		$.ajax({
        type: "get",
        url: "${ctx}/role/roleOnly/"+$("#roleName").val(),
        success:function(data){
        	if(data.code!=0){
        		top.layer.msg(data.msg);
        		$("#roleName").val("");
        		$("#roleName").focus();
        	}
        }
    });
	})

layui.use(['form'], function(){
	 var form = layui.form  
	 
	 var xtree1 = new layuiXtree({
	      elem: 'xtree1'  //(必填) 放置xtree的容器id，不带#号
	      , form: form    //(必填) layui 的 from
	      , data:  '${ctx}/role/xtree'  //(必填) 数据接口，需要返回指定结构json字符串
	      , ckall: false   //启动全选功能，默认false
	      , ckallback: function () {}//全选框状态改变后执行的回调函数
	});
     
	 
  //监听提交
  form.on('submit(demo1)', function(data){
     /* layer.alert(JSON.stringify(data.field), {
      title: '最终的提交信息'
    })  */ 
	  var list=xtree1.GetChecked()
		//菜单id
		var mStr="";
		for(var i=0;i<list.length;i++){
			mStr+=list[i].value+",";
			if(xtree1.GetParent(list[i].value)!=null){
				mStr+=xtree1.GetParent(list[i].value).value+",";
				if(xtree1.GetParent(xtree1.GetParent(list[i].value).value)!=null){
	 				mStr+=xtree1.GetParent(xtree1.GetParent(list[i].value).value).value+",";
				}
			}else{
				mStr+=list[i].value+",";
			}
		}
		//去除字符串末尾的‘,’
		mStr=mStr.substring(0,mStr.length-1)
         /*  console.log(menu) */
        
          var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
         $.ajax({
          	type:'POST',
          	url:'${ctx}/role/addRole',
          	data:{"data":JSON.stringify(data.field) ,"menu":mStr},
          	dataType:'text',
          	success:function(data){
          		console.log(data)
          		top.layer.close(index);
      	    	layer.msg('提交成功', {time:1000});
      	    },
      	    error:function(msg){
      	    	top.layer.close(index);
      			if(msg.status == 404){
      				layer.alert("该用户名已经被使用")
      			}
      		}	
          });  
    
     
     
     
     
    return false;
  });
 
 
});
</script>
</html>
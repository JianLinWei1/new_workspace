<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>角色管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" href="${ctx}/layui/css/layui.css">
<link rel="stylesheet" href="${ctx}/layer/theme/default/layer.css">
<link rel="stylesheet" href="${ctx}/css/attdence.css">
<script type="text/javascript" src="${ctx}/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"  src="${ctx}/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/layer/layer.js"></script>
</head>
<body>
<div class="page-container">
	<div class="layui-btn-group demoTable">
  <button class="layui-btn" style="background-color: #4AC451;" id="insertUser" data-type=""><i class="layui-icon">&#xe654;</i>添加角色</button>
</div>
 <div class="layui-btn-group demoTable">
  <button class="layui-btn" style="background-color: #4AC451;" data-type="getCheckData"><i class="layui-icon">&#xe640;</i>批量删除</button>
</div>
<table class="layui-table" lay-data="{width: 1160, url:'${ctx}/role/getrolelist', page:true, id:'idTest'}" lay-filter="demo">
  <thead>
    <tr>
      <th lay-data="{type:'checkbox'}"></th>
      <th lay-data="{field:'roleid', width:180, sort: true}">ID</th>
      <th lay-data="{field:'rolename', width:180}">角色名</th>
      <th lay-data="{field:'roledescription', width:180}">角色描述</th>
      <th lay-data="{field:'hasModel', width:380}">拥有权限</th>
      <th lay-data="{ width:180, align:'center', toolbar: '#barDemo'}"></th>
    </tr>
  </thead>
</table>
 
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
</div>
</body>
<script type="text/javascript">
    function  deleteUser(obj){
    	var userName="<%=session.getAttribute("user")%>";
    	if(obj.id  == userName){
    		layer.alert('不能删除自己')
    	}else{
    		$.ajax({
        		type:'POST',
        		url:'${ctx}/user/deleteUser?userName='+obj.id,
        		dataType:"text",
        	    success:function(data){
        	    	layer.msg('删除成功', {time:1000}, function(){
        	    		window.location.reload();
        	    		});
        	    },
        		error:function(msg){
        			layer.msg("失败了")
        		}		
        	});
    	}
    }
    
    
    function updateAdmin(obj){
    	var userName="<%=session.getAttribute("user")%>";
    	if(obj.id  == userName){
    		layer.alert('不能编辑自己')
    	}else{
    		var index = layer.open({
    	  		  type: 2,
    	  		  content: '${ctx}/user/updateAdmin/'+obj.id,
    	  		  area: ['500px', '600px'],
    	  		  maxmin: false,
    	  		  title:'编辑管理员',
    	  		  cancel:function(){
    	  			  window.location.reload();
    	  		  }
    	  		});
    	}
    	
    }
    
    
    $('#insertUser').on('click', function(){
    	var index = layer.open({
    		  type: 2,
    		  content: '${ctx}/role/addRole',
    		  area: ['500px', '600px'],
    		  maxmin: false,
    		  title:'添加角色',
    		  cancel:function(){
    			  window.location.reload();
    		  }
    		});
    	
      });
</script>
<script>
layui.use('table', function(){
	  var table = layui.table;
	  //监听表格复选框选择
	  table.on('checkbox(demo)', function(obj){
	    console.log(obj)
	  });
	  //监听工具条
	  table.on('tool(demo)', function(obj){
	    var data = obj.data;
	    if(obj.event === 'detail'){
	      layer.msg('ID：'+ data.roleid + ' 的查看操作');
	    } else if(obj.event === 'del'){
	      layer.confirm('真的删除行么', function(index){
	        obj.del();
	        layer.close(index);
	      });
	    } else if(obj.event === 'edit'){
	      /* layer.alert('编辑行：<br>'+ JSON.stringify(data)) */
	      var x = ${admin.belongid};
	      if(data.belongid >= x){
	    	  if(data.flag==1){
		    	  layer.alert("不能编辑超级管理员")
		    	  return
		      }
	      }
	      
	      sessionStorage.setItem("editRole", JSON.stringify(data))
	    	var index = layer.open({
	    		  type: 2,
	    		  title:'编辑角色',
	    		  content: '${ctx}/role/editRole',
	    		  area: ['500px', '560px'],
	    		  maxmin: false,
	    		  closeBtn:2 ,
	    		  anim: 0,
	    		});
	    }
	  });
	  
	  var $ = layui.$, active = {
	    getCheckData: function(){ //获取选中数据
	      var checkStatus = table.checkStatus('idTest')
	      ,data = checkStatus.data;
	      layer.alert(JSON.stringify(data));
	    }
	    ,getCheckLength: function(){ //获取选中数目
	      var checkStatus = table.checkStatus('idTest')
	      ,data = checkStatus.data;
	      layer.msg('选中了：'+ data.length + ' 个');
	    }
	    ,isAll: function(){ //验证是否全选
	      var checkStatus = table.checkStatus('idTest');
	      layer.msg(checkStatus.isAll ? '全选': '未全选')
	    }
	  };
	  
	  $('.demoTable .layui-btn').on('click', function(){
	    var type = $(this).data('type');
	    active[type] ? active[type].call(this) : '';
	  });
	});
</script>
</html>
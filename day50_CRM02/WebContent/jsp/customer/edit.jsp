<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
//定义一个方法用于接收服务器端传来的json数据,为了使三个下拉框的内容能够整合到一个方法中,该方法中需要传入2个参数:数据字典类别代码 和 要追加到的下拉框id
function loadDict(dict_type_code,tagId,oldVal){
	var url = "${pageContext.request.contextPath}/baseDict_findByType";
	//type是方法findByType(type)中传入的参数名
	$.getJSON(url,{"type":dict_type_code},function(result){
		//现在resutl 是一个list集合  ，里面装的是字典对象
		$(result).each(function(index,element){
			//往下拉框追加内容
			$(tagId).append("<option value='"+element.dict_id+"'>"+element.dict_item_name +"</option>")
		});
		$(tagId).find("option[value='"+oldVal+"']").attr("selected","selected");
	})
}
//页面加载完就加载三个下拉框的值,调用上面的方法
$(function(){                   //这里参数的写法 不能写EL表达式
	loadDict("001","#cust_industry","${editCustomer.cust_industry.dict_id}")//客户行业
	loadDict("002","#cust_source","${editCustomer.cust_source.dict_id}")//客户来源
	loadDict("006","#cust_level","${editCustomer.cust_level.dict_id}")//客户级别
})
</script>


<TITLE>编辑客户</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css rel=stylesheet>
<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id=form1 name=form1 action="${pageContext.request.contextPath }/customer_update" method=post>
		<input type="hidden" name="cust_id" value="${editCustomer.cust_id}">
		<!-- 创建人和负责人 -->
		<input type="hidden" name="cust_create_id.user_id" value="${editCustomer.cust_create_id.user_id }">
		<input type="hidden" name="cust_user_id.user_id" value="${editCustomer.cust_user_id.user_id }">
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_019.jpg" border=0></TD>
					<TD width="100%" background="${pageContext.request.contextPath }/images/new_020.jpg" height=20></TD>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_021.jpg" border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15 background="${pageContext.request.contextPath }/images/new_022.jpg">
						<IMG src="${pageContext.request.contextPath }/images/new_022.jpg" border=0>
					</TD>
					<TD vAlign=top width="100%" bgColor=#ffffff>
						<TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
							<TR>
								<TD class=manageHead>当前位置：客户管理 &gt; 编辑客户</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>						
						<TABLE cellSpacing=0 cellPadding=5  border=0>
							<TR>
								<td>客户名称：</td>
								<td>
									<input class=textbox id=sChannel2 style="WIDTH: 180px; height:21px" maxLength=50 name="cust_name" value="${editCustomer.cust_name}">
								</td>
								<td>所属行业 ：</td>
								<td>
									<select name="cust_industry.dict_id" class=textbox id="cust_industry" style="WIDTH: 180px; height:21px">
										<option value="">---请选择---</option>
									</select>
								</td>
							</TR>							
							<TR>	
								<td>信息来源 ：</td>
								<td>
									<select name="cust_source.dict_id" class=textbox id="cust_source" style="WIDTH: 180px; height:21px">
										<option value="">---请选择---</option>
									</select>
								</td>
								<td>客户级别：</td>
								<td>
									<select name="cust_level.dict_id" class=textbox id="cust_level" style="WIDTH: 180px; height:21px">
										<option value="">---请选择---</option>
									</select>								
								</td>
							</TR>
							<TR>
								<td>联系地址 ：</td>
								<td>
									<input class="textbox" id="sChannel2" style="WIDTH: 180px; height:21px" maxLength="50" name="cust_address" value="${editCustomer.cust_address}">
								</td>
								<td>联系电话 ：</td>
								<td>
									<input class="textbox" id="sChannel2" style="WIDTH: 180px; height:21px" maxLength="50" name="cust_phone" value="${editCustomer.cust_phone}">
								</td>
							</TR>
							<TR>
								<td>客户资质 ：</td>
								<td>
									<img src="${pageContext.request.contextPath }/${editCustomer.cust_img}" width="100px" height="90px"/>
								</td>
								
							</TR>
							<tr>
								<td rowspan=2>
									<INPUT class=button id=sButton2 type=submit value=" 保存 " name=sButton2>
								</td>
							</tr>
						</TABLE>
					</TD>
					<TD width=15 background="${pageContext.request.contextPath }/images/new_023.jpg">
						<IMG src="${pageContext.request.contextPath }/images/new_023.jpg" border=0>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_024.jpg" border=0></TD>
					<TD align="center" width="100%" background="${pageContext.request.contextPath }/images/new_025.jpg" height=15></TD>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_026.jpg"	border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
	</FORM>
</BODY>
</HTML>

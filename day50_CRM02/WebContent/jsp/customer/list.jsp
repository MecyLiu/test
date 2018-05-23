<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>客户列表</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
<SCRIPT language=javascript>
	function to_page(page){//page:其实就是带过来要跳转的具体页码数
		if(page){//用于判定有没有带参数过来   在js里面,调用方法的时候,不是一定要带参数才可以调用方法!!!
			//走到这里,有两种其情况:前一页&后一页
			$("#page").val(page);
		}else{
			//能进入这里只有Go(没有携带page参数)
			var requestPage = $("#page").val();//获取输入的请求要到达的页面
			//val() 可以做赋值也可以做取值， 关键是看括号里面有没有东西 ， 有就是赋值 ，没有就是取值
			var totalPage = "${totalPage}";//总页数
			if(Number(requestPage) > Number(totalPage)){//将其格式转换为数字类型再进行比较,否则输入某些页面的时候也能跳转到根本就不存在的页面,显示的是空数据
				alert(+requestPage+ "请求页超过最大页!" +totalPage);
				return;//结束方法
			}
		}
		document.customerForm.submit();	//提交表单 customerForm是表单的name属性值
	}
	
	function changePageSize(){
		document.customerForm.submit();//在用户点击下拉框进行切换每页显示的数据条数的时候,重新提交一次表单,再走findByPage方法
	}
	
	/* 以下是list页面筛选条件下拉框的数据回显 */
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
		loadDict("001","#cust_industry","${cust_industry.dict_id}")//客户行业
		loadDict("002","#cust_source","${cust_source.dict_id}")//客户来源
		loadDict("006","#cust_level","${cust_level.dict_id}")//客户级别
	})
</SCRIPT>
<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id="customerForm" name="customerForm" action="${pageContext.request.contextPath }/customer_findByPage.action" method=post>	
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
								<TD class=manageHead>当前位置：客户管理 &gt; 客户列表</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>
						<TABLE borderColor=#cccccc cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
							<TBODY>
								<TR>
									<TD height=25>
										<TABLE cellSpacing=0 cellPadding=2 border=0>
											<TBODY>
												<TR>
													<TD>客户名称：</TD>
													<TD><s:textfield class="textbox" id="sChannel2" style="WIDTH: 80px" maxLength="50" name="cust_name"></s:textfield></TD>
													
													<td>所属来源 ：</td>
													<td>
														<select name="cust_source.dict_id" class=textbox id="cust_source" style="WIDTH: 100px;height:21px">
															<option value="">---请选择---</option>
														</select>
													</td>
													
													<td>所属行业 ：</td>
													<td>
														<select name="cust_industry.dict_id" class=textbox id="cust_industry" style="WIDTH: 100px;height:21px">
															<option value="">---请选择---</option>
														</select>
													</td>
													
													<td>所属级别 ：</td>
													<td>
														<select name="cust_level.dict_id" class=textbox id="cust_level" style="WIDTH: 100px;height:21px">
															<option value="">---请选择---</option>
														</select>
													</td>
													
													<TD><INPUT class=button id=sButton2 type=submit value=" 筛选 " name=sButton2></TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD>
										<TABLE id=grid style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc" cellSpacing=1 cellPadding=2 rules=all border=0>
											<TBODY>
												<TR style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
													<TD>客户名称</TD>
													<TD>客户级别</TD>
													<TD>客户来源</TD>
													<TD>所属行业</TD>
													<TD>联系地址</TD>
													<TD>负责人</TD>
													<TD>创建人</TD>
													<TD>联系电话</TD>
													<TD>操作</TD>
												</TR>
												<c:forEach items="${list}" var="customer">
												<TR style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
													<TD>${customer.cust_name }</TD>
													<TD>${customer.cust_level.dict_item_name }</TD>
													<TD>${customer.cust_source.dict_item_name }</TD>
													<TD>${customer.cust_industry.dict_item_name }</TD>
													<TD>${customer.cust_address }</TD>
													<TD>${customer.cust_user_id.user_name }</TD>
													<TD>${customer.cust_create_id.user_name }</TD>
													<TD>${customer.cust_phone }</TD>
													<TD>
													<a href="${pageContext.request.contextPath }/customer_edit?cust_id=${customer.cust_id}">修改</a>
													&nbsp;&nbsp;
													<%-- <a href="${pageContext.request.contextPath }/customer/CustomerServlet?method=removeCustomer&cust_id=${customer.cust_id}">删除</a> --%>
													<a href="javascript:del(${customer.cust_id })">删除</a>
													
													<script type="text/javascript">
														function del(id){
															var flag = confirm("确定要删除该客户吗?");
															if(flag){
																location.href = "${pageContext.request.contextPath }/customer_delete?cust_id="+id;
															}
														}
													</script>
													</TD>
												</TR>	
												</c:forEach>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD>
										<SPAN id=pagelink>
											<DIV style="LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right">
												共[<B>${totalPage}</B>]条记录,[<B>${totalPage}</B>]页
												,每页显示
												<select onchange="changePageSize()" name="pageSize">
												
												<%-- <option value="15" <c:if test="${pageSize==1 }">selected</c:if>>1</option> --%>
												<option value="5" <c:if test="${pageSize==5 }">selected</c:if>>5</option>
												<option value="10" <c:if test="${pageSize==10 }">selected</c:if>>10</option>
												<option value="15" <c:if test="${pageSize==15 }">selected</c:if>>15</option>
												<option value="20" <c:if test="${pageSize==20 }">selected</c:if>>20</option>
												</select>
												条
												[
												<%-- 注意： struts的标签里面不能写EL表达式。 只能写OGNL表达式
													EL表达式取值: ${user.name }
													OGNL : user.name --%>
													<s:if test = "currentPage == 1">
														前一页
													</s:if> 
													<s:else>
														<A href="javascript:to_page(${currentPage-1})">前一页</A>
													</s:else>
												]
												<B>${currentPage}</B>
												[
													<s:if test="currentPage == totalPage">
														后一页
													</s:if>
													<s:else>
														<A href="javascript:to_page(${currentPage+1})">后一页</A>
													</s:else>
												] 
												到
												<input type="text" size="3" id="page" name="currentPage" />
												页
												
												<input type="button" value="Go" onclick="to_page()"/>
											</DIV>
										</SPAN>
									</TD>
								</TR>
							</TBODY>
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
					<TD align=middle width="100%" background="${pageContext.request.contextPath }/images/new_025.jpg" height=15></TD>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_026.jpg" border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
	</FORM>
</BODY>
</HTML>

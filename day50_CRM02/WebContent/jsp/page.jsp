<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
function to_page(page){ //page:其实就是带过来要跳转的具体页码数
	if(page){ //用于判定有没有带参数过来。 aa(int age ) ---> aa(6)  to_page()
		//前一页  & 后一页
		$("#page").val(page);
	}else{
		//能进入这里只有 go
		var requestPage = $("#page").val();
		var totalPage = "${totalPage}";
		
		if(Number(requestPage) > Number(totalPage)){
			alert(+requestPage+" 请求页超过最大页! " + totalPage);
			return ;
		}
	}
	document.customerForm.submit();	
}

function changePageSize(){
	document.customerForm.submit();	
}
</script>
<body>
	<TR>
		<TD><SPAN id=pagelink>
				<DIV style="LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right">
					共[<B>${totalSize}</B>]条记录,[<B>${totalPage}</B>]页 ,每页显示 <select
						name="pageSize" onchange="changePageSize()">
						<option value="2" <c:if test="${pageSize==2 }">selected</c:if>>2</option>
						<option value="5" <c:if test="${pageSize==5 }">selected</c:if>>5</option>
						<option value="10" <c:if test="${pageSize==10 }">selected</c:if>>10</option>
						<option value="15" <c:if test="${pageSize==15 }">selected</c:if>>15</option>
					</select> 条 [
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
					<s:if test = "currentPage == totalPage">后一页</s:if>
					<s:else>
						<A href="javascript:to_page(${currentPage+1})">后一页</A>
					</s:else>
					] 到 
					<input type="text" size="3" id="page" name="currentPage" /> 
						页 
						<input type="button" value="Go" onclick="to_page()" />
				</DIV>
		</SPAN></TD>
	</TR>
</body>
</html>
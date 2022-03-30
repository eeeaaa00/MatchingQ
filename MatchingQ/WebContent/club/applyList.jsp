<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="model.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>���Ƹ� ȸ�� ����</title>
<link rel=stylesheet href="<c:url value='/css/clubunity.css' />" type="text/css">
<link rel=stylesheet href="<c:url value='/css/bootstrap.css' />">
<script>
function clubRemove() {
	return confirm("���� �����Ͻðڽ��ϱ�?");		
}
function userLogout(targetUri) {
	form1.action = targetUri;
	form1.submit();
}
function myPage(targetUri) {
	form1.action = targetUri;
	form1.submit();
}
</script>
</head>
<style>
#search {padding: 20px;}
h6 {padding:10px;}
li {padding: 8px 16px;}
</style>
<body>
<header class="blog-header py-4">
    <div class="row flex-nowrap justify-content-between align-items-center">
      <div class="col-4 pt-1"> 
      	<p class="text-info">&nbsp;&nbsp;&nbsp;naibulmunyeong</p>      
      </div>
      <div class="col-4 text-center">
        <h4 class="blog-header-logo text-dark">��û ��Ȳ</h4>
      </div>
      <div class="col-4 d-flex justify-content-end align-items-center">
	      <nav class="my-2 my-md-0 mr-md-3">
	      	<form name="form1" method="post" action="<c:url value='/user/logout' />">
		      	<input type="button" class="btn btn-sm btn-outline-secondary" value="�α׾ƿ�(&nbsp;${curUserId}&nbsp;)" onClick="userLogout('<c:url value='/user/logout' />')">&nbsp;
				<input type="button" class="btn btn-sm btn-outline-secondary" value="������" onClick="myPage('<c:url value='/user/view'>
				<c:param name='stuId' value='${curUserId}'/></c:url>')" />
			</form>
		  </nav>
      </div>
    </div>
  </header>
<div class="container-fluid">
  <div class="row">
    <nav class="col-md-2 d-none d-md-block bg-light sidebar">
      <div class="sidebar-sticky">
        <ul class="nav flex-column">
          <li class="nav-item">
            <a class="nav-link active p-2 text-muted"  href="<c:url value='/review/list ' />">
              HOME
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link p-2 text-muted" href="<c:url value='/review/list' />">
            	������õ
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link p-2 text-muted" href="<c:url value='/teamProject/list' />">
              	����ã��
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link p-2 text-muted" href="<c:url value='/club/list' />">
              	���Ƹ� ��õ
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link p-2 text-muted" href="<c:url value='/study/list' />">
              	Ʃ��ƩƼ
            </a>
          </li>
        </ul>
      </div>
    </nav>
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap pt-3 pb-2 mb-3">
      	<div class="table-responsive">
		  <table class="table table-shopping">
		   	<tr rowspan = "20">
			<td class="clubHead">ȸ�� ���</td>
			<td class="clubCell"> 
				<table>
				  <c:forEach var="stu" items="${stuList}">
				  <tr>
				  <td>
					  	${stu} 
				  </td>
				  </tr>
	  			</c:forEach>
	  			</table>
	  		</td>
		  </tr>
		   <tr rowspan = "20">
			<td class="clubHead">���� ��û ���</td>
			<td class="clubCell">
			<table>
			<c:forEach var="stu2" items="${stuList2}">
			<tr>
					<td>			  	
					  	${stu2}
					</td>
					 <td>
						<a class="btn btn-outline-primary" href="<c:url value='/club/apply'>
					    <c:param name='student_number' value='${stu2}'/>
					    <c:param name='club_code' value='${club.club_code}'/>
				 	    </c:url>" onclick="return apply();">����</a>
				 	    <a class="btn btn-outline-primary" href="<c:url value='/club/deny'>
					    <c:param name='student_number' value='${stu2}'/>
					    <c:param name='club_code' value='${club.club_code}'/>
				 	    </c:url>" onclick="return deny();">����</a>
					</td>
				</tr>
	  			</c:forEach>
	  			</table>
	  			<br>
	  			<tr>
	  			<td>
 	    <a class="btn btn-outline-primary" href="<c:url value='/club/list' />">���Ƹ� ���</a>
 	    <br><br>	   
 	    </td>
 	    </tr>
 	    <tr>
 	    <td>
 	    <!-- ������ ������ ��� exception ��ü�� ����� ���� �޽����� ��� -->
        <c:if test="${updateFailed}">
	      <font color="red"><c:out value="${exception.getMessage()}" /></font>
	    </c:if>    
		  </td>
		  </tr>
		  </table>
		  </div>
		  </div>
		  </main>
</div>
</div>
</body>
</html>
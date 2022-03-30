<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*, model.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("euc-kr");
	@SuppressWarnings("unchecked") 
	List<Review> reviewList = (List<Review>)request.getAttribute("reviewList");
	StudentDTO user = (StudentDTO)request.getAttribute("user");
	ProfDTO recommendedProf = (ProfDTO)request.getAttribute("prof");
	request.setAttribute("recommendedProf",(ProfDTO)request.getAttribute("prof"));
	//System.out.println(reviewList.get(2).getClassDto());
	//request.setAttribute("revClass", reviewList.get(2).getClassDto());
%>

<!DOCTYPE html>
<html>
<head>
<script>
function userLogout(targetUri) {
	form1.action = targetUri;
	form1.submit();
}
function myPage(targetUri) {
	form1.action = targetUri;
	form1.submit();
}
function search() {
	if (form2.searchKeyword.value == "") {
		alert("검색어를 입력하십시오.");
		form2.searchKeyword.focus();
		return false;
	} 	
	form2.submit(); // 아 form에서 review/search로 바로 안넘어가도록 어떻게 하지
}
function recommand(targetUri) {
	form.action = targetUri;
	form.submit();
}
</script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel=stylesheet href="<c:url value='/css/bootstrap.css' />">
<title>후기게시판</title>
</head>
<style>
li {padding: 8px 16px;}
td.check {color:red; font-size:smaller;}
</style>
<body>
<header class="blog-header py-4">
    <div class="row flex-nowrap justify-content-between align-items-center">
      <div class="col-4 pt-1"> 
      	<p class="text-info">&nbsp;&nbsp;&nbsp;naibulmunyeong</p>      
      </div>
      <div class="col-4 text-center">
        <h4 class="blog-header-logo text-dark">후기 게시판</h4>
      </div>
      <div class="col-4 d-flex justify-content-end align-items-center">
	      <nav class="my-2 my-md-0 mr-md-3">
	      	<form name="form1" method="post" action="<c:url value='/user/logout' />">
				<input type="button" class="btn btn-sm btn-outline-secondary" value="로그아웃(&nbsp;${curUserId}&nbsp;)" onClick="userLogout('<c:url value='/user/logout' />')">&nbsp;
				<input type="button" class="btn btn-sm btn-outline-secondary" value="내정보" onClick="myPage('<c:url value='/user/view'>
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
            	수업추천
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link p-2 text-muted" href="<c:url value='/teamProject/list' />">
              	팀플찾기
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link p-2 text-muted" href="<c:url value='/club/list' />">
              	동아리 추천
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link p-2 text-muted" href="<c:url value='/study/list' />">
              	튜터튜티
            </a>
          </li>
        </ul>
      </div>
    </nav>
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap pt-3 pb-2 mb-3">
      	<div id="search">
			<form class="form-inline mt-2 mt-md-0" name="form2" method="post" action="<c:url value='/review/search' />">
			    <input name = "searchKeyword" class="form-control mr-sm-2 w-90" type="text" placeholder="과목명 검색" aria-label="Search">
			    <input type="button" class="btn btn-outline-success my-2 my-sm" onClick="search()" value="Search">
			</form>
		</div>
	  </div>
	  <div class="table-center" align="center">
	  <h6>${profName}추천하는 교수님</h6>
	  <table>
		<tr>
			<td>
			<h2>${recommendedProf.profName} 교수님</h2>
			</td>
		</tr>
	  </table>
	  </div>
	  <br>
	  <div >
	 
	  <div class="table-responsive">
	  <table class="table table-shopping">
		<tr>
			<td width="500" align="center" bgcolor="E6ECDE" height="20">추천 강의평</td>
		</tr>
		<tr><td class="check">※강의평 추천은 다양한 추천을 위해 키워드와 무관하게 선택한 수업특성의 조합으로만 매칭됩니다.</td></tr>
		<c:forEach var="review" items="${reviewList}">  
		<hr>
			<table id="content">
				<tr>
					<th width="150">${review.className} - ${review.classDto.profName}<hr></th>
					<td></td>
				</tr>
				<tr>
					<th>과제</th>
					<td>${review.project}</td>
				</tr>
				<tr>
					<th>조모임</th>
					<td>${review.teamProject}</td>
				</tr>
				<tr>
					<th>학점비율</th>
					<td>${review.creditRate}</td>
				</tr>
				<tr>
					<th>총점</th>
					<td width="600">${review.lectureTotal}</td>
				</tr>
				
				<tr>
					<th>시험 횟수</th>
					<td>${review.numberOfExam}</td>
				</tr>
				<tr>
					<th>총평</th>
					<td>${review.lectureReview}</td>
				</tr>
			</table>
	 	 </c:forEach> 
		</table>
		</div>
		<div align="center">
		<br><br>
			<form name="form" class="blog-pagination" action="<c:url value='/review/match' />">
        <input type="button" value="다시 추천받기" onClick="recommand('<c:url value='/review/match' />')" class="btn btn-outline-primary">
    </form>
    <br><br>
    </div>
		</div>
	</main>
</div>
</div>	
</body>
</html>

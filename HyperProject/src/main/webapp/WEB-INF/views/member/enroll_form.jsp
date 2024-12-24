<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <style> 
        .content { 
            background-color:rgb(247, 245, 245);
            width:80%; 
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }
    </style>
</head>
<body>
    
<!-- 메뉴바 -->
    <jsp:include page="../common/menubar.jsp" />
    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>
            
            <script>
            	$(function(){
           			
            		const $idInput = $('#enroll-form > #userId');	// userId가 위에 메뉴바에 모달에도 같은 아이디로 있기 때문에 폼태그아이디 밑의 자식요소 아이디로 가리킨다
            		const $checkResult = $('#check-result');
            		const $joinBtn = $('#join-btn');
            		
            		// console.log($idInput);
            		
            		$idInput.keyup(function(){
            			
            			//console.log($idInput.val());
            			
            			if($idInput.val().length >= 5){
            				
            				$.ajax({
            					url : 'idcheck',
            					type : 'get',
            					data : {
            						userId : $idInput.val()
            					},
            					success : function(result){
            						
            						//console.log(result);
            						// NNNNN / NNNNY
            						
            						if(result.substr(4) === 'N'){ // 중복이다!
            							
            							$checkResult.show().css('color', 'crimson').text('사용할 수 없는 아이디입니다.');
            							$joinBtn.attr('disabled', true);
            							
            						} else{ // 중복아니다!
            							$checkResult.show().css('color', 'lightgreen').text('멋진 아이디네요!');
            							$joinBtn.removeAttr('disabled');
            						}
            					}
            				});
            			}
            		});
            	});
            
            </script>            
            
            <form action="sign-up.me" method="post">
                <div class="form-group" id="enroll-form">
                    <label for="userId">* ID : </label>
                    <input type="text" class="form-control" id="userId" placeholder="Please Enter ID" name="userId" required> <br>
                    <div id="check-result" style="font-size:0.7em; display:none;"></div>
                    <br>
                    
                    <label for="userPwd">* Password : </label>
                    <input type="password" class="form-control" id="userPwd" placeholder="Please Enter Password" name="userPwd" required> <br>
                    <label for="checkPwd">* Password Check : </label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required> <br>
                    <label for="userName">* Name : </label>
                    <input type="text" class="form-control" id="userName" placeholder="Please Enter Name" name="userName" required> <br>
                    <label for="email"> &nbsp; Email : </label>
                    <input type="text" class="form-control" id="email" placeholder="Please Enter Email" name="email"> <br>
                    <label for="age"> &nbsp; Age : </label>
                    <input type="number" class="form-control" id="age" placeholder="Please Enter Age" name="age"> <br> <!-- value= 해서 기본값 설정하는게 best -->
                    <label for="phone"> &nbsp; Phone : </label>
                    <input type="tel" class="form-control" id="phone" placeholder="Please Enter Phone (-없이)" name="phone"> <br>
                    <label for="address"> &nbsp; Address : </label>
                    <input type="text" class="form-control" id="address" placeholder="Please Enter Address" name="address"> <br>
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" id="Male" value="M" name="gender" checked>
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" id="Female" value="F" name="gender">
                    <label for="Female">여자</label> &nbsp;&nbsp;
                </div>
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary disabled" id="join-btn" >회원가입</button>
                    <button type="reset" class="btn btn-danger">초기화</button>
                </div>
            </form>
        </div>
        <br><br>
    </div>

    <!-- 푸터바 -->
    <jsp:include page="../common/footer.jsp" />

</body>
</html>
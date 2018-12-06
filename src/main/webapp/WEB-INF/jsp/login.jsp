<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<meta name="keywords" content="Flat Dark Web Login Form Responsive Templates, Iphone Widget Template, Smartphone login forms,Login form, Widget Template, Responsive Templates, a Ipad 404 Templates, Flat Responsive Templates" />
<link href="/css/style.css" rel='stylesheet' type='text/css' />
<link rel="shortcut icon" href="assets/img/titleIcon.png" type="/images/icon.gif" />
<!--webfonts-->
<link href='http://fonts.useso.com/css?family=PT+Sans:400,700,400italic,700italic|Oswald:400,300,700' rel='stylesheet' type='text/css'>
<link href='http://fonts.useso.com/css?family=Exo+2' rel='stylesheet' type='text/css'>
<!--//webfonts-->
</head>
<body>
<script>
//获取url参数
function getQueryString(name) {
var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
var r = window.location.search.substr(1).match(reg);
if (r != null) return unescape(r[2]); return null;
}
//登录函数
var doSubmit= function(){
 var userName=document.getElementById("userName").value;
 var password=document.getElementById("password").value;
 var xhr = new XMLHttpRequest();
     xhr.open("get","http://sso.piaoniu.com:7777/ssoapi/user/login?id="+userName+"&password="+password,true);
     xhr.onreadystatechange = callback;
     xhr.send(null);
     function callback() {
             if (xhr.readyState == 4) {//readyState表示文档加载进度,4表示完毕
             alert(xhr.responseText);
             window.location.href=window.atob(getQueryString("originUrl"))
             }
     }
 };


</script>

 <h1>票牛单点登录</h1>
<div class="login-form">
	<div class="close"> </div>
		<div class="head-info">
			<label class="lbl-1"> </label>
			<label class="lbl-2"> </label>
			<label class="lbl-3"> </label>
		</div>
			<div class="clear"> </div>
	<div class="avtar">
		<img src="/images/avtar.png" />
	</div>
			<form>
					<input id="userName" type="text" class="text" value="" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = '';}" >
						<div class="key">
					<input id="password" type="password" value="" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = '';}">
						</div>
			</form>
	<div class="signin">
		<input type="submit" value="Login" onClick="doSubmit()">
	</div>
</div>
</body>
</html>

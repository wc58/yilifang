var E3MALL = {
	checkLogin : function(){
		var _ticket = $.cookie("COOKIE_COKEN_KEY");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8880/user/getUser/" + _ticket + ".u",
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到宜立方购物网！<a href=\"http://localhost:8880/user/logout.u?COOKIE_COKEN_KEY="+_ticket+"\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}else if(data.status == 201){
					var username = data.msg;
					var html = "<a href=\"http://localhost:8880/user/login.u\">"+ username +",请登录</a>"
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	E3MALL.checkLogin();
});
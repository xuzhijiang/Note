<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>测试验证码</title>
    <script src="/js/jquery-3.2.1.js"></script>
  </head>
  <body>
  <h2 style="text-align: center;">测试验证码的生成</h2>
  <div style="margin: 0 auto; text-align: center">
    <img src="code.jsp" alt="" id="code" />
    <br/>
    <a href="javascript:void(0);" onclick="changeCode()">看不清？点我</a>
  </div>
  <hr/>
  <h3 style="text-align: center">使用kaptcha框架生成验证码</h3>
  <div style="text-align: center">
      <input type="text" name="kaptcha" value="" id="userInput"/>
      <img src="/kaptcha.jpg" />
      <button id="btnCheck">检测验证码</button>
  </div>
<script>
    function changeCode() {
        document.getElementById("code").src = "code.jsp?d="+new Date().getTime();
    }
    $("#btnCheck").on("click",function () {
        $.ajax({
            type: "POST",
            url: "/ValidateCodeServlet",
            data: "kaptcha=" + $("#userInput").val().toString(),
            success: function (data) {
              console.info(data);
              alert(data);
            }

        })
    });
</script>
  </body>
</html>

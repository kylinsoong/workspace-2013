<html>
<head><title>JVM Hang Lab 1</title></head>
<body>
<form action="./CountServlet">
<%
Object count = session.getAttribute("count");
if(count != null) out.println("Count = " + count);
%>
<input type="submit" value="count"> 
</form>
</body>
</html>
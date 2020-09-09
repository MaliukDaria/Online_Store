<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
<h2>Add new product to the catalog</h2>
<form method="post" action="${pageContext.request.contextPath}/products/add">
    Name: <input type="text" placeholder="name" name="name"> <br/> <br/>
    Price: <input type="number" placeholder="price" step="0.1" name="price"> <br/> <br/>
    <button type="submit">Add</button>
</form>
</body>
</html>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<jsp:include page="header/header.jsp"></jsp:include>
<br/>
<a href="${pageContext.request.contextPath}
/users/injectData">Inject test data into the DB</a>
</body>
</html>

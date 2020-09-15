<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Home Page =)</h1>
<h2> ${date}</h2>
<a href="${pageContext.request.contextPath}
/users/injectData">Inject test data into the DB</a>
<br/> <br/>
<a href="${pageContext.request.contextPath}/users/registration"><button>Register user</button></a> <br/> <br/>
<a href="${pageContext.request.contextPath}/users/all"><button>All users</button></a> <br/> <br/>
<a href="${pageContext.request.contextPath}/products/all"><button>Product catalog for user</button></a> <br/> <br/>
<a href="${pageContext.request.contextPath}/products/manage"><button>Product catalog for admin</button></a> <br/> <br/>
<a href="${pageContext.request.contextPath}/orders/user"><button>User orders</button></a> <br/> <br/>
<a href="${pageContext.request.contextPath}/orders/all"><button>All orders</button></a> <br/> <br/>
<a href="${pageContext.request.contextPath}/home-page"><button>Home Page</button></a> <br/> <br/>
</body>
</html>

<!DOCTYPE html>
<html>
<style>
    <%@include file="../styles/addProduct.css"%>
</style>
<body>
<h3>Add new product to the catalog</h3>
<p>Fill out all required fields:</p>
<div class="container">
    <form method="post" action="${pageContext.request.contextPath}/products/add">
        <label for="name">Name</label>
        <input type="text" id="name" name="name" placeholder="Name">
        <label for="price">Price</label> </br>
        <input type="number" id="price" step="0.1" name="price" placeholder="Price">
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>

<!DOCTYPE html>
<html>
<style>
    body {
        font-family: Arial;
    }

    input[type=text], select {
        width: 75%;
        padding: 12px 20px;
        margin: 8px 0;
        display: block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    input[type=number], select {
        width: 40%;
        padding: 12px 20px;
        margin: 8px 0;
        display: block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    input[type=submit] {
        width: 100%;
        background-color: #34432f;
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    input[type=submit]:hover {
        background-color: #34432f;
    }

    div.container {
        width: 30%;
        border-radius: 5px;
        background-color: #f2f2f2;
        padding: 20px;
    }
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

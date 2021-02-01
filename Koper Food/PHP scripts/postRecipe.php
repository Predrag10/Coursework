<?php
//String url = "https://www.studenti.famnit.upr.si/~89171025/postRecipe.php?name="+name+"&description="+description+"&difficulty="+difficulty+"&ingredients="+ingridients+"&markets="+markets+"&restaurants="
//echo $query;

	$conn = new mysqli("localhost","mit_uporabnik", "FamnitMIT19", "mit_19");
 		if (!$conn) {
    		 die("Error");
 		}
	
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	
	}

	$name = $_GET['name'];
	$description = $_GET['description'];
	$price = $_GET["price"];
	$difficulty = $_GET['difficulty'];
	$ingridients = $_GET['ingredients'];
	$markets = $_GET['markets'];
	$restaurants = $_GET['restaurants'];
	$userID = $_GET["username"];

	$sql = "INSERT INTO recipesKoperFood (name, description, price, difficulty, ingridients, markets, restaurants, userID) VALUES ('$name', '$description', '$price', '$difficulty', '$ingridients', '$markets', '$restaurants', '$userID')";

	if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
	} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
 $conn->close();
 ?>
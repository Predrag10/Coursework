<?php
//String url = "https://www.studenti.famnit.upr.si/~89171025/postRecipe.php?name="+name+"&description="+description+"&difficulty="+difficulty+"&ingridients="+ingridients+"&markets="+markets+"&restaurants="+restaurants
//echo $query;

	$conn = new mysqli("localhost","mit_uporabnik", "FamnitMIT19", "mit_19");
 		if (!$conn) {
    		 die("Error");
 		}
	
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	
	}

	$name = $_GET['name'];
	$birthday = $_GET['birthday'];
	$email = $_GET["email"];
	$username = $_GET['username'];
	$password = $_GET['password'];

	$sql = "INSERT INTO usersKoperFood (name, birthday, email, username, password) VALUES ('$name', '$birthday', '$email', '$username', '$password')";

	if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
	} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
 $conn->close();
 ?>
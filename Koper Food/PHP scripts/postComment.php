<?php
//String url = "https://www.studenti.famnit.upr.si/~89171025/postComment.php?comment="+comment+"&recipeID="+recipeID"
//echo $query;

	$conn = new mysqli("localhost","mit_uporabnik", "FamnitMIT19", "mit_19");
 		if (!$conn) {
    		 die("Error");
 		}
	
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	
	}

	$comment = $_GET['comment'];
	$recipeID = $_GET['recipeID'];
	$user = $_GET["username"];

	$sql = "INSERT INTO commentsKoperFood (recipeID, comment, userID) VALUES ('$recipeID', '$comment', '$user')";

	if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
	} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
 $conn->close();
 ?>
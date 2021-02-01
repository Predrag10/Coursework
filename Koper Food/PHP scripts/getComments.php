<?php
//String url = "https://www.studenti.famnit.upr.si/~89171025/getComments.php?recipeID=1
//echo $query;

	$conn = new mysqli("localhost","mit_uporabnik", "FamnitMIT19", "mit_19");
 		if (!$conn) {
    		 die("Error");
 		}
	
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	
	}

    $recipeID = $_GET["recipeID"];


$sql = "SELECT id, recipeID, comment, thumbsUp, thumbsDown, userID  FROM commentsKoperFood WHERE recipeID=$recipeID";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["id"]. "%" . $row["recipeID"]. "%" . $row["comment"]. "%" . $row["thumbsUp"]. "%" . $row["thumbsDown"]. "%" . $row["userID"]. "<br>";
    }
} else {
    echo "No entry";
}

 $conn->close();
 ?>
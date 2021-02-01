<?php
//String url = "https://www.studenti.famnit.upr.si/~89171025/updateLike.php?commentID=
//echo $query;

	$conn = new mysqli("localhost","mit_uporabnik", "FamnitMIT19", "mit_19");
 		if (!$conn) {
    		 die("Error");
 		}
	
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	
	}

$commentID = $_GET["commentID"];
$sql = "UPDATE commentsKoperFood SET thumbsUp = thumbsUp + 1 WHERE id = $commentID";

if ($conn->query($sql) === TRUE) {
    echo "Record updated successfully";
} else {
    echo "Error updating record: " . $conn->error;
}

 $conn->close();
 ?>
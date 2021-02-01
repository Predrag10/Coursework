<?php
//String url = "https://www.studenti.famnit.upr.si/~89171025/getRecipes.php?recipeNo=1
//echo $query;

	$conn = new mysqli("localhost","mit_uporabnik", "FamnitMIT19", "mit_19");
 		if (!$conn) {
    		 die("Error");
 		}
	
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	
	}


$sql = "SELECT id, Name, Location, City, Class, Reviews, Rating, LocationRating, Cleanliness, Service, Value  FROM hotels";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["id"]. "%" . $row["Name"]. "%" . $row["Location"]. "%" . $row["City"]. "%" . $row["Class"]. "%" . $row["Reviews"]. "%" . $row["Rating"]. "%" . $row["LocationRating"]. "%" . $row["Cleanliness"]. "%" . $row["Service"]. "%" . $row["Value"] . "<br>";
    }
} else {
    echo "No entry";
}

 $conn->close();
 ?>
<?php
//String url = "https://www.studenti.famnit.upr.si/~89171025/getRecipesDifficulty.php?orderBy=
//echo $query;

	$conn = new mysqli("localhost","mit_uporabnik", "FamnitMIT19", "mit_19");
 		if (!$conn) {
    		 die("Error");
 		}
	
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	
	}

$orderBy = $_GET["orderBy"];

if ($orderBy == "high_to_low") {
    $sql = "SELECT id, name, description, price, difficulty, ingridients, markets, restaurants, userID  FROM recipesKoperFood ORDER BY difficulty DESC";
    $result = $conn->query($sql);
}

if ($orderBy == "low_to_high") {
    $sql = "SELECT id, name, description, price, difficulty, ingridients, markets, restaurants, userID  FROM recipesKoperFood ORDER BY difficulty ASC";
    $result = $conn->query($sql);
}

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row["id"]. "%" . $row["name"]. "%" . $row["description"]. "%" . $row["price"]. "%" . $row["difficulty"]. "%" . $row["ingridients"]. "%" . $row["markets"]. "%" . $row["restaurants"]. "%" . $row["userID"]. "<br>";
    }
} else {
    echo "No entry";
}

 $conn->close();
 ?>
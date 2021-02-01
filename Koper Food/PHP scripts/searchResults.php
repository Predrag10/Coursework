<?php
//String url = "https://www.studenti.famnit.upr.si/~89171025/searchResults.php?search=SALMON
//echo $query;

	$conn = new mysqli("localhost","mit_uporabnik", "FamnitMIT19", "mit_19");
 		if (!$conn) {
    		 die("Error");
 		}
	
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	
	}

$search = $_GET["search"];

$sql = "SELECT id, name, description, price, difficulty, ingridients, markets, restaurants, userID  FROM recipesKoperFood";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
    	if(strpos(strtolower($row['name']), strtolower($search)) !== false){
        echo $row["id"]. "%" . $row["name"]. "%" . $row["description"]. "%" . $row["price"]. "%" . $row["difficulty"]. "%" . $row["ingridients"]. "%" . $row["markets"]. "%" . $row["restaurants"]. "%" . $row["userID"]. "<br>";
    	}
    }

} else {
    echo "No entry";
}

 $conn->close();
 ?>
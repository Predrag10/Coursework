<?php
//String url = "https://www.studenti.famnit.upr.si/~89171025/login.php?username=&password=
//echo $query;

	$conn = new mysqli("localhost","mit_uporabnik", "FamnitMIT19", "mit_19");
 		if (!$conn) {
    		 die("Error");
 		}
	
	if ($conn->connect_error) {
    	die("Connection failed: " . $conn->connect_error);
	
	}

$deny = true;
$username = $_GET["username"];
$password = $_GET["password"];

$sql = "SELECT username, password  FROM usersKoperFood";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while ($row = $result->fetch_assoc()) {
    	if (($row["username"] === $username) && ($row["password"] === $password)) {
        	echo "Login successful";
        	$deny = false;
    	}
    }
}

if($deny) {
	echo "Login denied";
}

 $conn->close();
 ?>
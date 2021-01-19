
<?php
//test URL  http://www.studenti.famnit.upr.si/~89171101/RealFakeDatabaseMaster.php?name=image1&perc=100&rf=real
//echo $query;

if(isset($_GET['name'])){
	$dbh = pg_connect("host=localhost dbname=89171101 user=OPBvaje port=5432 password=OPBvaje");
 		if (!$dbh) {
    		 die("Error");
 		} 

	//echo "Connected successfully";
	$name=$_GET['name'];
	$sql = "SELECT * FROM public.scores WHERE pname='$name'";
	//echo $sql;
 	$result = pg_query($sql);
 		if (!$result) {
    		die("Error");
 		}
 	$row = pg_fetch_assoc($result);
 	$realvot=0;
 	$realper=0;
 	$fakeper=0;
 	$fakevot=0;


 	if($_GET['rf'] == "real"){
 		$realper= (string) ((((int) $row['realvoteper'] * (int) $row['realvotetot']) + (int) $_GET['perc'])/((int) $row['realvotetot'] +1));

 		$realvot= (string) ((int) $row["realvotetot"] + 1);
 		$sql="UPDATE public.scores SET realvoteper = '$realper', realvotetot='$realvot' WHERE pname='$name'";
 	}else{
 		$fakeper= (string) ((((int) $row['fakevoteper'] * (int) $row['fakevotetot']) + (int) $_GET['perc'])/((int) $row['fakevotetot'] +1));

 		$fakevot= (string) ((int) $row["fakevotetot"] + 1);
 		$sql="UPDATE public.scores SET fakevoteper = '$fakeper', fakevotetot='$fakevot' WHERE pname='$name'";
 	}
 	$result = pg_query($sql);
 	if (!$result) {
    		die("Error");
 		}
 	$sql = "SELECT * FROM public.scores WHERE pname='$name'";
 	$result = pg_query($sql);
 		if (!$result) {
    		die("Error");
 		}
 	$row = pg_fetch_assoc($result);

 	$realper=(int) (((float) $row['realvotetot']/( (float) $row['realvotetot'] + (float) $row['fakevotetot']))*100);
 	$fakeper=100 - $realper;
 	
 	$fres=$realper."I".$row['realvoteper']."I".$fakeper."I".$row['fakevoteper'];

 	//echo $row['real']." ";


 	if($row['real'] == "true"){
 		$fres=$fres."IREAL";
 	}else{
 		$fres=$fres."IFAKE";
 	}
 	echo $fres;
 	



}
		

 ?>

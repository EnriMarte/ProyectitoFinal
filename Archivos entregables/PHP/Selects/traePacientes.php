<?php
require_once('../conexion.php');


$db= connect();
$statement=$db->prepare("SELECT * FROM pacientes");
$statement->execute();
$results=$statement->fetchAll(PDO::FETCH_ASSOC);
$json=json_encode($results);

echo $json;


/*try {

// Create the query - here we grab everything from the table
$stmt = $db->query('SELECT * from '.$table);
// Close connection to database
$db = NULL;
while($rows = $stmt->fetch()){
echo "<tr><td>". $rows['Name'] . "</td><td>" . $rows['Age'] . "</td></tr>";
};}



}
*/?>
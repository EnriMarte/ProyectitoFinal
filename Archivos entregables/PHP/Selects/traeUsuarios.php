<?php
require_once('../conexion.php');


$db= connect();
$stm=$db->prepare("SELECT * FROM usuarios");
$stm->execute();
$results2=$stm->fetchAll(PDO::FETCH_ASSOC);
$json2=json_encode($results2);

echo $json2;

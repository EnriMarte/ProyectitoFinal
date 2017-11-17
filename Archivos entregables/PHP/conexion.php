<?php

function connect(){
try{
$db = new PDO('mysql:host=localhost;dbname=proyecto;charset=utf8mb4', 'root', 'albi');
$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
return $db;
}catch(PDOException $e){
echo 'ERROR';
}
}
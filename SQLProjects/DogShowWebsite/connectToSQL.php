<?php
//this is a class which hides my passwords, if the user access's the F12 command to view the html code of my website
//this stops the passwords being visible by not having it in the same class as the main code, so it isn't visible
$servername = "selene.hud.ac.uk";
$username = "u2058703";
$password = "KJ30sep21kj";
$dbname = "u2058703";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} else {
    echo "";
}

?>
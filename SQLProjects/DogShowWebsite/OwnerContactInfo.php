<?php
if(isset($_GET['ID'])) {

    require "../Coursework3/connectToSQL.php";
    //this code adds another column to the table owners in the PHPMyAdmin database
    $sql = "ALTER TABLE owners
            ADD Image VARBINARY NOT NULL
            AFTER phone";
    //with the newly created column I insert a photo into it, where the owners id corresponds to 1
    $image = '<img src="Photos/DominicStewart.jpg" width="450" height="300">';
    $sql = "UPDATE owners
            SET Image = '$image'
            WHERE id = 1";

    //This retrieves all the relevant contact information which matches the owner's id which was chosen such as 43
    $ID = mysqli_real_escape_string($conn, $_GET['ID']);
    $sql = "SELECT * FROM owners WHERE id ='$ID'";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            echo nl2br(
                "<b>Owners ID: </b>" . "\n" . $row["id"] . "\n" . "<b>Owners Name: </b>" . "\n" . $row["name"] . "\n" .
                "<b>Owners Address: </b>" . "\n" . $row["address"] . "\n" . "<b>Owners Email: </b>" . "\n" . $row["email"] . "\n" .
                "<b>Owners Phone Number: </b>" . "\n" . $row["phone"] . "\n" . "<b> Image: </b>" . "\n"  . $row['Image'] . "<br>");
        }
    } else {
        echo "0 results";
    }
}



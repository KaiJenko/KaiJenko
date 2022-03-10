<html>
<title>Official Poppleton Dog Show's site</title>
<!-- the following code is just simple html code for my various styles and designs on my website, such as giving font colour -->
<div class="header">
    <p style = "text-align:left; top:0">  <?php websiteCreator("JenkoSites", "01/01/2022");?></p>
    <h1> <u>Welcome To Poppleton Dog Show!</u></h1>
    <style>
    h1{
        font-family: Garamond, serif;
        margin:0;
        padding:0;
    }
    body{
        font-family: "Baskerville Old Face", sans-serif;
    }
    .header {
        padding: 1px;
        text-align: center;
        background: aqua;
        color: darkblue;
    }
    * {
        box-sizing: border-box;
    }
    .row {
        display: flex;
        flex-wrap: wrap;
    }
    .side {
        flex: 20%;
        background-color: lightblue;
        color: darkblue;
        padding: 22px 5px 5px 20px;
        height: 270px;
    }

    .main {
        flex: 80%;
        background-color: lightblue;
        padding: 5px 25px 5px 5px;
        text-align: center;
        height: 270px;

    }
    .bottom {
        background-color: aqua;
        padding: 5px 15px 5px 5px;
        height: 270px;
        text-align: center;
    }
    table {
        border-spacing: 3px;
    }
    </style>
<body>

<?php
require '../Coursework3/connectToSQL.php';//using require uses another class which contains the code for connecting to the php database
//This is the code for the header portion of my website, which displays all the owners who have entered at least one dog
// in at least one event - meets the first requirement of my project
    $sql = "SELECT COUNT(Distinct(owner_id)) AS NumOfOwners FROM `dogs`";
    $result = $conn -> query($sql); //this creates the connection between my sql code and the PHPMyAdmin database
    if ($result->num_rows > 0) { //ensures there is data to show
        while($row = $result->fetch_assoc()) { echo "<h1>This year " . $row["NumOfOwners"] . " owners  </h1>" ;}}//fetches the results and displays it in a certain format

    else { echo "0 results";}

    $sql = "SELECT COUNT(DISTINCT(dog_id)) AS NumOfDogs FROM `entries` ";
    $result = $conn -> query($sql);
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) { echo "<h1>" . "entered " . $row["NumOfDogs"]. " dogs </h1>";}}
    else {echo "0 results";}

    $sql = "SELECT COUNT(DISTINCT(event_id)) AS NumOfEvents FROM competitions";
    $result = $conn -> query($sql);
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) { echo "<h1>" . " in " . $row["NumOfEvents"]. " events!</h1>";}
    }
    else { echo "0 results";
    }

    function websiteCreator($name, $date) { //simple function which reduces need for repeated code
        echo "This website is maintained by: --$name-- Created on: $date <br>"; //displays two variables wherever this function is called
    }

?>

</body>
<p> Such a great turnout thank-you for all of the support </p>
</div>

<!-- above is the header portion of my website, next is the main body which includes two columns, a main and side column -->
<div class = "row">
    <div class = "side">
        <img src="Photos/EmilyRottweiler.jpg" width="250" height="200"><!--this inserts a photo into my side column -->
        <p> ---The winner of the dog show: Emily--- </p>
    </div>
    <div class = "main">


<h2>
    <u>Below Is a List of The Top 10 Dogs based on their average score across more than one event!</u>
</h2>
<table border = 1> <!--creates a simple table which stores the following php code -->
<?php
//this block of code is for meeting the list of top dogs, with the best average scores across more than one event
//I used inner joins to grab different columns and their values from multiple tables and display them in one table for easy comparison
$sql = "SELECT UPPER(dogs.name) AS Name, breeds.name AS Breed, AVG(entries.score) AS AVGScore, owners.name AS OwnerName, owners.email AS Email, owners.id AS ownerID FROM `dogs`  
        inner join breeds on breed_id = breeds.id
        inner join entries on dogs.id = entries.dog_id
        inner join owners on owners.id = dogs.owner_id
        GROUP BY entries.dog_id HAVING COUNT(entries.id) > 1 ORDER BY `AVGScore` DESC LIMIT 10";
$result = $conn -> query($sql);
if ($result->num_rows > 0) {
    print("<td>");//this is the code format which is stored in the list,which I have formatted into a table
    while($row = $result->fetch_assoc()) { echo nl2br("<th>" . '<span style="color: darkblue; font-size: 9.5px;">' . " Dogs Name: " . "\n" . $row["Name"]
        . "\n" . " Breed: " . "\n" . $row["Breed"] . "\n" . " Dogs Average Score: " . "\n" . $row["AVGScore"] . "\n" . "<br>" .
        " Owners Name: " . "\n" . "<a href='OwnerContactInfo.php?ID={$row["ownerID"]}'>{$row['OwnerName']}</a><br>" .
        "Email: " ." <a href='mailto: " . "\n" . $row['Email'] . " '>"  . "\n" . $row['Email'] . "<td>");}}
        //the code above also creates a link which is assigned to the owners name, along with their email address, which is also turned
        //into a link, which opens the default email application, meeting this requirement
else { echo "0 results";}

?>
</table>
    </div>
</div>
<div class = "bottom">
    <body>
    <br>
    <h1> <u>These are the dogs which scored a perfect score in at least 1 event!</u></h1>
    <table border = 2>
    <?php
    //this is extra code just to fill out my website and also demonstrate my abilities with php and sql
    //here I create a view which is then created inside my PHPMyAdmin database, it joins two tables and can be viewed from my website
    $sql = "CREATE VIEW TopScoreView AS SELECT
    distinct(dogs.name), entries.score from dogs
    inner join entries on dogs.id = entries.dog_id
    where entries.score = 10" ;
    $result = $conn -> query($sql);

    //I decided my view did not display enough information, so I updated the view with another inner join and information to be displayed
    $sql = "CREATE OR REPLACE VIEW TopScoreView AS SELECT
    distinct(dogs.name) AS name, entries.score AS score, breeds.name AS breed from dogs
    inner join entries on dogs.id = entries.dog_id
    inner join breeds on breeds.id = dogs.breed_id
    where entries.score = 10";
    $result = $conn -> query($sql);

    //this is the small piece of code which displays the information from my view in my database
    $sql = "SELECT name, breed FROM TopScoreView";
    $result = $conn -> query($sql);
    if ($result->num_rows > 0) {
        print("<br>");
        print('<td>');
        while($row = $result->fetch_assoc()) { echo nl2br( '<th style = "padding: 3px">' . "<u>" . $row["name"] . "</u>" . "\n" ."Breed: " . "\n" . $row["breed"] . "<td>");}}
    else { echo "0 results";}

    $conn->close();
    ?>

    </table>
    <p style = "text-align: right; padding: 65px 0 5px 5px">  <?php websiteCreator("JenkoSites", "01/01/2022");?> </p>
    </body>
</div>
</html>

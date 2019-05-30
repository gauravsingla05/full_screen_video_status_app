<?php	

$uploaddir = 'videos/';
$uploadfile = $uploaddir . basename($_FILES['uploaded_file']['name']);


if (move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $uploadfile)) {
     
       
                    echo "Entry Failed <br/>"; 
} else {
    echo "Possible file upload attack!\n";
}
?>

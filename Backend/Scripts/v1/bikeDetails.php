<?php 

	require '../includes/DBOperations.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if(isset($_POST['cityName'])&&isset($_POST['bookStart'])&&isset($_POST['bookEnd'])){
				$db = new DBOperations();

				if($db->getBikeNumber($_POST['cityName'],$_POST['bookStart'],$_POST['bookEnd'])){
					$response["BikeDetails"] = $db->BikeDetails($_POST['cityName'],$_POST['bookStart'],$_POST['bookEnd']);
					$response["Message"]="Successful";
				}else{
					$response["Message"]="No Bikes Available";
				}
		}
		else{
			$response["Message"]="Required Fields are missing";
		}
	}else{
		$response["Message"]="Not a GET method";
	}

	echo json_encode($response);
?>
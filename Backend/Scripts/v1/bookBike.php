<?php 

	require '../includes/DBOperations.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if(isset($_POST['bikeStart'])&&isset($_POST['bikeEnd'])&&isset($_POST['bikeID'])){
				$db = new DBOperations();
				if($db->bookBikes($_POST['bikeStart'],$_POST['bikeEnd'],$_POST['bikeID'])){
					$response["Message"]="Successful";
				}else{
					$response["Message"]="Unsuccessful";
				}
		}else{
			$response["Message"]="Parameters not set";
		}
	}else{
		$response["Message"]="Not a POST method";
	}

	echo json_encode($response);
?>
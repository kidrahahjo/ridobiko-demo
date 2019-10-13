<?php 

	require '../includes/DBOperations.php';
	$response = array();

	if($_SERVER['REQUEST_METHOD']=='GET'){

		$db = new DBOperations();

		$response["City"] = $db->getCities();

		$response["Message"]="Successful";
	
	}else{
			$response["Message"]="Not a GET Method";
	}
	echo json_encode($response);
?>
<?php

	/**
	 * 
	 */
	class DBOperations 
	{
		private $con;
		public $res = array();
		public $myRes = array();
		function __construct()
		{
			require_once dirname(__FILE__).'/DBConnect.php';
			$db = new DBConnect();
			

			$this->con = $db->connect();
		
		}

		function getCities(){
			$stmt = $this->con->prepare("select distinct City from bikedetails order by City");

			if($stmt->execute()){
				$stmt->bind_result($City);
			}
			while($stmt->fetch()){
				$cName = array();
				$cName['name'] =  $City;
				$res[] = $cName;	
			}
  			return $res;
		}

		function getBikeNumber($cityName,$bookStart,$bookEnd){
			$stmt = $this->con->prepare("SELECT * from bikedetails where (City = ? and BookEnd<?) or (City = ? and BookStart IS NULL) or (City = ? and BookStart>?) order by BikeName");

			$stmt->bind_param("sssss",$cityName,$bookStart,$cityName,$cityName,$bookEnd);

			$stmt->execute();

			$stmt->store_result();

			return $stmt->num_rows>0;

		}

		function bikedetails($cityName,$bookStart,$bookEnd){
			$stmt = $this->con->prepare("SELECT BikeID, BikePicture, BikeName, BikeRent, BikeDeposit from bikedetails where (City = ? and BookEnd<?) or (City = ? and BookStart IS NULL) or (City = ? and BookStart>?) order by BikeName");

			$stmt->bind_param("sssss",$cityName,$bookStart,$cityName,$cityName,$bookEnd);


			if($stmt->execute()){
				$stmt->bind_result($bikeID,$bikePic,$bikeName,$bikeRent,$bikeDeposit);
			}

			while($stmt->fetch()){
				$bikes = array();
				$bikes['BikeID']=$bikeID;
				$bikes['BikePic']=$bikePic;
				$bikes['BikeName']=$bikeName;
				$bikes['BikeRent']=$bikeRent;
				$bikes['BikeDeposit']=$bikeDeposit;
				$res[] = $bikes;	
			}

			return $res;

		}

		function bookBikes($bikeStart,$bikeEnd,$bikeID){
			$stmt = $this->con->prepare("UPDATE bikedetails SET BookEnd = ? , BookStart = ? WHERE `BikeID` = ?");

			$stmt->bind_param('sss', $bikeEnd,$bikeStart,$bikeID);

			if($stmt->execute()){
				return true;	
			}else{
				return false;
			}
		}
	

	}

?>	

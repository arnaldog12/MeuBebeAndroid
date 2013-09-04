<?php 

function adicionaProgresso($id, $progresso){
		if (@pg_query($con, "BEGIN")) {
   		
			$query = "INSERT INTO progresso  VALUES('$id', '$progresso');";	
			if (@pg_query($con, $query)) {
				echo json_encode(array("status" => true));
			} else {
				@pg_query($con, "ROLLBACK");
				echo  json_encode(array("status" => false, "msg" => "Error commit"));
			}
			
		} else {
			echo  json_encode(array("status" => false, "msg" => "Sequencia bebe agdsghjasdhgjsadghjdasghjasd"));
		}

}

?>

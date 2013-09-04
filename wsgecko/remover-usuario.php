<?php

include 'Classes/Conexao.php';

if (isset($_GET['usuario']) && isset($_GET['senha'])) {

	$usuario = $_GET['usuario'];
	$senha = $_GET['senha'];

	if (filter_var($usuario, FILTER_VALIDATE_EMAIL) && preg_match("/[abcdefABCDEF0-9]{128,128}/", $senha)) {

		$con_bd = new Conexao();
		$con_bd->abreConexao();
		$con = $con_bd->getConexao();	
	
		if (@pg_fetch_assoc(@pg_query($con, "SELECT * FROM usuarios WHERE email = '$usuario' AND senha = '$senha';"))) {
		
			if (@pg_query($con, "BEGIN")) {
		
				if ($resp = @pg_query($con, "SELECT * FROM responsaveis WHERE responsavel = '$usuario';")) {

					if (@pg_query($con, "DELETE FROM responsaveis WHERE responsavel = '$usuario';")) {

						$ok = true;
						while ($val = @pg_fetch_assoc($resp)) {

							if (!@pg_query($con, "DELETE FROM progressos WHERE id_bebe = '$val[id_bebe]';")
								|| !@pg_query($con, "DELETE FROM bebes WHERE id = '$val[id_bebe]';")) {

								$ok = false;
								@pg_query($con, "ROLLBACK");
								echo  json_encode(array("status" => false, "msg" => "Erro delete bebe"));
								break;
							}
						}

						if ($ok) {
	
							if (@pg_query($con, "DELETE FROM usuarios WHERE email = '$usuario';")) {

								if (@pg_query($con, "COMMIT")) {

									echo  json_encode(array("status" => true));
								} else {

									@pg_query($con, "ROLLBACK");
									echo  json_encode(array("status" => false, "msg" => "Erro commit"));
								}
							} else {

								@pg_query($con, "ROLLBACK");
								echo  json_encode(array("status" => false, "msg" => "Erro delete usuario"));
							}
						}
					} else {

						@pg_query($con, "ROLLBACK");
						echo  json_encode(array("status" => false, "msg" => "Erro delete reponsaveis"));
					}
				} else {

					@pg_query($con, "ROLLBACK");
					echo  json_encode(array("status" => false, "msg" => "Erro fetch responsável"));
				}
			} else {

				echo  json_encode(array("status" => false, "msg" => "Erro begin"));	
			}
		} else {
		
			echo  json_encode(array("status" => false, "msg" => "User not found"));	
		}
	} else {

		echo json_encode(array("status" => false, "msg" => "Invalid parameters"));
	}		
} else {

    echo  json_encode(array("status" => false, "msg" => "Missing parameters"));
}
?>


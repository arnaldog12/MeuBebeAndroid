<?php

include 'Classes/Conexao.php';

if (isset($_GET['id'])) {

        $id = $_GET['id'];

                $con_bd = new Conexao();
                $con_bd->abreConexao();
                $con = $con_bd->getConexao();
                        if (@pg_query($con, "BEGIN")) {

                                if ($resp = @pg_query($con, "SELECT * FROM responsaveis WHERE id_bebe = '$id';")) {

                                        if (@pg_query($con, "DELETE FROM responsaveis WHERE id_bebe = '$id';")) {

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

    echo  json_encode(array("status" => false, "msg" => "Missing parameters"));
}
?>


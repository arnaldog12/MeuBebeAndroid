<?php

Class Conexao {

    protected $host = "localhost";
    protected $user = "postgres";
    protected $pswd = "g3ck04pps";
    protected $dbname = "wsgecko";
    protected $con = null;

    function __construct()
    {
        
    }
    
    function abreConexao()
    {
	
        $this->con = @pg_connect("host=$this->host user = $this->user
        password = $this->pswd dbname = $this->dbname");
	return $this->con;
    }
    /*
   function abreConexao()
    {
             
        $this->con = @pg_connect("host=$this->host user = $this->user
        password = $this->pswd dbname = $this->dbname");
        echo "oi";
        return $this->con;
    }*/


    function executaQuery($sql)
    {
    	$bd = $this->abreConexao();
        $res = pg_query($bd, $sql);
        if (!$res) {
            pg_query($bd, "ROLLBACK");
            $retorno = false;
        } else {
            pg_query($bd, "COMMIT");
            $retorno = true;
        }
        $this->fechaConexao();
        return $retorno; 
    }

    function fechaConexao() 
    {
        @pg_close($this->con);
    }
    
    function listar($tabela, $colunas, $predicado)
    {
    	$bd = $this->abreConexao();
      	$sql = "SELECT ".$colunas." FROM $tabela $predicado;";
         // echo "<br>".$sql;
        $res = pg_query($bd, $sql);
    
        if (!$res){
            pg_query($bd, "ROLLBACK");
          //  echo "deu merda";
        }else{ 
            pg_query($bd, "COMMIT");
            $linhas = array();
            $linhas = pg_fetch_all($res);
    	}
    	
        $this->fechaConexao();            
        return $linhas;
    }

    function statusConexao() 
    {
       if (!$this->con) {
           echo "<h3>O sistema nao esta conectado a  [$this->dbname] em [$this->host].</h3>";
           exit;
       } else {
           echo "<h3>O sistema esta conectado a  [$this->dbname] em [$this->host].</h3>";
       }
    }

	function getConexao() {

		return $this->con;
	}
    
}

?>


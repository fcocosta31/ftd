<div class="center-align div-title-padrao col s12">
		Parâmetros do Systema
</div>
<br><br>
 <div class="container">

	<form action="srl" method="post" id="formsystemparams">

			<div class="row">
				<div class="col s12"><h5>Configurações da página</h5></div>
				<div class="col l6 m6 s12">
					<label>Titulo da Página: </label>
					<input type="text" value="${params.pagetitle}" name="pagetitle">
				</div>
				<div class="col l6 m6 s12">
					<label>E-mail: </label>
					<input type="text" value="${params.pageemail}" name="pageemail" placeholder="email@example.com">
				</div>
				<div class="col l6 m6 s12">
					<label>Telefone(s): </label>
					<input type="text" value="${params.pagefone}" name="pagefone" placeholder="(XX) XXXX-XXXX, XXXX-XXXX">
				</div>
				<div class="col l6 m6 s12">
					<label>Estado: </label>
					<input type="text" value="${params.pageuf}" name="pageuf" placeholder="Ex.: São Paulo">
				</div>
				<div class="col s12">
					<label>Mensagem Padrão: </label>
					<input type="text" value="${params.pagemsg}" name="pagemsg" placeholder="Ex.: Distribuidor exclusivo FTD Educação...">
				</div>
				<div class="col s12"><h5>Configurações do banco Totvs</h5></div>
				<div class="col l4 m4 s12">
					<label>Grupo Empresa (Totvs): </label>
					<input type="text" value="${params.gpoemptotvs}" name="gpoemptotvs" placeholder="Ex.: 110">
				</div>
				<div class="col l5 m5 s12">
					<label>Nº IP MSSQL Server: </label> 
					<input type="text" value="${params.mssqladdress}" name="mssqladdress" placeholder="Ex.: 192.168.0.223">
				</div>									
				<div class="col l4 m4 s12">
					<label>Domain Server Windows: </label> 
					<input type="text" value="${params.mssqldomain}" name="mssqldomain" placeholder="Ex.: FTD">
				</div>									
				<div class="col l4 m4 s12">
					<label>User Server Windows: </label> 
					<input type="text" value="${params.mssqluser}" name="mssqluser" placeholder="Ex.: Administrador">
				</div>									
				<div class="col l3 m3 s12">
					<label>Senha Server Windows: </label> 
					<input type="password" value="${params.mssqlpswd}" name="mssqlpswd">
				</div>									
				<div class="col l3 m3 s12">
					<label>MSSQL Db Name: </label> 
					<input type="text" value="${params.mssqldb}" name="mssqldb" placeholder="Ex.: Totvs">
				</div>									
				<div class="col l2 m2 s12">
					<label>MSSQL Db Port: </label> 
					<input type="text" value="${params.mssqlport}" name="mssqlport" placeholder="Ex.: 1433">
				</div>									
				<div class="col s12"><h5>Configurações de e-mail de envio de mensagens</h5></div>
				<div class="col l6 m6 s12">
					<label>Conta de e-mail: </label> 
					<input type="text" value="${params.loginmail}" name="loginmail" placeholder="Ex.: logistica@email.com">
				</div>									
				<div class="col l6 m6 s12">
					<label>Senha conta de e-mail: </label> 
					<input type="password" value="${params.pswdmail}" name="pswdmail">
				</div>									
				<div class="col s12">
					<label>E-mails de destino (logística): </label> 
					<input type="text" value="${params.ccmails}" name="ccmails" placeholder="Ex.: fulano@ftd.com.br, sicrano@ftd.com.br">
				</div>
				<div class="col s12"><h5>Configurações de e-mail de download de mensagens (arquivos xml das notas fiscais) </h5></div>
				<div class="col l6 m6 s12">
					<label>Conta de e-mail: </label> 
					<input type="text" value="${params.loginrecmail}" name="loginrecmail" placeholder="Ex.: exemple@email.com">
				</div>									
				<div class="col l6 m6 s12">
					<label>Senha conta de e-mail: </label> 
					<input type="password" value="${params.pswrecmail}" name="pswrecmail">
				</div>									
				<div class="col l6 m6 s12">
					<label>Host do servidor de e-mail: </label> 
					<input type="text" value="${params.hostrecmail}" name="hostrecmail" placeholder="Ex.: imap.gmail.com">
				</div>									
				<div class="col l3 m3 s12">
					<label>Protocolo: </label> 
					<input type="text" value="${params.protocolrecmail}" name="protocolrecmail" placeholder="Ex.: pop3">
				</div>									
				<div class="col l3 m3 s12">
					<label>Porta: </label> 
					<input type="text" value="${params.portrecmail}" name="portrecmail" placeholder="Ex.: 995">
				</div>									
													
				<br>
				<div class="col s12">
					<input type="hidden" name="acao" value="setsystemparams">
					<button class="waves-effect waves-light btn blue"
						type="submit">Enviar</button>
				</div>
			</div>

	</form>
</div>

<script type="text/javascript">

$(document).ready(function(){	
	
	$.get("srl",{acao:"loadsystemparams"}, function(msg){
		if(msg == '0'){
			location.href = 'result.jsp';
		}		
	});
	
	$('#formsystemparams').on('submit', function(e){
		e.preventDefault();
		preloadActive();
		$.ajax({
		       type: "POST",
		       url: "srl",
		       data: $(this).serialize(),
		       success: function(msg) {
		    	   preloadDeActive();
		    	   mbox.alert(msg, function(){
		    		   location.href = 'index.jsp';
		    	   });		    	    	
		       }       
		    });
		
	});	
});
</script>

<script type="text/javascript">
	
	$(document).ready(function(){
		
		loadComboEmpresas();

		$("#txtsobrenome").blur(function(){
			var nome = $("#txtnome").val();
			var logarray = nome.split(' ');
			var login = logarray[0];
			login = login + '.' + $(this).val();

			$("#txtusuario").val(login.toLowerCase());
		});
		
		$("#txtsenha2").blur(function(){
			verificaSenhaUsuario();
		});
		
	});
	
	$(document).on("click", "#btnOpenDialogCadastrarUsuario", function(e) {
		
		$("#modalConfirmaCadastro").modal('open');
		
	});	
	
	$(document).on("click", "#btnConfirmaCadastro", function(e) {

		var senha = $("#txtsenha1").val();

		if(verificaSenhaUsuario()){
			sendAjaxCadastroUsuario($('#formCadastroUsuario').serialize());
	    }
		
	});	
		
</script>

<div class="center-align div-title-padrao col s12">
		Cadastrar usuário
</div>
<br><br>

	<div class="container">

		<div class="row">

						<form action="srl" method="post" id="formCadastroUsuario">

									<div class="col l5 m5 s12">
										<label>Nome: </label> <input type="text" id="txtnome"
											name="txtnome" required>
									</div>

									<div class="col l4 m4 s12">
										<label>Ultimo Nome: </label> <input type="text"
											name="txtsobrenome" id="txtsobrenome" required>
									</div>

									<div class="col l3 m3 s12">
										<label>Tipo: </label> <select
											name="txtcargo">
											<option value="1">Administrador</option>
											<option value="2">Usuário</option>
											<option value="3">Consultor</option>
										</select>
									</div>

									<div class="col l6 m6 s12">
										<label>E-mail: </label> <input type="text" name="txtemail"
											required>
									</div>

									<div class="col l2 m2 s12">
										<label>Setor: </label> <input type="text" name="txtsetor"
											required value="0">
									</div>

									<div class="col l4 m4 s12">
										<label>Empresa: </label> 
										<select	name="txtempresa" id="cmbEmpresas">
											<option value="Nenhuma" selected>Nenhuma</option>
										</select>
									</div>

									<div class="col l4 m4 s12">
										<label>Login: </label> 
										<input type="text" id="txtusuario" placeholder="login do usuário"
											name="txtlogin" required autocomplete="off">
										<p style="color: red; font-size: 10pt;" id="errorlogin"></p>

									</div>

									<div class="col l4 m4 s12">
										<label>Senha: </label> <input type="password" id="txtsenha1"
											name="txtsenha" required autocomplete="off">
									</div>

									<div class="col l4 m4 s12">
										<label>Confirma senha: </label> <input type="password"
											id="txtsenha2" required autocomplete="off">
										<p style="color: red; font-size: 10pt;" id="errorsenha"></p>
									</div>
								
								<br>

								<div class="col s12">
									<input type="hidden" name="acao" value="cadastrarusuario">
									<button  id="btnOpenDialogCadastrarUsuario" type="button"
										class="waves-effect waves-light btn blue">
										Salvar
									</button>	 
									<button
										type="reset" class="waves-effect waves-light btn red">
										Limpar
									</button>	
								</div>

						</form>

				
			</div>
				
	</div>
				<!-- Fim da div formtabela -->

				<div class="modal" id="modalConfirmaCadastro">
					<div class="center-align div-title-modal">
						Mensagem!
					</div>		
				
					<div class="modal-content">
							<h4>Confirma Inclusão?</h4>
					</div>
					<div class="modal-footer">
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat" id="btnConfirmaCadastro">Enviar</a>
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
					</div>					
				</div>


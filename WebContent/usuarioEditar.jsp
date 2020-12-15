<script type="text/javascript">					
			
	$(document).ready(function(){		
		
		carregarUsuarios();
		
		$('.select-with-search').select2({width: "100%"});
		
		$('#email').attr('autocomplete','off');
		
		$('#txtsenha2').blur(function(){
			verificaSenhaUsuario();
		});

		$('#cmbListUsuarios').change(function(){
			var id = $(this).val();
			$("#formEditarUsuario").removeClass('noDisplay');
			carregarDadosUsuario(id);			
		});
		
		$("#btnOpenDialogEditarUsuario").click(function(e) {
			
			$("#modalConfirmaEdicao").modal('open');
			
		});	

		$("#btnOpenDialogDeletarUsuario").click(function(e) {
			
			$("#modalConfirmaDelete").modal('open');
			
		});	

		$("#btnOpenDialogSenhaUsuario").click(function(e) {
			if($('#id').val() == ''){
				alert("Selecione primeiro um usuário!");
			}else{
				$("#modalAlterarSenha").modal('open');
			}			
		});	

		$("#btnDialogSalvarSenha").click(function(e) {
			
			var senha = $("#txtsenha1").val();

			if(verificaSenhaUsuario()){
				$("#modalAlterarSenha").modal('close');
				sendAjaxAlterarSenhaUsuario($('#id').val(), senha);
		    }
			
		});	

		$("#btnConfirmaEditar").click(function(e) {
			
			sendAjaxEditarUsuario($('#formEditarUsuario').serialize());
			
		});

		$("#btnConfirmaDelete").click(function(e) {
			
			sendAjaxDeletarUsuario($('#id').val());
					
		});
		
	});
	
</script>

<div class="center-align div-title-padrao col s12">
		Editar usuário
</div>
<br><br>

	<div class="container">

		<div class="row">



					<div class="row well col-sm-12">

						<div class="row">
							<div class="col l8 m8 s12">
								<select id="cmbListUsuarios"
								 class="select-with-search">
								</select>
							</div>
						</div>
						<br/><br/>
						<form action="srl" method="post" id="formEditarUsuario" class="noDisplay">

								<div class="row">

									<div class="col l1 m1 s2">
										<label>id: </label> <input type="text" id="id" name="txtid"
											readonly>
									</div>

									<div class="col l4 m4 s10">
										<label>Nome: </label> <input type="text" id="nome"
											name="txtnome" required>
									</div>

									<div class="col l4 m4 s12">
										<label>Ultimo Nome: </label> <input type="text" id="sobrenome"
											name="txtsobrenome" required>
									</div>

									<div class="col l3 m3 s12">
										<label>Tipo: </label> <input type="hidden" value="" id="cargo">
										<select name="txtcargo" id="txtcargo">
											<option value="1">Administrador</option>
											<option value="2">Usuário</option>
											<option value="3">Consultor</option>
										</select>
									</div>

								</div>

								<br/>

								<div class="row">
									<div class="col l5 m5 s12">
										<label>E-mail: </label> <input type="text" id="email"
											name="txtemail" required>
									</div>

									<div class="col l2 m2 s12">
										<label>Setor: </label> <input type="text" id="setor"
											name="txtsetor" required value="0">
									</div>

									<div class="col l1 m1 s12">
										<label>IdEmp: </label> <input type="text" name="txtidempresa"
											id="idempresa" readonly>
									</div>
									<div class="col l4 m4 s12">
										<label>Empresa: </label> <input type="text" id="nomeempresa"
											name="txtempresa" readonly>
									</div>
								</div>

								<br />

								<div class="row">
										<button type="button" id="btnOpenDialogEditarUsuario"
											class="waves-effect waves-light btn blue">
											Salvar
										</button>	
										<button type="button" id="btnOpenDialogSenhaUsuario"
											class="waves-effect waves-light btn green">
											Senha
										</button>
										<c:if test="${usuariologado.cargo eq 1}">
											<button type="button" id="btnOpenDialogDeletarUsuario"
												class="waves-effect waves-light btn red">
												Deletar
											</button>	
										</c:if>
								</div>

							<input type="hidden" name="acao" value="editarusuario"> <input
								type="hidden" id="txtcargousuariologado"
								value="${usuariologado.cargo}">
						</form>
					</div>

		</div>
				<!-- Fim da div formtabela -->				
	</div>
				
				<div class="modal" id="modalConfirmaEdicao">
					<div class="center-align div-title-modal">
						Mensagem!
					</div>		
					<div class="modal-content">
							<h4>Confirma Alteração?</h4>
					</div>
					<div class="modal-footer">
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat" id="btnConfirmaEditar">Ok</a>
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>						
					</div>					
				</div>


				<div class="modal" id="modalConfirmaDelete">
					<div class="center-align div-title-modal">
						Mensagem!
					</div>		
					<div class="modal-content">
							<h4>Tem certeza que deseja deletar?</h4>
					</div>
					<div class="modal-footer">
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat" id="btnConfirmaDelete">Ok</a>
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>						
					</div>					
				</div>

				<div class="modal" id="modalAlterarSenha">
					<div class="center-align div-title-modal">
						Alterar Senha!
					</div>		
					<div class="modal-content">
						<div class="row" id="divmodalsenha">
							<div class="row">
								<div class="col l4 m4 s12">
									<label>Nova senha: </label> <input type="password"
										id="txtsenha1" name="txtsenha" required autocomplete="off">
								</div>
							</div>
							<div class="row">
								<div class="col l4 m4 s12">
									<label>Confirma senha: </label> <input type="password"
										id="txtsenha2" required autocomplete="off">
									<p style="color: red; font-size: 10pt;" id="errorsenha"></p>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat" id="btnDialogSalvarSenha">Salvar</a>
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>												
					</div>
				</div>

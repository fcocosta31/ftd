<script type="text/javascript">
	
	$(document).on('keyup', '#txtnome', function(){
		var pos = $(this).caret();
		this.value = removerAcentos(this);
		$(this).caret(pos);
	});

	$(document).on('keyup', '#txtendereco', function(){
		var pos = $(this).caret();
		this.value = removerAcentos(this);
		$(this).caret(pos);
	});

	$(document).on('keyup', '#txtbairro', function(){
		var pos = $(this).caret();
		this.value = removerAcentos(this);
		$(this).caret(pos);
	});
	
	$(document).on("click", "#btnSalvarProfessor", function(){
	
		sendAjaxCadastroProfessor($('#formcadastroprofessor').serialize());
		
	});
	
	$(document).on("click", "#btnFormReset", function(){
		$('#formcadastroprofessor').trigger("reset");
		location.reload(true);
	});

	$(document).ready(function(){
		
		loadCombosCadastroProfessor();		
		$('#txtaniversario').mask("99/99");

		$("#cmbUF").change(function(){
			
			var uf = $("#cmbUF").val();

			loadComboMunicipio(uf);
			
		});
		
	});
	
</script>

<div class="center-align div-title-padrao col s12">
		Cadastrar Professor
</div>
<br><br>

<div class="container">


				<div class="row">

					<form action="srl" method="post" id="formcadastroprofessor">

								<div class="col l8 m8 s12">
									<label>Nome: </label> <input type="text" class="form-control"
										id="txtnome" name="txtnome" required>
								</div>

								<div class="col l4 m4 s12">
									<label>Cargo: </label> <select
										class="form-control selectpicker" title="Selecione o Cargo"
										name="txtcargo">
										<option value="Professor">Professor</option>
										<option value="Coordenador">Coordenador</option>
										<option value="Diretor">Diretor</option>
										<option value="Secretario">Secretario</option>
										<option value="Outro">Outro</option>
									</select>
								</div>


								<div class="col l7 m7 s12">
									<label>E-mail: </label> <input type="email" name="txtemail"
										class="form-control">
								</div>
								
								<div class="col l3 m3 s12">
									<label>Telefone: </label> <input type="text"
										name="txttelefone" value="" class="form-control">
								</div>
								
								<div class="col l2 m2 s12">
									<label>Aniversário: </label> <input type="text"
										id="txtaniversario" name="txtaniversario" placeholder="dd/mm"
										size="5" class="form-control">
								</div>

								<div class="col l4 m4 s12">
									<label>Escola:</label> 
				                    <input type="text" id="txtdescricaoescola"
				                     placeholder="Digite o nome da escola..."
				                      name="txtdescricaoescola"
				                      autocomplete="off"
				                     required data-activates="singleDropdownSchool" data-beloworigin="true">
				                     <ul id="singleDropdownSchool" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>
				                     <input type="hidden" name="txtescola" id="idescola">		                     
								</div>

								<div class="col l4 m4 s12">
									<label>Nivel de ensino:</label> <select id="cmbProfNivel"
										title="Selecione o nivel..." name="txtnivel"
										multiple="multiple">
										<option value="01-Infantil">Ed.Infantil</option>
										<option value="02-Fundamental_I">Ens.Fundamental I
												(1 ao 5 Ano)</option>
										<option value="03-Fundamental_II">Ens.Fundamental II
												(6 ao 9 Ano)</option>
										<option value="04-Medio">Ens.Medio</option>
										<option value="05-Geral">Outros</option>
									</select>
								</div>

								<div class="col l4 m4 s12">
									<label>Disciplina:</label> <select id="cmbProfDisciplinas"
										title="Selecione a disciplina..." name="txtdisciplina"
										multiple="multiple">
									</select>
								</div>

								<div class="col s12">
									<label>Endereço/Nº.: </label> <input type="text" name="txtendereco"
										id="txtendereco" required class="form-control">
								</div>
					
					
								<div class="col l6 m6 s12">
									<label>Bairro: </label> <input type="text" name="txtbairro"
										id="txtbairro" required class="form-control">
								</div>
					
					
								<div class="col l2 m2 s12">
									<label>UF: </label> <select class="form-control" id="cmbUF"
										name="txtuf">
										<option value="Nenhum">Nenhum</option>
									</select>
								</div>
								<div class="col l4 m4 s12">
									<label>Municipio: </label> <select class="form-control"
										id="cmbMunicipio" name="txtmunicipio">
										<option value="Nenhum">Nenhum</option>
									</select>
								</div>

							<div class="col s12">

								<button type="button" id="btnSalvarProfessor"
									class="waves-effect waves-light btn blue noPrint">Salvar
								</button>
								
								<button type="button" class="waves-effect waves-light btn grey noPrint"
									id="btnFormReset">Limpar
								</button>
								
								<input type="hidden" name="acao" value="cadastrarprofessor">
								

							</div>

					</form>

				</div>

</div>

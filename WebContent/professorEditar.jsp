<script type="text/javascript">
	
	var idprofessor;

	$(document).on('keyup', '#txtnome', function(){
		var pos = $(this).caret();
		this.value = removerAcentos(this);
		$(this).caret(pos);
	});
	
	$(document).on("click", "#btnEditarProfessor", function(){

		enviarAjaxEditarProfessor($('#formeditarprofessor').serialize());
		
	});

	$(document).on("click", "#btnDeletarProfessor", function(){
		idprofessor = $("#txtidprofessor").val();
		$("#modalConfirmaDeletarProfessor").modal("open");
	}); 
	
	$(document).on("click", "#btnConfirmaDeleteProfessor", function(){		
		sendAjaxDeletarProfessor(idprofessor);				
	});
	
	$(document).on("change", "#cmbListProfessores", function(){
		loadDadosProfessor($(this).val());
	});

	$(document).ready(function(){
		loadListProfessor();

		$('#txtaniversario').mask("99/99");
	});

</script>

<div class="center-align div-title-padrao col s12">
		Editar Professor
</div>
<br><br>

<div class="container">


	<div class="row">

						<div class="row">
							<div class="col l8 m8 s12">
								<label>Professor:</label>
			                    <input type="text" id="txtdescricaoprofessor"
			                     placeholder="Digite o nome do professor..."
			                      autocomplete="off"
			                     required data-activates="singleDropdownTeacher" data-beloworigin="true">			                     		                     
							</div>
							<ul id="singleDropdownTeacher" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>
						</div>
						<form action="srl" method="post" id="formeditarprofessor" class="noDisplay">

							<div class="col l2 m2 s12">
								<label>Id: </label> <input type="text" class="form-control"
									id="txtidprofessor" name="txtidprofessor" readonly>
							</div>

							<div class="col l6 m6 s12">
								<label>Nome: </label> <input type="text" class="form-control"
									id="txtnome" name="txtnome" required>
							</div>

							<div class="col l4 m4 s12">
								<label>Cargo: </label> <select
									class="form-control selectpicker" title="Selecione o Cargo"
									name="txtcargo" id="cmbCargo">
									<option value="Professor">Professor</option>
									<option value="Coordenador">Coordenador</option>
									<option value="Diretor">Diretor</option>
									<option value="Secretario">Secretario</option>
									<option value="Outro">Outro</option>
								</select>
							</div>


							<div class="col l7 m7 s12">
								<label>E-mail: </label> <input type="text" name="txtemail"
									class="form-control" id="txtemail">
							</div>
							<div class="col l3 m3 s12">
								<label>Telefone: </label> <input type="text" name="txttelefone"
									value="" class="form-control" id="txttelefone">
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
									class="selectpicker form-control show-tick"
									data-selected-text-format="count>1"
									title="Selecione o nivel..." name="txtnivel"
									multiple="multiple">
									<option value="01-Infantil">Ed.Infantil</option>
									<option value="02-Fundamental_I">Ens.Fundamental I (1
											ao 5 Ano)</option>
									<option value="03-Fundamental_II">Ens.Fundamental II
											(6 ao 9 Ano)</option>
									<option value="04-Medio">Ens.Medio</option>
									<option value="05-Geral">Outros</option>
								</select>
							</div>

							<div class="col l4 m4 s12">
								<label>Disciplina:</label> <select id="cmbProfDisciplinas"
									data-live-search="true"
									class="selectpicker form-control show-tick"
									data-selected-text-format="count>1"
									title="Selecione a disciplina..." name="txtdisciplina"
									multiple="multiple">
								</select>
							</div>
							
							<div class="col s12">
								<label>Endereço/Nº.: </label> <input type="text"
									name="txtendereco" id="txtendereco" required class="form-control">
							</div>
				
							<div class="col l4 m4 s12">
								<label>Bairro: </label> <input type="text" name="txtbairro"
									id="txtbairro" required class="form-control">
							</div>
							<div class="col l4 m4 s12">
								<label>Municipio: </label> <input type="text" name="txtmunicipio"
									id="txtmunicipio" required class="form-control">
							</div>
							<div class="col l4 m4 s12">
								<label>UF: </label> <input type="text" name="txtuf" required
									id="txtuf" class="form-control">
							</div>
							
							<div class="col s12">
								<button type="button" id="btnEditarProfessor"
									class="waves-effect waves-light btn blue noPrint">Salvar</button>
						
								<button type="button" id="btnDeletarProfessor"
									class="waves-effect waves-light btn red noPrint">Deletar</button>							
							</div>
							
							<input type="hidden" name="acao" value="editarprofessor">

						</form>

		</div>

</div>



				<div class="modal" id="modalConfirmaDeletarProfessor">
						<div class="center-align div-title-modal">
							Mensagem!
						</div>		
						<div class="modal-content">
								<h4>Confirma Deletar?</h4>
							<input type="hidden" id="idprofessor" value="">
						</div>
						<div class="modal-footer">
							<a href="#!" id="btnConfirmaDeleteProfessor"
								class="modal-action modal-close waves-effect waves-green btn-flat">Ok</a>
							<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>	
						</div>						
				</div>


<script type="text/javascript">

	
	$(document).on('keyup', '#txtnome', function(){
		var pos = $(this).caret();
		this.value = removerAcentos(this);
		$(this).caret(pos);
	});

	$(document).on('keyup', '#txtnomered', function(){
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
	
	$(document).ready(function(){
		loadCodCliente();
		loadComboUf();
		loadComboMunicipio('MA');
	});
	
	$(document).on("click", "#btnSalvarCliente", function(){
	
		sendAjaxCadastroCliente($('#formcadastrocliente').serialize());
		
	});

	$(document).on("change", "#cmbUF", function(){
		
		var uf = $("#cmbUF").val();

		loadComboMunicipio(uf);
		$("#cmbMunicipio").selectmenu('refresh');
		
	});

	$(document).on("change", "#cmbMunicipio", function(){
		var uf = $("#cmbUF").val();
		loadCodMunicipio(uf, $(this).val());		
	});

</script>



<div class="row well">

	<form action="srl" method="post" id="formcadastrocliente">

		<fieldset>
			<div class="row">
				<div class="col-sm-12">
					<legend>
						<strong>Cadastro de Cliente</strong>
					</legend>
				</div>
				<div class="col-sm-2">
					<label>Codigo: </label> <input type="text" class="form-control"
						id="txtcodigo" name="a1cod" maxlength="6" readonly>
				</div>
				<div class="col-sm-6">
					<label>Nome: </label> <input type="text" class="form-control"
						id="txtnome" name="a1nome" required maxlength="40">
				</div>
				<div class="col-sm-4">
					<label>Nome Reduz: </label> <input type="text" class="form-control"
						id="txtnomered" name="a1nreduz" required maxlength="20">
				</div>
			</div>

			<div class="row">

				<div class="col-sm-2">
					<label>Pessoa: </label> <select class="form-control"
						name="a1pessoa" id="cmbPessoa">
						<option value="F">F - Fisica</option>
						<option value="J">J -Juridica</option>
					</select>
				</div>

				<div class="col-sm-5">
					<label>CPF/CNPJ: </label> <input type="text" id="cnpj" name="a1cgc"
						value="" class="form-control">
				</div>

				<div class="col-sm-2">
					<label>Cod.Vendedor: </label> <input type="text" maxlength="6"
						name="a1vend" value="" class="form-control">
				</div>

				<div class="col-sm-3">
					<label>Grupo de clientes: </label> <select class="form-control"
						name="a1grptrib" id="cmbGrupo">
						<option value="05">05-Livreiros</option>
						<option value="20">20-Professores</option>
						<option value="30">30-Escolas Estaduais</option>
						<option value="40">40-Escolas Municipais</option>
						<option value="50">50-Escolas Particulares</option>
						<option value="60">60-Prefeituras</option>
						<option value="97">97-Orgaos Governamentais</option>
						<option value="99">99-Diversos</option>
					</select>
				</div>

			</div>


			<div class="row">
				<div class="col-sm-7">
					<label>Endereço/Nº.: </label> <input type="text" name="a1end"
						id="txtendereco" required maxlength="40" class="form-control">
				</div>
				<div class="col-sm-5">
					<label>Bairro: </label> <input type="text" id="txtbairro"
						name="a1bairro" value="" maxlength="30" class="form-control">
				</div>

			</div>

			<div class="row">
				<div class="col-sm-2">
					<label>UF: </label> <select class="form-control" id="cmbUF"
						name="a1est">
						<option value="Nenhum">Nenhum</option>
					</select>
				</div>
				<div class="col-sm-5">
					<label>Municipio: </label> <select class="form-control"
						id="cmbMunicipio" name="a1mun">
						<option value="Nenhum">Nenhum</option>
					</select>
				</div>
				<div class="col-sm-2">
					<label>Cod.Mun.: </label> <input type="text" class="form-control"
						id="txtcodmun" name="a1codmun" required maxlength="5" readonly>
				</div>
				<div class="col-sm-3">
					<label>CEP: </label> <input type="text" id="cep" name="a1cep"
						value="" class="form-control">
				</div>
			</div>

			<div class="row">
				<div class="col-sm-6">
					<label>E-mail: </label> <input type="text" name="a1email" value=""
						class="form-control" required maxlength="55">
				</div>
				<div class="col-sm-3">
					<label>Limite Crédito: </label> <input type="text" name="a1lc"
						value="" class="form-control" required maxlength="9">
				</div>
				<div class="col-sm-3">
					<label>Vcto Limite: </label> <input type="text" name="a1venclc"
						value="" class="form-control">
				</div>
			</div>
			<br />
			<div class="button-group">

				<input type="button" id="btnSalvarCliente" class="btn btn-primary"
					value="Salvar"> <input type="reset" class="btn btn-primary"
					value="Limpar"> <input type="hidden" name="acao"
					value="cadastrarcliente">

			</div>

		</fieldset>

	</form>

</div>


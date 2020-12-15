<script type="text/javascript">

		
	$(document).ready(function(){

		$('#txtnome').keyup(function(){
			var pos = $(this).caret();
			this.value = removerAcentos(this);
			$(this).caret(pos);
		});
			   
		$('#txtendereco').keyup(function(){
			var pos = $(this).caret();
			this.value = removerAcentos(this);
			$(this).caret(pos);
		});
		
		$('#txtcomplemento').keyup(function(){
			var pos = $(this).caret();
			this.value = removerAcentos(this);
			$(this).caret(pos);
		});
		
		$('#txtbairro').keyup(function(){
			var pos = $(this).caret();
			this.value = removerAcentos(this);
			$(this).caret(pos);
		});

		loadCombosCadastroEscola();
		
		$("#formcadastroescola").validate({
			rules: {
				cnpj: {verificaCNPJ: true} 
			}	
		});
	
		$("#cnpj").mask("99.999.999/9999-99");
	
		$("#cep").mask("99.999-999");
	
		$("#btnSalvarEscola").click(function(){
			
			sendAjaxCadastroEscola($('#formcadastroescola').serialize());
			
		});

		$("#cmbUF").change(function(){
			
			var uf = $("#cmbUF").val();

			loadComboMunicipio(uf);
			
		});
		
	});
			
</script>


<div class="center-align div-title-padrao col s12">
		Cadastrar Escola
</div>
<br><br>

<div class="row">

	<div class="col l10 m10 s12 offset-l1 offset-m1">

	<form action="srl" method="post" id="formcadastroescola">

			<div class="row">

				<div class="col l9 m9 s12">
					<label>Nome: </label> <input type="text" class="form-control"
						id="txtnome" name="txtnome" required>
				</div>
				<div class="col l3 m3 s12" id="divselectdependencia">
					<label>Dependencia: </label> <select class="form-control"
						name="txtdependencia" id="cmbDependencia">
						<option value="privada">Privada</option>
						<option value="publica">Pública</option>
					</select>
				</div>
			</div>

			<div class="row" id="divoptvendedores">

				<div class="col l3 m3 s12" id="divselectclassificacao">
					<label>Perfil da Escola: </label> <select class="form-control"
						name="txtclassificacao" id="cmbClassificacao">
						<option value="Nenhum">Nenhum</option>
						<option value="P1">P1 - Purista</option>
						<option value="A2">A2 - Aspirante</option>
						<option value="M3">M3 - Mercantil</option>
						<option value="P4">P4 - Popular</option>
					</select>
				</div>

				<div class="col l4 m4 s12">
					<label>CNPJ: </label> <input type="text" id="cnpj" name="cnpj"
						value="" onkeypress="cnpjMask(this, event)" class="form-control">
				</div>

				<div class="col l5 m5 s12" id="selectvendedor">
					<label>Vendedor: </label> <select id="cmbVendedor" name="txtsetor">
						<option value="Nenhum">Nenhum</option>
					</select>
				</div>
			</div>


			<div class="row">
				<div class="col l12 m12 s12">
					<label>Endereço/Nº.: </label> <input type="text" name="txtendereco"
						id="txtendereco" required class="form-control">
				</div>
			</div>


			<div class="row">
				<div class="col l6 m6 s12">
					<label>Complemento: </label> <input type="text"
						name="txtcomplemento" value="" id="txtcomplemento"
						class="form-control">
				</div>
				<div class="col l6 m6 s12">
					<label>Bairro: </label> <input type="text" name="txtbairro"
						id="txtbairro" required class="form-control">
				</div>
			</div>


			<div class="row">
				<div class="col l2 m2 s12">
					<label>UF: </label> <select class="form-control" id="cmbUF"
						name="txtuf">
						<option value="Nenhum">Nenhum</option>
					</select>
				</div>
				<div class="col l7 m7 s12">
					<label>Municipio: </label> <select class="form-control"
						id="cmbMunicipio" name="txtmunicipio">
						<option value="Nenhum">Nenhum</option>
					</select>
				</div>
				<div class="col l3 m3 s12">
					<label>CEP: </label> <input type="text" id="cep" name="cep"
						value="" class="form-control">
				</div>
			</div>

			<div class="row">
				<div class="col l7 m7 s12">
					<label>E-mail: </label> <input type="text" name="txtemail" value=""
						class="form-control">
				</div>
				<div class="col l5 m5 s12">
					<label>Telefone: </label> <input type="text" name="txttelefone"
						value="" class="form-control">
				</div>
			</div>
			<br />
			<div class="button-group">

				<button type="button" id="btnSalvarEscola" class="waves-effect waves-light btn blue noPrint">Salvar</button>
				<button type="reset" class="waves-effect waves-light btn brown noPrint">Limpar</button>
				<input type="hidden" name="acao" value="salvarescola">

			</div>

	</form>

</div>
</div>


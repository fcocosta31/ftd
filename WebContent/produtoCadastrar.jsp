<script type="text/javascript">
		
	$(document).on('keyup', '#txtcodigo', function(){
		var pos = $(this).caret();
		this.value = removerAcentos(this);
		$(this).caret(pos);
	});
	
	$(document).on('keyup', '#txtdescricao', function(){
		var pos = $(this).caret();
		this.value = removerAcentos(this);
		$(this).caret(pos);
	});
	
	$(document).on('keyup', '#txtautor', function(){
		var pos = $(this).caret();
		this.value = removerAcentos(this);
		$(this).caret(pos);
	});
	
	$(document).on('keyup', '#txtobs', function(){
		var pos = $(this).caret();
		this.value = removerAcentos(this);
		$(this).caret(pos);
	});
	
	$(document).on("click", "#btnOpenDialogCadastrarProduto", function(e) {
		
		$("#modalConfirmaCadastro").modal('open');
		
	});	
	
	$(document).on("click", "#btnConfirmaCadastro", function(e) {
		
		sendAjaxCadastrarProduto($('#formcadastrarproduto').serialize());
	
		
	});	
	
	$(document).ready(function(){
		loadCombosCadastrarProduto();
	});
</script>


<div class="center-align div-title-padrao col s12">
		Cadastrar Produto
</div>
<br><br>
 <div class="container">

	<form action="srl" method="post" id="formcadastrarproduto">

			<div class="row">
				<div class="col l2 m2 s12">
					<label>Código: </label> <input type="text" value=""
						id="txtcodigo" name="txtcodigo" size="10" required>
				</div>
				<div class="col l10 m10 s12">
					<label>Descrição: </label> <input type="text" value=""
						id="txtdescricao" name="txtdescricao" size="62"
						class="form-control">
				</div>
				<div class="col l8 m8 s12">
					<label>Autor: </label> <input type="text" value=""
						id="txtautor" name="txtautor" size="60" class="form-control">
				</div>
				<div class="col l4 m4 s12">
					<label>CodBarras: </label> <input type="text" value=""
						name="txtcodbar" id="txtcodbar" size="13"
						class="form-control">
				</div>
				<div class="col l3 m3 s12">
					<label>Familia: </label> <select id="cmbFamilia"
						name="txtfamilia" class="form-control">
						<option value="Nenhuma" selected>Nenhuma</option>
					</select>
				</div>
				<div class="col l3 m3 s12">
					<label>Nivel: </label> <select id="cmbNivel" name="txtnivel"
						class="form-control">
						<option value="Nenhum" selected>Nenhum</option>
					</select>
				</div>
				<div class="col l3 m3 s12">
					<label>Disciplina: </label> <select id="cmbDisciplina"
						name="txtdisciplina" class="form-control">
						<option value="Nenhuma" selected>Nenhuma</option>
					</select>
				</div>
				<div class="col l3 m3 s12">
					<label>Grupo: </label> <input type="text" name="txtgrupo"
						value="" size="61" class="form-control" placeholder="opcional">
				</div>									
				<div class="col l4 m4 s12">
					<label>Coleção: </label> <input type="text" name="txtcolecao"
						value="" size="61" class="form-control">
				</div>
				<div class="col l3 m3 s12">
					<label>Editora: </label> <select id="cmbEditora"
						name="txteditora" class="form-control">
						<option value="Nenhuma" selected>Nenhuma</option>
					</select>
				</div>
				<div class="col l3 m3 s12">
					<label>Série: </label> <select id="cmbSerie" name="txtserie"
						class="form-control">
						<option value="Nenhuma" selected>Nenhuma</option>
					</select>
				</div>
				<div class="col l2 m2 s12">
					<label>Preço: </label> <input type="text" name="txtpreco"
						value="<fmt:formatNumber value="0" type="number" pattern="#,#00.00#"/>"
						size="10" class="form-control">
				</div>
				<div class="col l1 m1 s6">
					<label>Ano: </label>
					<input type="text" placeholder="YYYY" id="txtLancto" name="txtlancto"
						value="<fmt:formatDate var="year" value="${date_now}" pattern="yyyy" />"
						class="form-control">										
				</div>
				<div class="col l1 m1 s6">
					<label>N.Páginas: </label>
					<input type="text" id="txtPaginas" name="txtpaginas"
						class="form-control" value="0">										
				</div>								
				<div class="col l1 m1 s6">
					<label>Peso: </label>
					<input type="text" id="txtPeso" name="txtpeso"
						class="form-control" value="0">										
				</div>								
				<div class="col l2 m2 s6">
					<label>MarketShare: </label>
					<input type="text" value="1" id="txtmarketshare" name="txtmarketshare"
						class="form-control">										
				</div>

				<div class="col l1 m1 s6">
					<label>Ativo? </label> <select name="txtativo"
						class="form-control" id="cmbAtivo">
						<option value="0">Sim</option>
						<option value="1">Não</option>
					</select>
				</div>
				<div class="col l6 m6 s12">
					<label>Status: </label> <input type="text" placeholder="Ativo, Estoque reduzido, etc..."
						id="txtstatus" name="txtstatus" class="form-control">
				</div>

				<div class="col l6 m6 s12">
					<label>Observação: </label> <input type="text" placeholder="opcional"
						id="txtobs" name="txtobs" size="50" class="form-control">
				</div>
				<div class="col l6 m6 s12">
					<label>URL da imagem: </label> <input type="text" value=""
						id="imagem" name="txtimagem" size="50" class="form-control">
				</div>

				<br>
				<div class="col s12">
					<input type="hidden" name="acao" value="cadastrarprd">
					<button class="waves-effect waves-light btn blue" id="btnOpenDialogCadastrarProduto"
						type="button">Enviar</button>
				</div>
			</div>

	</form>
</div>


<div class="modal noPrint" id="modalConfirmaCadastro">
	<div class="center-align div-title-modal">
		Mensagem!
	</div>		
	<div class="modal-content">
			<h4>Confirma Inclusão?</h4>
	</div>
	<div class="modal-footer">
		<a href="#!" id="btnConfirmaCadastro" 
			class="modal-action modal-close waves-effect waves-green btn-flat">Ok</a>
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
	</div>
</div>


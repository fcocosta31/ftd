<script type="text/javascript">	
	
	$(document).ready(function(){
		
		$('select').not('.disabled').material_select();
		
		$('#cmbDependencia').on('change',function(e) {
			var setor = $('#cmbVendedor').val();
			listarMunicipios(setor, $(this).val());
		});

		$('#cmbVendedor').on('change',function(e) {
			var dependencia = $('#cmbDependencia').val();			
			listarMunicipios($(this).val(), dependencia);
		});

		$('#cmbMunicipio').on('change',function(e) {
			var setor = $('#cmbVendedor').val();
			var dependencia = $('#cmbDependencia').val();
			listarBairros(setor, dependencia, $(this).val());
		});
				
		var setor = $('#cmbVendedor').val();
		var dependencia = $('#cmbDependencia').val();
		if(setor == '0'){
			vendedorselect();
		}
		listarMunicipios(setor, dependencia);
	});
	
	$(document).on('click', '#btnDialogEscolas',function(){                    
        
		if($("#cmbMunicipio").val() == null){
			$("#errorcidade").text("*escolha uma das opções");
			return false;
		}else if($("#cmbBairro").val() == null){
			$("#errorbairro").text("*escolha uma das opções");
			return false;
		}else if($("#cmbVisita").val() == ''){
			$("#errorvisita").text("*escolha uma das opções");
			return false;			
		}else{
			sendAjaxListarEscolas($("#formlistescolas").serialize());
		}
	});
	
</script>

<div class="center-align div-title-padrao col s12">
		Listar Escolas
</div>
<br><br>

<div class="container">
<div class="row">

	<form id="formlistescolas">

			<div class="row">
				<div class="input-field col l6 m6 s12">					
					<select name="txtsetor" id="cmbVendedor">
						<c:choose>
							<c:when test="${usuariologado.cargo lt 3}">
								<option value="0" selected>Todos</option>
							</c:when>
							<c:otherwise>
								<option value="${usuariologado.setor}" selected>${usuariologado.nome}</option>
							</c:otherwise>
						</c:choose>
					</select>
                    <label>Consultor </label>
				</div>
				<div class="input-field col l6 m6 s12">					
					<select name="cmbdependencia" id="cmbDependencia">
						<option value="privada">Privada</option>
						<option value="publica">Publica</option>
					</select>
                    <label>Dependência </label>
				</div>
			</div>
			
			<div class="row">
				<div class="input-field col l6 m6 s12">					
					<select name="cmbmunicipio" id="cmbMunicipio">
					</select>
                    <label>Município </label>
					<p id="errorcidade" style="font-size: 8pt; color: red"></p>
				</div>
				<div class="input-field col l6 m6 s12">					
					<select name="cmbbairro" id="cmbBairro" multiple>
					</select>
                    <label>Bairro </label>
					<p id="errorbairro" style="font-size: 8pt; color: red"></p>
				</div>
			</div>
			
			<div class="row">
				<div class="input-field col l6 m6 s12">					
					<select name="cmbvisita" id="cmbVisita">
						<option value="todos">Todas (Visitadas e Não Visitadas)</option>
						<option value="30">Visitadas há mais de 30 dias</option>
						<option value="60">Visitadas há mais de 60 dias</option>
						<option value="90">Visitadas há mais de 90 dias</option>
						<option value="120">Visitadas há mais de 120 dias</option>
						<option value="nunca">Sem nenhuma Visita cadastrada</option>
					</select>
                    <label>Filtros </label>
					<p id="errorvisita" style="font-size: 8pt; color: red"></p>
				</div>

				<div class="input-field col l4 m4 s8">
                    <input type="hidden" name="acao" value="listarescolas">
                    <button class="btn waves-effect waves-light blue loadevent" type="button" id="btnDialogEscolas">
                    	Enviar
                    </button>
				</div>
			</div>

	</form>

</div>
</div>

<script type="text/javascript">
	
		$(document).on("click", "#btnGeraRelToExcel", function() {
			var vendedores = $('#cmbVendedor').val();
			if(vendedores == null){
				mbox.alert("Selecione um ou mais vendedores!", function(){});
			}else{
				sendAjaxGeraRelDoacaoExcel(vendedores);
			}
			
		});
		
		$(document).on('click', '#btnMarcaItensDoados',function(){
			var vendedores = $('#cmbVendedor').val();
			if(vendedores == null){
				mbox.alert("Selecione um ou mais vendedores!", function(){});
			}else{
				sendAjaxMarcaItensAcertados(vendedores);
			}
			
		});
		$(document).ready(function(){
			vendedorselect();
		});

</script>


<div class="center-align div-title-padrao col s12">
		Rotinas de acerto das doações a filhos de professores
</div>
<br><br>

<div class="container">
	
	<div class="row">

		<div class="col s12">
			<h3>1º passo: Selecionar os vendedores</h3>
			<div class="bordered">
				<div class="col-md-5 col-sm-5 col-xs-12">
					<select name="setor" id="cmbVendedor" multiple="multiple">
						<option selected value="0">Todos</option>
					</select>
				</div>
				<br> <br>
			</div>
			<br>

			<h3>2º passo: Gerar a planilha excel para acerto</h3>
			<div class="bordered">
				<a href="#!" id="btnGeraRelToExcel"><img
					src="resources/images/export_excel.png"
					title="Exportar Doações para Excel"> <span>Clique para
						gerar a planilha</span></a>
			</div>

			<br>

			<h3>3º passo: Marcar os itens gerados como já acertados</h3>
			<div class="bordered">
				<a href="#!" id="btnMarcaItensDoados"><i
					class="material-icons" title="Marcar itens gerados">check_circle</i>
					<span>Clique para marcar os itens como já acertados</span></a>
			</div>
		</div>

	</div>
</div>

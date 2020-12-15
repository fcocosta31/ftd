<script type="text/javascript">

$(document).on('click','#btnBonusEditar',function(event){
	event.preventDefault();
	$("#action").val('editar');
	$("#formpesqbonus").submit();
});

</script>
<div class="center-align div-title-padrao col s12">
		Pesquisar Bônus
</div>
<br><br>

<div class="container">
<form action="srl"  method="post" id="formpesqbonus">
	<div class="col s12">
		<div class="row">
			<div class="col l8 m8 s12">
       			<div class="ac-input" style="margin-top: 10px">
				<label for="txtdescricaoescola">Nome da Escola:</label>
                <input type="text" id="txtdescricaoescola"
                 placeholder="Digite o nome da escola..."
                  name="txtdescricaoescola"
                  autocomplete="off"
                 required data-activates="singleDropdownSchool" data-beloworigin="true">		                                      		                     
                </div>	                     
				<input type="hidden" name="txtidescola" id="idescola">
				<ul id="singleDropdownSchool" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>
			</div>
		</div>

		<div class="row" id="divoptserieescola">

			<div class="col l4 m4 s12">
				<label>Ano: </label>
				<input type="text" class="form-control" placeholder="Digite o ano e tecle enter..."
				onkeydown="pesquisarBonusClick(this, event)" id="txtyear" name="txtano">
			</div>
			<div class="col l4 m4 s12">
				<label>Selecione o bônus: </label>
				<select id="cmbBonus" name="txtdesconto">
				</select>								
			</div>
			
			<div class="col s12">
				<div class="button-group">
					<button class="waves-effect waves-light btn blue noPrint" type="submit">Enviar
					</button>
					<c:if test="${usuariologado.id eq 1}">
						<button class="waves-effect waves-light btn brow noPrint" type="button" id="btnBonusEditar">Editar
						</button>
					</c:if>
				</div>
			</div>

			<input type="hidden" value="bonusdetalhar" name="acao">
			<input type="hidden" value="imprimir" name="action" id="action">
	
		</div>
	</div>
</form>
</div>

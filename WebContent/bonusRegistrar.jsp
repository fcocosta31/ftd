<div class="center-align div-title-padrao col s12">
		Registro de Bônus
</div>
<br><br>

<div class="container">
	<form id="formbonus" action="srl" method="post">
	<div class="col s12 center-align">
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
		<br>
		<div class="row">
			<div class="col l4 m4 s12">
				<label>Ano: </label> <input type="text" class="form-control"
					id="txtyear" name="txtyear">
			</div>		
			<div class="col l4 m4 s12">
				<label>Valor ou % Desconto do Bônus: </label> <input type="text" class="form-control"
					id="txtdesconto" name="txtdesconto">
			</div>		
		</div>
		<br>
		<div class="row">
			<div class="col l8 m8 s12">
				<label>Observação adicional (opcional): </label>
				<textarea rows="4" cols="40" class="form-control" name="txtdescricao">
						OU 10% EM ATE 6X NO CARTAO
				</textarea>				
			</div>		
		</div>
		<br>
		<div class="row">
			<div class="col l4 m4 s12">
					<input type="radio" name="txttipo" class="with-gap" id="rd2" value="valor">
					<label for="rd2">Valor(R$)</label>

					<input type="radio" name="txttipo" class="with-gap" id="rd1" value="desconto" checked>
					<label for="rd1">Desconto(%)</label>
			</div>
			<div class="col l2 m2 s12">
				<button class="waves-effect waves-light btn blue noPrint" type="submit" id="btnDialogSalvar">Salvar</button>			
			</div>
			<input type="hidden" name="acao" value="bonusregistrar">
		</div>
	</div>
	</form>
</div>
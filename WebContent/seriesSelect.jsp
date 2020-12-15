
<div class="col-sm-3">
	<select id="serieadocao" name="serieadocao" class="form-control">
		<option value="Bercario">Ed.Infantil - Berçário</option>
		<option value="Maternal">Ed.Infantil - Maternal</option>
		<option value="Maternal I">Ed.Infantil - Maternal I</option>
		<option value="Maternal II">Ed.Infantil - Maternal II</option>
		<option value="Maternal III">Ed.Infantil - Maternal III</option>
		<option value="Infantil I">Ed.Infantil - Infantil I</option>
		<option value="Infantil II">Ed.Infantil - Infantil II</option>
		<option value="Infantil III">Ed.Infantil - Infantil III</option>
		<option value="1 Ano">Ens.Fundamental - 1 Ano</option>
		<option value="2 Ano">Ens.Fundamental - 2 Ano</option>
		<option value="3 Ano">Ens.Fundamental - 3 Ano</option>
		<option value="4 Ano">Ens.Fundamental - 4 Ano</option>
		<option value="5 Ano">Ens.Fundamental - 5 Ano</option>
		<option value="6 Ano">Ens.Fundamental - 6 Ano</option>
		<option value="7 Ano">Ens.Fundamental - 7 Ano</option>
		<option value="8 Ano">Ens.Fundamental - 8 Ano</option>
		<option value="9 Ano">Ens.Fundamental - 9 Ano</option>
		<option value="1 Serie">Ens.Médio - 1 Série</option>
		<option value="2 Serie">Ens.Médio - 2 Série</option>
		<option value="3 Serie">Ens.Médio - 3 Série</option>
		<option value="Supletivo">Supletivo</option>
		<option value="EJA">EJA</option>
		<option value="Outros">Outros</option>
	</select>
</div>
<div class="col-sm-3" id="divselanoadocao">
	<select id="selanoadocao" name="selanoadocao" class="form-control">
		<option value="Nenhum">Nenhum</option>
	</select>
</div>
<div class="col-sm-3">
	<button class="btn btn-success form-control" id="btnconfirmaadocao"
		onclick="confirmarpesquisaadocaoclick()">Enviar</button>
</div>
<div class="col-sm-3">
	<button class="btn btn-success form-control" id="btnclearadocao"
		onclick="javascript:$('#txtdescricaoescola').val(''); $('#txtdescricaoescola').focus()">Limpar</button>
</div>

<div class="well">
	<form id="formdatanota" action="srl" method="post">
		<fieldset id="fieldorcam">
			<legend>
				<strong>Registrar data do recebimento da nota fiscal</strong>
			</legend>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-btn">
						<button class="btn btn-default" type="button">Número da
							nota:</button>
					</span> <input type="text" class="form-control" name="txtnota" required
						size="9" maxlength="15" value="">
				</div>
			</div>
			<div class="col-sm-4">
				<div class="input-group">
					<span class="input-group-btn">
						<button class="btn btn-default" type="button">Data
							recebimento:</button>
					</span> <input type="date" class="form-control" id="txtdata"
						name="txtdata" required>
				</div>
			</div>
			<input type="hidden" name="acao" value="datachegadanota"> <input
				class="btn btn-primary" type="submit" value="Enviar">
		</fieldset>
	</form>
</div>

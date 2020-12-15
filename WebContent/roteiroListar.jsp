<script type="text/javascript">
			
    $(document).ready(function(){
        vendedorselect();
    });

    $(document).on('click', '#btnDialogRoteiros', function(e){
        e.preventDefault();
        var formulario = $("#formroteiros").serialize();
        sendAjaxListarRoteiros(formulario);
    });

		
</script>
<div class="center-align div-title-padrao col s12">
		Listar Roteiros
</div>
<br><br>

<div class="container">
<div class="row">
<div class="col l10 m10 s12">

						<form id="formroteiros">

								<div class="row">
									
                                    <div class="input-field col l12 m12 s12">
										<select name="txtsetor" id="cmbVendedor">
										</select>
                                        <label>Vendedor: </label>
									</div>

                        			<div class="row"></div>														
                                    <br>			
                                    <div class="col l6 m6 s12">
                                        <label>Data inicial</label>
                                        <div>
                                            <input type="text" id="txtinicio" required name="txtdataini" class="datepicker validate">				
                                        </div>
                                    </div>
                                    <div class="col l6 m6 s12">
                                        <label>Data final</label>
                                        <div>
                                            <input type="text" id="txtfim" required name="txtdatafim" class="datepicker validate">					
                                        </div>
                                    </div>
									
								</div>
                                
								<input type="hidden" name="acao" value="listarroteiros">
                                <div class="col l4 m4 s8">
                                    <button class="btn waves-effect waves-light blue" id="btnDialogRoteiros" type="submit">
                                    	Enviar
                                    </button>
                                </div>

						</form>
		</div>
	</div>
</div>


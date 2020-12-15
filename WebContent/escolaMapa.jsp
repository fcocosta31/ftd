  
<script type="text/javascript">

function initMap() {
 
	var myLat = '${mapdata.lat}';
	var myLon = '${mapdata.lon}';
	var myTitle = '${mapdata.nome}';
	var infowindow = new google.maps.InfoWindow({
	    content: "<span><strong>${mapdata.nome}</strong><br/>"+
	    "${mapdata.endereco} ${mapdata.complemento}<br/>${mapdata.bairro}<br/>${mapdata.municipio}<br/>${mapdata.uf}</span>"
	});
	
	  var myLatLng = new google.maps.LatLng(myLat, myLon);	

	  var map = new google.maps.Map(document.getElementById('map'), {
	    zoom: 16,
	    center: myLatLng
	  });
	  
	  initializeMarker();	  
	  
	  function initializeMarker(){

		  var image = 'resources/images/Map-Marker-Flag.png';
		  
		  var marker = new google.maps.Marker({
		    position: myLatLng,
		    map: map,
		    icon: image,
		    draggable:true,
		    animation: google.maps.Animation.DROP,
		    title: myTitle
		  });
	  
		  google.maps.event.addListener(marker, 'dragend', function() {
				var lat = marker.getPosition().lat();
				var lng = marker.getPosition().lng();			
				$('#txtlat').val(lat);
				$('#txtlon').val(lng);	
		  });	  

		  google.maps.event.addListener(marker, 'click', function() {
			  infowindow.open(map,marker);
			  toggleBounce();
		  });

		  google.maps.event.addDomListener(window, "resize", function() {
				google.maps.event.trigger(map, "resize");
				map.setCenter(myLatLng);
		  });
	}


	function toggleBounce() {
		  if (marker.getAnimation() !== null) {
		    marker.setAnimation(null);
		  } else {
		    marker.setAnimation(google.maps.Animation.BOUNCE);
		  }
	}
	
}



$(document).on("click", "#btnMarker", function() {
	
	sendAjaxEditarMap($('#formMapMarker').serialize());	
	
});

</script>
<div class="embed-responsive embed-responsive-16by9">

   <div id="map" class="embed-responsive-item" style="border: 1px solid black"></div>
	
</div>

<div class="row">
	<div class="col l6 m6 s12">
		<form action="srl" method="post" id="formMapMarker">
			<button class="waves-effect waves-light btn blue" id="btnMarker">Salvar nova localização</button>
			<a href="escolas.jsp" class="waves-effect waves-light btn grey" id="btnBack">Voltar</a>
			<input type="hidden" name="txtlat" id="txtlat" value="${mapdata.lat}">
			<input type="hidden" name="txtlon" id="txtlon" value="${mapdata.lon}">
			<input type="hidden" name="txtid" id="txtid" value="${mapdata.id}">
			<input type="hidden" name="acao" value="editarmarker">
		</form>
	</div>
</div>

<script async defer
      src="https://maps.googleapis.com/maps/api/js?callback=initMap&signed_in=true&key=AIzaSyCTqaUht8f314rrMKdysWIZSJWlqJxIt4A">
</script>

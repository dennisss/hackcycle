var marker1, marker2;
var poly, geodesicPoly;

function initMap() {
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 4,
		center: {lat: 34, lng: -40.605},
		mapTypeId: google.maps.MapTypeId.ROADMAP
	});


	/*
	var contentString1 = '<p id="firstHeading" class="firstHeading">Sample Text for Start Point</p>'+ '</div>'

	var infowindow1 = new google.maps.InfoWindow({
		content: contentString1
	});

	var contentString2= '<p id="firstHeading" class="firstHeading">Sample Text for End Point</p>'+ '</div>'

	var infowindow2 = new google.maps.InfoWindow({
		content: contentString2
	});

	map.controls[google.maps.ControlPosition.TOP_CENTER].push(
		document.getElementById('info'));
	*/


/*
	marker1 = new google.maps.Marker({
		map: map,
		draggable: true,
		position: {lat: 40.714, lng: -74.006},
		title: 'Start Point'
	});
	marker1.addListener('click', function() {
		infowindow1.open(map, marker1);
	});

	marker2 = new google.maps.Marker({
		map: map,
		draggable: true,
		position: {lat: 48.857, lng: 2.352},
		title: 'End Point'
	});
	marker2.addListener('click', function() {
		infowindow2.open(map, marker2);
	});

	var bounds = new google.maps.LatLngBounds(
		marker1.getPosition(), marker2.getPosition());
	map.fitBounds(bounds);

	google.maps.event.addListener(marker1, 'position_changed', update);
	google.maps.event.addListener(marker2, 'position_changed', update);

	poly = new google.maps.Polyline({
		strokeColor: '#FF0000',
		strokeOpacity: 1.0,
		strokeWeight: 3,
		map: map,
	});

	geodesicPoly = new google.maps.Polyline({
		strokeColor: '#CC0099',
		strokeOpacity: 1.0,
		strokeWeight: 3,
		geodesic: true,
		map: map
	});

	update();

	*/


	function add_point(data, tid){

		var marker = new google.maps.Marker({
			map: map,
			//draggable: true,
			position: {lat: data.lat, lng: data.lon},
			//title: 'Start Point'
		});


		if(data.image){
			var infowindow = new google.maps.InfoWindow({
				content: '<img src="' + data.image + '" />'
			});

			marker.addListener('click', function() {
				infowindow.open(map, marker);
			});
		}


		map.setCenter( new google.maps.LatLng(data.lat, data.lon) );
		//socket.emit('my other event', { my: 'data' });
	}



	socket.on('data', function (data) {
		console.log(data);

		for(var i = 0; i < data.length; i++){
			add_point(data[i]);
		}

	});

}

function update() {
	var path = [marker1.getPosition(), marker2.getPosition()];
	poly.setPath(path);
	geodesicPoly.setPath(path);
	var heading = google.maps.geometry.spherical.computeHeading(path[0], path[1]);
//	document.getElementById('heading').value = heading;
//	document.getElementById('origin').value = path[0].toString();
//	document.getElementById('destination').value = path[1].toString();
}

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


	var last_point = null;
	function add_point(data, tid){

		var marker = new google.maps.Marker({
			map: map,
			//draggable: true,
			position: {lat: data.lat, lng: data.lon},
			//title: 'Start Point'
		});


		if(last_point != null){ // Make a line

			var poly = new google.maps.Polyline({
				strokeColor: '#FF0000',
				strokeOpacity: 1.0,
				strokeWeight: 3,
				map: map,
			});
			poly.setPath([last_point.getPosition(), marker.getPosition()]);

		}
		last_point = marker;



		if(data.image){

			marker.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');

			var url = data.image;

			var infowindow = new google.maps.InfoWindow({
				content: '<img style="width: 100%; height: 100%" src="' + data.image + '" />'
				+ '<a href="http://www.facebook.com/sharer.php?u=cycle.denniss.me:8080/'+url+'" target="_blank" title="Share with Facebook">' +
				'<img src="/images/facebook.jpg" alt="Facebook" />'+
				'</a>'+

				'<!-- Google+ -->'+
				'<a href="https://plus.google.com/share?url=cycle.denniss.me:8080/'+url+'" target="_blank" title="Share with Google+">'+
				'<img src="/images/gplus.jpg" alt="Google" />'+
				'</a>'+

				'<!-- Reddit -->'+
				'<a href="http://reddit.com/submit?url=cycle.denniss.me:8080/'+url+'&amp;title=My motorcycle trip" target="_blank" title="Share with Reddit">'+
				'<img src="/images/reddit.jpg" alt="Reddit" />'+
				'</a>'+

				'<!-- StumbleUpon-->'+
				'<a href="http://www.stumbleupon.com/submit?url=cycle.denniss.me:8080/'+url+'&amp;title=My Motorcycle Trip" target="_blank" title="Share with StumbleUpon">'+
				'<img src="/images/stumble.jpg" alt="StumbleUpon" />'+
				'</a>'+

				'<!-- Twitter -->'+
				'<a href="https://twitter.com/share?url=cycle.denniss.me:8080/'+url+'&amp;text=My motorcycle trip&amp;hashtags=gomoto" target="_blank" title="Share with Twitter">'+
				'<img src="/images/twitter.jpg" alt="Twitter" />'+
				'</a>'


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



	/*
	var ctx = document.getElementById("myChart").getContext("2d");


	var data = {
		labels: ["1"],
		datasets: [
			{
				label: "Heart rate",
				fillColor: "rgba(220,220,220,0.2)",
				strokeColor: "rgba(220,220,220,1)",
				pointColor: "rgba(220,220,220,1)",
				pointStrokeColor: "#fff",
				pointHighlightFill: "#fff",
				pointHighlightStroke: "rgba(220,220,220,1)",
				data: []
			},
		]
	};

	var chart = new Chart(ctx).Line(data, {

	});


	chart.addData([50, 60], ["1", "2"])
	*/


	socket.on('beat', function(data){
		console.log(data);

		document.getElementById('heartText').innerText = data.bpm + ' bpm';


	})



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
